/**
 * 
 */
package jnio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * read and show 
 * 
 * @author yangwm May 3, 2010 10:52:32 PM
 */
public class ReadAndShow {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream("copyFileIn.txt");
        FileChannel fc = fin.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        fc.read(buffer);

        buffer.flip();

        int i = 0;
        while (buffer.remaining() > 0) {
            byte b = buffer.get();
            System.out.println("Character " + i + ": " + ((char) b));
            i++;
        }

        fin.close();
    }

}

/*
Character 0: 

Character 1: 

Character 2: j
Character 3: -
Character 4: n
Character 5: i
Character 6: o
Character 7:  
Character 8: c
Character 9: o
Character 10: p
Character 11: y
Character 12:  
Character 13: f
Character 14: i
Character 15: l
Character 16: e
Character 17:  
Character 18: 

Character 19: 

Character 20: 

Character 21: 

*/

