/**
 * 
 */
package nio.aio;

import java.io.IOException;   
import java.net.InetSocketAddress;   
import java.nio.ByteBuffer;   
import java.nio.channels.AsynchronousChannelGroup;   
import java.nio.channels.AsynchronousSocketChannel;   
import java.nio.channels.CompletionHandler;   
import java.nio.charset.CharacterCodingException;   
import java.nio.charset.Charset;   
import java.nio.charset.CharsetDecoder;   
import java.util.Timer;   
import java.util.TimerTask;   
import java.util.concurrent.ExecutionException;   
import java.util.concurrent.ExecutorService;   
import java.util.concurrent.Executors;   
import java.util.concurrent.Future;   
   
/**
 * @author yangwm in Sep 25, 2009 11:40:03 AM
 */
public class AioTcpConnector {   
    private AsynchronousChannelGroup asyncChannelGroup;   
    private AsynchronousSocketChannel connector;   
   
    public AioTcpConnector() throws Exception {   
        ExecutorService executor = Executors.newFixedThreadPool(20);   
        asyncChannelGroup = AsynchronousChannelGroup.withThreadPool(executor);   
    }   
   
    private final CharsetDecoder decoder = Charset.forName("GBK").newDecoder();   
   
    public void start(final String ip, final int port) throws Exception {   
        Timer timer = new Timer();   
        timer.scheduleAtFixedRate(new TimerTask() {   
            @Override   
            public void run() {   
                try {   
                    if (connector == null || !connector.isOpen()) {   
                        connector = AsynchronousSocketChannel.open(asyncChannelGroup);   
                        //connector.setOption(StandardSocketOption.TCP_NODELAY, true);   
                        //connector.setOption(StandardSocketOption.SO_REUSEADDR, true);   
                        //connector.setOption(StandardSocketOption.SO_KEEPALIVE, true);   
                        Future<Void> future = connector.connect(new InetSocketAddress(ip, port));   
                        try {   
                            future.get();   
                            handlerRead();   
                        } catch (InterruptedException e) {   
                            e.printStackTrace();   
                        } catch (ExecutionException e) {   
                            System.out.println("尝试连接失败!");   
                        }   
                    }   
                } catch (IOException e) {   
                    e.printStackTrace();   
                }   
            }   
        }, 1, 10 * 1000);   
    }   
   
    public void handlerRead() throws InterruptedException, ExecutionException {   
        try {   
            System.out.println("与服务器连接成功:" + connector.getRemoteAddress());   
        } catch (IOException e1) {   
            e1.printStackTrace();   
        }   
        final ByteBuffer buf = ByteBuffer.allocate(1024);   
        Future<Integer> rows = connector.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {   
            public void cancelled(ByteBuffer attachment) {   
                System.out.println("cancelled");   
            }   
   
            public void completed(Integer i, ByteBuffer in) {   
                if (i > 0) {   
                    in.flip();   
                    try {   
                        System.out.println("收到" + connector.getRemoteAddress().toString() + "的消息:" + decoder.decode(in));   
                        in.compact();   
                    } catch (CharacterCodingException e) {   
                        e.printStackTrace();   
                    } catch (IOException e) {   
                        e.printStackTrace();   
                    }   
                    connector.read(in, in, this);   
                } else if (i == -1) {   
                    try {   
                        System.out.println("与服务器连接断线:" + connector.getRemoteAddress());   
                        connector.close();   
                    } catch (IOException e) {   
                        e.printStackTrace();   
                    }   
                    in = null;   
                } else if (i == 0) {   
                    System.out.println(i);   
                }   
            }   
   
            public void failed(Throwable exc, ByteBuffer buf) {   
                System.out.println(exc);   
            }   
        });   
        rows.get();   
    }   
   
    public static void main(String... args) throws Exception {   
        AioTcpConnector client = new AioTcpConnector();   
        client.start("localhost", 9998);   
    }   
}
