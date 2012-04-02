/**
 * 
 */
package nio.pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.Charset;

/**
 * 
 * 
 * @author yangwm Jul 17, 2010 12:40:20 PM
 */
public class PipeSample {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel psic = pipe.sink();
        Pipe.SourceChannel psoc = pipe.source();
        
        psic.write(ByteBuffer.wrap("Hello yangwm, Pipe!".getBytes("utf-8")));
        
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        psoc.read(byteBuffer);
        byteBuffer.flip();
        System.out.println("Content:" + Charset.forName("UTF-8").decode(byteBuffer).toString());
    }

}
