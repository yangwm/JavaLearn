/**
 * 
 */
package jnio;

import java.nio.FloatBuffer;

/**
 * use FloatBuffer 
 * 
 * @author yangwm Apr 29, 2010 4:40:15 PM
 */
public class UseFloatBuffer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        FloatBuffer buffer = FloatBuffer.allocate(10);
        
        for (int i = 0; i < buffer.capacity(); i++) {
            float f = (float) Math.sin( (((float)i)/10)*(2*Math.PI) );
            buffer.put(f);
        }
        
        buffer.flip();
        
        while (buffer.hasRemaining()) {
            float f = buffer.get();
            System.out.println(f);
        }
    }

}

/*
0.0
0.58778524
0.95105654
0.9510565
0.58778524
1.2246469E-16
-0.58778536
-0.9510565
-0.9510565
-0.58778536

*/

