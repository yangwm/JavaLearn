/**
 * 
 */
package jnio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 分散/聚集 I/O 对于将数据划分为几个部分很有用。
 * 
 * @author yangwm Apr 30, 2010 9:27:34 AM
 */
public class UseScatterGather {
    static private final int firstHeaderLength = 2;
    static private final int secondHeaderLength = 4;
    static private final int bodyLength = 6;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args.length!=1) {
            System.err.println( "Usage: java UseScatterGather port" );
            System.exit( 1 );
        }
        
        int port = Integer.parseInt( args[0] );
        
        ServerSocketChannel ssc = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress( port );
        ssc.socket().bind( address );
        
        int messageLength = firstHeaderLength + secondHeaderLength + bodyLength;
        
        ByteBuffer buffers[] = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate( firstHeaderLength );
        buffers[1] = ByteBuffer.allocate( secondHeaderLength );
        buffers[2] = ByteBuffer.allocate( bodyLength );
        
        SocketChannel sc = ssc.accept();

        while (true) {
            
            // Scatter-read into buffers
            int bytesRead = 0;
            while (bytesRead < messageLength) {
                long r = sc.read( buffers );
                
                if (r <= 0) {//r == -1
                    break;
                }
                
                bytesRead += r;
                System.out.println( "r "+r );
                
                for (int i=0; i<buffers.length; ++i) {
                    ByteBuffer bb = buffers[i];
                    System.out.println( "b "+i+" "+bb.position()+" "+bb.limit() );
                }
            }
            
            // Process message here
            
            // Flip buffers
            for (int i=0; i<buffers.length; ++i) {
                ByteBuffer bb = buffers[i];
                bb.flip();
            }
            
            // Scatter-write back out
            long bytesWritten = 0;
            while (bytesWritten<messageLength) {
                long r = sc.write( buffers );
                
                if (r <= 0) {
                    break;
                }
                
                bytesWritten += r;
                System.out.println( "r "+r );
            }
            
            // Clear buffers
            for (int i=0; i<buffers.length; ++i) {
                ByteBuffer bb = buffers[i];
                bb.clear();
            }
            
            System.out.println( bytesRead+" "+bytesWritten+" "+messageLength );
        }
    }

}

/*
java UseScatterGather 5555

r 1
b 0 1 2
b 1 0 4
b 2 0 6
r 1
b 0 2 2
b 1 0 4
b 2 0 6
r 1
b 0 2 2
b 1 1 4
b 2 0 6
r 3
3 3 12
Exception in thread "main" java.io.IOException: 您的主机中的软件放弃了一个已建立的连接。
    at sun.nio.ch.SocketDispatcher.readv0(Native Method)
    at sun.nio.ch.SocketDispatcher.readv(SocketDispatcher.java:47)
    at sun.nio.ch.IOUtil.read(IOUtil.java:307)
    at sun.nio.ch.SocketChannelImpl.read0(SocketChannelImpl.java:404)
    at sun.nio.ch.SocketChannelImpl.read(SocketChannelImpl.java:427)
    at java.nio.channels.SocketChannel.read(SocketChannel.java:469)
    at jnio.UseScatterGather.main(UseScatterGather.java:52)


telnet 127.0.0.1 5555
ABC
*/

