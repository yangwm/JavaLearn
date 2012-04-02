/**
 * 
 */
package distributed.udpnio;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.Charset;

/**
 * 实现UDP/IP+NIO 方式的系统间通讯的代码，服务器端：
 * 网络IO 的操作则改为通过ByteBuffer来实现。
 * 
 * @author yangwm in Mar 23, 2010 11:23:26 AM
 */
public class ServerUdpNio {

    /**
     * create by yangwm in Mar 23, 2010 11:23:26 AM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int rport = 9527;
        int sport = 9528;
        
        DatagramChannel sendChannel = DatagramChannel.open();
        sendChannel.configureBlocking(false);
        sendChannel.connect(new InetSocketAddress(host, sport));
        
        DatagramChannel receiveChannel = DatagramChannel.open();
        DatagramSocket serverSocket = receiveChannel.socket();
        serverSocket.bind(new InetSocketAddress(rport));
        System.out.println("Data receive listen on port: " + rport);
        receiveChannel.configureBlocking(false);
        
        Selector selector = Selector.open();
        receiveChannel.register(selector, SelectionKey.OP_READ);
        while (true) {
            int nKeys = selector.select();
            if (nKeys > 0) {
                for (SelectionKey selectionKey : selector.selectedKeys()) {
                    if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        DatagramChannel datagramChannel = (DatagramChannel)selectionKey.channel();
                        datagramChannel.receive(byteBuffer);
                        byteBuffer.flip();
                        
                        String message = Charset.forName("UTF-8").decode(byteBuffer).toString();
                        System.out.println("Message from client: " + message);
                        
                        if ("quit".equalsIgnoreCase(message.trim())) {
                            System.out.println("Server has been shutdown!");
                            
                            datagramChannel.close();
                            selector.close();
                            sendChannel.close();
                            System.exit(0);
                        }
                        
                        String response = "Server response：" + message;
                        sendChannel.write(Charset.forName("UTF-8").encode(response));
                    }
                }
                selector.selectedKeys().clear();
                
            }
        }
    }

}

/*
Data receive listen on port: 9527
Message from client: hello, yangwm, udp nio
Message from client: what are your doing?
Message from client: quit
Server has been shutdown!

*/
