/**
 * 
 */
package distributed.tcpnio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * 实现TCP/IP+NIO 方式的系统间通讯的代码，服务器端：
 * SocketChannel和ServerSocketChannel两个关键的类，网络IO 的操作则改为通过ByteBuffer来实现。
 * 
 * @author yangwm in Mar 22, 2010 5:52:58 PM
 */
public class ServerTcpNio {

    /**
     * create by yangwm in Mar 22, 2010 5:52:58 PM
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        int port = 9527;
        System.out.println("Server listen on port: " + port);
        
        Selector selector = Selector.open();
        
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int nKeys = selector.select();
            if (nKeys > 0) {
                for (SelectionKey selectionKey : selector.selectedKeys()) {
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel tempServerChannel = (ServerSocketChannel)selectionKey.channel();
                        SocketChannel socketChannel = tempServerChannel.accept();
                        if (socketChannel == null) {
                            continue;
                        }
                        
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        //try { Thread.sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
                        SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                        
                        StringBuilder sb = new StringBuilder();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        try {
                            int readBytes = 0;
                            int ret = 0;
                            while ((ret = socketChannel.read(byteBuffer)) > 0) {
                                readBytes += ret;
                                byteBuffer.flip();
                                
                                sb.append(Charset.forName("UTF-8").decode(byteBuffer).toString());
                                
                                byteBuffer.clear();
                            }
                            
                            if (readBytes == 0) {
                                /*
                                 * handle Exception
                                 */
                                System.err.println("handle opposite close Exception");
                                socketChannel.close();
                            }
                            
                            
                            String message = sb.toString();
                            System.out.println("Message from client: " + message);
                            if ("quit".equalsIgnoreCase(message.toString().trim())) {
                                System.out.println("Client has been quit!");
                                
                                socketChannel.close();
                            } else if ("serverquit".equalsIgnoreCase(message.trim())) {
                                System.out.println("Server has been shutdown!");
                                
                                socketChannel.close();
                                serverChannel.close();
                                selector.close();
                                System.exit(0);
                            } else {
                                String outMessage = "Server response：" + message;
                                socketChannel.write(Charset.forName("UTF-8").encode(outMessage));
                            }
                        } catch (IOException e) {
                            /*
                             * handle Exception
                             */
                            System.err.println("handle read Exception");
                            socketChannel.close();
                        } finally {
                            byteBuffer.clear();
                        }
                        
                    }
                }
                selector.selectedKeys().clear();
                
            }
        }
    }

}

/*
Server listen on port: 9527
Message from client: hello, yangwm, see nio
Message from client: what are you doing?
Message from client: quit
Server has been shutdown!

*/
