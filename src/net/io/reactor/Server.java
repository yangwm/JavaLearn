/**
 * 
 */
package net.io.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * 
 * @author yangwm Jan 17, 2012 5:50:44 PM
 */
public class Server {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 9527;
        System.out.println("Server listen on port: " + port);
        new Thread(new Reactor(port)).start();
    }

}

class Reactor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocket;

    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));
        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());
    }

    /*
     * Alternatively, use explicit SPI provider: 
     * SelectorProvider p = SelectorProvider.provider(); 
     * selector = p.openSelector(); 
     * serverSocket = p.openServerSocketChannel();
     */

    // class Reactor continued
    public void run() { // normally in a new Thread
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selected = selector.selectedKeys();
                Iterator<SelectionKey> it = selected.iterator();
                while (it.hasNext()) {
                    dispatch((SelectionKey) it.next());
                }
                selected.clear();
            }
        } catch (IOException ex) { /* ... */
        }
    }

    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) (k.attachment()); // Acceptor or Acceptor.Handler
        if (r != null) {
            System.out.println(r.getClass());
            r.run();
            // new Thread(r).start(); not ok
        }
    }

    // class Reactor continued
    class Acceptor implements Runnable { // inner
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) {
                    //new GofHandler(selector, c);
                    //new StateHandler(selector, c);
                    //new GofPoolHandler(selector, c);
                    new StatePoolHandler(selector, c);
                }
            } catch (IOException ex) { /* ... */
            }
        }

        final class GofHandler extends BaseHandler implements Runnable {
            GofHandler(Selector sel, SocketChannel c) throws IOException {
                super(sel, c);
            }

            public void run() { // initial state is reader
                try {
                    read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            void read() throws IOException {
                socket.read(input);
                if (inputIsComplete()) {
                    processAndHandOff();
                }
            }
            void processAndHandOff() {
                process();
                sk.attach(new Sender());
                // Normally also do first write now
                sk.interestOps(SelectionKey.OP_WRITE);
                //sk.selector().wakeup(); // remove by yangwm 
            }
            class Sender implements Runnable {
                public void run() { // ...
                    try {
                        send();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        final class StateHandler extends BaseHandler implements Runnable {
            StateHandler(Selector sel, SocketChannel c) throws IOException {
                super(sel, c);
            }

            static final int READING = 0, SENDING = 1;
            int state = READING;

            // class Handler continued
            public void run() {
                try {
                    if (state == READING) {
                        read();
                    } else if (state == SENDING) {
                        send();
                    }
                } catch (IOException ex) { /* ... */
                }
            }

            void read() throws IOException {
                socket.read(input);
                if (inputIsComplete()) {
                    processAndHandOff();
                }
            }
            void processAndHandOff() {
                process();
                state = SENDING; // or rebind attachment
                // Normally also do first write now
                sk.interestOps(SelectionKey.OP_WRITE);
                //sk.selector().wakeup(); // add by yangwm 
            }
        }
        
        final class GofPoolHandler extends BaseHandler implements Runnable {
            GofPoolHandler(Selector sel, SocketChannel c) throws IOException {
                super(sel, c);
            }
            
            // uses util.concurrent thread pool
            final ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            
            public void run() { // initial state is reader
                try {
                    read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            void read() throws IOException {
                socket.read(input);
                if (inputIsComplete()) {
                    pool.execute(new Processer());
                }
            }
            class Processer implements Runnable {
                public void run() {
                    processAndHandOff();
                }
            }
            void processAndHandOff() {
                process();
                sk.attach(new Sender());
                // Normally also do first write now
                sk.interestOps(SelectionKey.OP_WRITE);
                sk.selector().wakeup(); // remove by yangwm, must be have 
            }
            class Sender implements Runnable {
                public void run() { // ...
                    try {
                        send();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        final class StatePoolHandler extends BaseHandler implements Runnable {
            StatePoolHandler(Selector sel, SocketChannel c) throws IOException {
                super(sel, c);
            }

            // uses util.concurrent thread pool
            final ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
            static final int PROCESSING = 3;
            
            static final int READING = 0, SENDING = 1;
            int state = READING;

            // class Handler continued
            public void run() {
                try {
                    if (state == READING) {
                        read();
                    } else if (state == SENDING) {
                        send();
                    }
                } catch (IOException ex) { /* ... */
                }
            }

            void read() throws IOException {
                socket.read(input);
                if (inputIsComplete()) {
                    //state = PROCESSING;
                    pool.execute(new Processer());
                }
            }
            class Processer implements Runnable {
                public void run() {
                    processAndHandOff();
                }
            }
            void processAndHandOff() {
                process();
                state = SENDING;
                // Normally also do first write now
                sk.interestOps(SelectionKey.OP_WRITE);
                sk.selector().wakeup(); // add by yangwm, must be have  
            }
        }

        class BaseHandler {
            final SocketChannel socket;
            final SelectionKey sk;
            static final int MAXIN = 1024;
            static final int MAXOUT = 1024;

            ByteBuffer input = ByteBuffer.allocate(MAXIN);
            ByteBuffer output = ByteBuffer.allocate(MAXOUT);

            BaseHandler(Selector sel, SocketChannel c) throws IOException {
                socket = c;
                c.configureBlocking(false);
                // Optionally try first read now
                sk = socket.register(sel, 0/*SelectionKey.OP_READ*/);
                sk.attach(this);
                sk.interestOps(SelectionKey.OP_READ);  // remove by yangwm 
                sel.wakeup();  // remove by yangwm 
            }

            boolean inputIsComplete() { /* ... */
                return true;
            }

            boolean outputIsComplete() { /* ... */
                return true;
            }

            void process() { /* ... */
                input.flip();
                byte[] result = ("hello, " + Charset.forName("UTF-8").decode(input)).getBytes();
                //input.clear();
                System.out.println("server result:" + new String(result) + ", result.length:" + result.length);

                output.put(result);
                output.flip();
                //output.clear();
                return;
            }

            void send() throws IOException {
                socket.write(output);
                if (outputIsComplete()) {
                    sk.cancel();
                    //socket.close(); // add by yangwm 
                }
            }
        }

    }
}
