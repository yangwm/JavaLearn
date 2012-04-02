/**
 * 
 */
package nio.aio;

import java.io.IOException;   
import java.nio.ByteBuffer;   
import java.nio.channels.AsynchronousServerSocketChannel;   
import java.nio.channels.AsynchronousSocketChannel;   
import java.nio.channels.CompletionHandler;   
import java.util.concurrent.ExecutionException;   
import java.util.concurrent.Future;   
   
/**
 * @author yangwm in Sep 25, 2009 11:33:36 AM
 */
public class AioAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {   
    public void cancelled(AsynchronousServerSocketChannel attachment) {   
        System.out.println("cancelled");   
    }   
   
    public void completed(AsynchronousSocketChannel socket, AsynchronousServerSocketChannel attachment) {   
        try {   
            attachment.accept(attachment, this);//此方法有点递归的意思.目的是继续侦听端口,由channelGroup负责执行.   
            System.out.println("有客户端连接:" + socket.getRemoteAddress().toString());   
            startRead(socket);   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
    }   
   
    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {   
        exc.printStackTrace();   
    }   
   
    public void startRead(AsynchronousSocketChannel socket) {   
        ByteBuffer clientBuffer = ByteBuffer.allocate(1024);   
        Future<Integer> future = socket.read(clientBuffer, clientBuffer, new AioReadHandler(socket));   
        try {   
            future.get();   
        } catch (InterruptedException e) {   
            e.printStackTrace();   
        } catch (ExecutionException e) {   
            e.printStackTrace();   
        }   
    }   
}  
