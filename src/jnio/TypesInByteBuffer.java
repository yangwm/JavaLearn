/**
 * 
 */
package jnio;

import java.nio.ByteBuffer;

/**
 * get() 和 put() 方法， ByteBuffer 还有用于读写不同类型的值的其他方法
 * 这其中的每个方法都有两种类型 — 一种是相对的，另一种是绝对的。
 * 
 * @author yangwm Apr 29, 2010 6:08:16 PM
 */
public class TypesInByteBuffer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate( 64 );

        buffer.putInt( 30 );
        buffer.putLong( 7000000000000L );
        buffer.putDouble( Math.PI );

        buffer.flip();

        System.out.println( buffer.getInt() );
        System.out.println( buffer.getLong() );
        System.out.println( buffer.getDouble() );
    }

}

/*
30
7000000000000
3.141592653589793

*/
