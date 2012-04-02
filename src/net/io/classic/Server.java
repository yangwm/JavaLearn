/**
 * 
 */
package net.io.classic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 
 * @author yangwm Jan 17, 2012 5:50:44 PM
 */
public class Server {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int port = 9527;
        System.out.println("Server listen on port: " + port);
        new Thread(new Classic(port)).start();
    }

}

class Classic implements Runnable {
    private final int PORT;
    
    public Classic(int port) {
        PORT = port;
    }
    
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            while (!Thread.interrupted()) {
                new Thread(new Handler(ss.accept())).start();
                // or, single-threaded, or a thread pool
            }
        } catch (IOException ex) { /* ... */
        }
    }

    static class Handler implements Runnable {
        static final int MAX_INPUT = 1024;
        final Socket socket;

        Handler(Socket s) {
            socket = s;
        }

        public void run() {
            try {
                byte[] input = new byte[MAX_INPUT];
                socket.getInputStream().read(input);
                byte[] output = process(input);
                socket.getOutputStream().write(output);
            } catch (IOException ex) { /* ... */
            }
        }

        private byte[] process(byte[] input) { /* ... */
            byte[] result = ("hello, " + new String(input)).getBytes();
            System.out.println("server result:" + new String(result) + ", result.length:" + result.length);
            return result;
        }
    }
    
}

//Note: most exception handling elided from code examples
