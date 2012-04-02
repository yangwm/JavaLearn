/**
 * 
 */
package nio.aio;

import java.io.IOException;   
import java.nio.ByteBuffer;   
import java.nio.channels.AsynchronousSocketChannel;   
import java.nio.channels.CompletionHandler;   
import java.nio.charset.CharacterCodingException;   
import java.nio.charset.Charset;   
import java.nio.charset.CharsetDecoder;   
   
/**
 * @author yangwm in Sep 25, 2009 11:34:42 AM
 */
public class AioReadHandler implements CompletionHandler<Integer, ByteBuffer> {   
    private AsynchronousSocketChannel socket;   
   
    public AioReadHandler(AsynchronousSocketChannel socket) {   
        this.socket = socket;   
    }   
   
    public void cancelled(ByteBuffer attachment) {   
        System.out.println("cancelled");   
    }   
   
    private CharsetDecoder decoder = Charset.forName("GBK").newDecoder();   
   
    public void completed(Integer i, ByteBuffer buf) {   
        if (i > 0) {   
            buf.flip();   
            try {   
                System.out.println("收到" + socket.getRemoteAddress().toString() + "的消息:" + decoder.decode(buf));   
                buf.compact();   
            } catch (CharacterCodingException e) {   
                e.printStackTrace();   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
            socket.read(buf, buf, this);   
        } else if (i == -1) {   
            try {   
                System.out.println("客户端断线:" + socket.getRemoteAddress().toString());   
                buf = null;   
            } catch (IOException e) {   
                e.printStackTrace();   
            }   
        }   
    }   
   
    public void failed(Throwable exc, ByteBuffer buf) {   
        System.out.println(exc);   
    }   
}
