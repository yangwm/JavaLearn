/**
 * 
 */
package nio.socket;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * @author yangwm in Sep 25, 2009 10:56:59 AM
 */
public class DatagramChannelSample {

    /**
     * create by yangwm in Sep 25, 2009 10:56:59 AM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        DatagramChannel channel = DatagramChannel.open();  
        DatagramSocket socket = channel.socket();  
        socket.bind(new InetSocketAddress("localhost", 9090));  
    }

}
