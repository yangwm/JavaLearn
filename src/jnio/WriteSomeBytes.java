/**
 * 
 */
package jnio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * write some byte
 * 
 * @author yangwm May 3, 2010 10:56:47 PM
 */
public class WriteSomeBytes {

    static private final byte message[] = { 83, 111, 109, 101, 32,
        98, 121, 116, 101, 115, 46 };

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        byte[] result = ("hello, " + new String("abc")).getBytes();
        System.out.println("server result:" + new String(result));
        
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );
        buffer.put(result);
        buffer.flip();
        System.out.println("server buffer:" + new String(buffer.array()));
    }

}
