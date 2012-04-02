/**
 * 
 */
package nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author yangwm in Sep 25, 2009 10:03:46 AM
 */
public class ServerSample {

    /**
     * create by yangwm in Sep 25, 2009 10:03:52 AM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        ServerSocketChannel serverChannel = ServerSocketChannel.open(); 
        ServerSocket serverSocket = serverChannel.socket(); 
        serverSocket.bind(new InetSocketAddress(8080)); 
        serverChannel.configureBlocking(false);
        
        Selector selector = Selector.open(); 
        serverChannel.register(selector, SelectionKey.OP_ACCEPT); 

        while(true){ 
            int n = selector.select(); 
            System.out.println("selector.select()=" + n);
            
            Iterator<SelectionKey> it = selector.selectedKeys().iterator(); 
            while (it.hasNext()) { // deal with I/O event 
                SelectionKey key = it.next(); 

                if (key.isAcceptable()) { // do something 
                    System.out.println("do something");
                    
                    ServerSocketChannel responeChannel = (ServerSocketChannel)key.channel(); 
                    SocketChannel channel = responeChannel.accept(); 
                    channel.configureBlocking(false); 
                    channel.register(selector, SelectionKey.OP_READ); 
                } else if (key.isReadable()) { // read data 
                    System.out.println("read data");
                    
                    //readDataFromSocket(key); 
                    SocketChannel requestChannel = (SocketChannel)key.channel(); 
                    requestChannel.close();
                } 

                it.remove(); //we remove the key beacause of we don't care about it 
            } 
        }
    }

}
