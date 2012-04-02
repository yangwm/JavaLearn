/**
 * 
 */
package jnio;

import java.nio.ByteBuffer;

/**
 * 
 * 
 * @author yangwm May 3, 2010 10:51:52 PM
 */
public class CreateArrayBuffer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        byte array[] = new byte[1024];

        ByteBuffer buffer = ByteBuffer.wrap( array );

        buffer.put( (byte)'a' );
        buffer.put( (byte)'b' );
        buffer.put( (byte)'c' );

        buffer.flip();

        System.out.println( (char)buffer.get() );
        System.out.println( (char)buffer.get() );
        System.out.println( (char)buffer.get() );
    }

}

/*
a
b
c

*/
