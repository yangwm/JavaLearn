/**
 * 
 */
package distributed.udpnio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

/**
 * 实现UDP/IP+NIO 方式的系统间通讯的代码，客户端：
 * 
 * @author yangwm in Mar 23, 2010 4:09:39 PM
 */
public class ClientUdpNio {

    /**
     * create by yangwm in Mar 23, 2010 4:09:39 PM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int rport = 9528;
        int sport = 9527;
        
        DatagramChannel receiveChannel = DatagramChannel.open();
        receiveChannel.configureBlocking(false);
        DatagramSocket socket = receiveChannel.socket();
        socket.bind(new InetSocketAddress(rport));
        
        Selector selector = Selector.open();
        receiveChannel.register(selector, SelectionKey.OP_READ);
        
        DatagramChannel sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        sendChannel.connect(new InetSocketAddress(host, sport));
        
        BufferedReader systemIn = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String command = systemIn.readLine();
            sendChannel.write(Charset.forName("UTF-8").encode(command));
            
            if (command == null || "quit".equalsIgnoreCase(command.trim())) {
                System.out.println("Client quit!");
                
                systemIn.close();
                selector.close();
                sendChannel.close();
                System.exit(0);
            }
            
            int nKeys = selector.select();
            if (nKeys > 0) {
                for (SelectionKey selectionKey : selector.selectedKeys()) {
                    if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        DatagramChannel datagramChannel = (DatagramChannel)selectionKey.channel();
                        datagramChannel.receive(byteBuffer);
                        byteBuffer.flip();
                        
                        String response = Charset.forName("UTF-8").decode(byteBuffer).toString();
                        System.out.println(response);
                        byteBuffer = null;
                    }
                }
                selector.selectedKeys().clear();
                
            }
            
        }
    }

}

/*
hello, yangwm, udp nio
Server response：hello, yangwm, udp nio
what are your doing?
Server response：what are your doing?
quit
Client quit!

*/
