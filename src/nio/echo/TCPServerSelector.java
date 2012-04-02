/**
 * 
 */
package nio.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * 
 * 
 * @author yangwm Jun 15, 2010 11:03:41 AM
 */
public class TCPServerSelector {
    private static final int BUFSIZE = 256;// Buffersize(bytes)

    private static final int TIMEOUT = 3000;// Waittimeout(milliseconds)

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {// Test for correct # of args
            throw new IllegalArgumentException("Parameter(s):<Port>...");
        }

        // Create a selector to multiplex listening sockets and connections
        Selector selector = Selector.open();
        // Create listening socket channel for each port and registers elector
        for (String arg : args) {
            ServerSocketChannel listnChannel = ServerSocketChannel.open();
            listnChannel.socket().bind(
                    new InetSocketAddress(Integer.parseInt(arg)));
            listnChannel.configureBlocking(false);// mustbenonblockingtoregister
            // Register selector with channel. There turned key is ignored
            listnChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        // Create a handler that will implement the protocol
        TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);
        while (true) {// Runforever,processingavailableI/Ooperations
        // Wait for some channel tobe ready(or timeout)
            if (selector.select(TIMEOUT) == 0) {// returns # of ready chans
                System.out.print(".");
                continue;
            }
            // Get iterator on set of keys with I/O to process
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();// Key is bit mask
                // Server socket channel has pending connection requests?
                if (key.isAcceptable()) {
                    protocol.handleAccept(key);
                }

                // Client socket channel has pending data?
                if (key.isReadable()) {
                    protocol.handleRead(key);
                }

                // Client socket channel is available for writing and
                // key is valid(i.e.,channel not closed)?
                if (key.isValid() && key.isWritable()) {
                    protocol.handleWrite(key);
                }
                keyIter.remove();// remove from set of selected keys
            }

        }

    }
}

/*
java nio.echo.TCPServerSelector 9527
...clntChan write: abc
.

*/

