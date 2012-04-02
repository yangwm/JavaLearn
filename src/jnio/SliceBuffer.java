/**
 * 
 */
package jnio;

import java.nio.ByteBuffer;

/**
 * slice() 方法根据现有的缓冲区创建一种 子缓冲区，新缓冲区与原来的缓冲区的一部分共享数据。 
 * 
 * @author yangwm Apr 29, 2010 6:15:48 PM
 */
public class SliceBuffer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate( 10 );

        for (int i=0; i<buffer.capacity(); ++i) {
          buffer.put( (byte)i );
        }

        buffer.position( 3 );
        buffer.limit( 7 );

        ByteBuffer slice = buffer.slice();

        for (int i=0; i<slice.capacity(); ++i) {
            byte b = slice.get( i );
            b *= 11;
            slice.put( i, b );
        }

        buffer.position( 0 );
        buffer.limit( buffer.capacity() );

        while (buffer.remaining()>0) {
          System.out.println( buffer.get() );
        }
    }

}

/*
0
1
2
33
44
55
66
7
8
9

*/
