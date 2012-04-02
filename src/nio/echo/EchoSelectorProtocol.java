/**
 * 
 */
package nio.echo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 
 * 
 * @author yangwm Jun 15, 2010 11:05:28 AM
 */
public class EchoSelectorProtocol implements TCPProtocol {

    private int bufSize;// Size of I/O buffer

    public EchoSelectorProtocol(int bufSize) {
        this.bufSize = bufSize;
    }

    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
        clntChan.configureBlocking(false);// Must be nonblocking to register
        // Register the selector with new channel for read and attach byte buffer
        clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    }

    public void handleRead(SelectionKey key) throws IOException {
        // Client socket channel has pending data
        SocketChannel clntChan = (SocketChannel) key.channel();
        ByteBuffer buf = (ByteBuffer) key.attachment();
        long bytesRead = clntChan.read(buf);
        if (bytesRead == -1) {// Did the other end close?
            clntChan.close();
        } else if (bytesRead > 0) {
            // Indicate via key that reading/writing are both of interest now.
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    public void handleWrite(SelectionKey key) throws IOException {

        /*
         * Channel is available forwriting,and key is valid(i.e.,client channel not closed).
         */
        // Retrieve data read earlier
        ByteBuffer buf = (ByteBuffer) key.attachment();

        buf.flip();// Prepare buffer forwriting
        SocketChannel clntChan = (SocketChannel) key.channel();
        System.out.println("clntChan write: " + new String(buf.array(), 0, buf.limit()));
        clntChan.write(buf);
        if (!buf.hasRemaining()) {// Buffer completely written?
            // Nothing left,so no longer interested in writes
            key.interestOps(SelectionKey.OP_READ);
        }
        buf.compact();// Make room for more data to be read in
    }
    
}
