/**
 * 
 */
package nio.pipe;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * 
 * 
 * @author yangwm Jul 17, 2010 1:27:32 PM
 */
public class PipeStreamSample {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        PipedOutputStream output = new PipedOutputStream();
        PipedInputStream input = new PipedInputStream(output);
        
        output.write("Hello yangwm, PipeStream!".getBytes("utf-8"), 0, 25);
        
        byte[] buffer = new byte[25];
        input.read(buffer);
        System.out.println("Content:" + new String(buffer, "UTF-8"));
    }

}
