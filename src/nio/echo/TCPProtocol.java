/**
 * 
 */
package nio.echo;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * 
 * 
 * @author yangwm Jun 15, 2010 10:59:27 AM
 */
public interface TCPProtocol {
    void handleAccept(SelectionKey key) throws IOException;

    void handleRead(SelectionKey key) throws IOException;

    void handleWrite(SelectionKey key) throws IOException;
}
