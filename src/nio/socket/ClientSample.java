/**
 * 
 */
package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author yangwm in Sep 25, 2009 10:49:46 AM
 */
public class ClientSample {

    /**
     * create by yangwm in Sep 25, 2009 10:49:46 AM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        InetSocketAddress addr = new InetSocketAddress ("localhost", 8080);
        SocketChannel sc = SocketChannel.open();
        sc.connect(addr);
        sc.configureBlocking(false);
        
        while( !sc.finishConnect() ) {
            //doSomethingElse();
        }
        //doSomethingWithChannel(sc);
        sc.close();
    }

}
