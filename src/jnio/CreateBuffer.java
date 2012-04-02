/**
 * 
 */
package jnio;

import java.nio.ByteBuffer;

/**
 * 
 * 
 * @author yangwm May 3, 2010 10:50:55 PM
 */
public class CreateBuffer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate( 1024 );

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
