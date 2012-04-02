/**
 * 
 */
package jnio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 
 * 使用 clear() 和 flip() 方法重设缓冲区，并准备缓冲区以便将新读取的数据写到另一个通道中。
 * 
 * 现在我们要将数据写到输出通道中。在这之前，我们必须调用 flip() 方法。这个方法做两件非常重要的事： 
 * 1. 它将 limit 设置为当前 position。 
 * 2. 它将 position 设置为 0。 
 * 
 * 最后一步是调用缓冲区的 clear() 方法。这个方法重设缓冲区以便接收更多的字节。 Clear 做两种非常重要的事情： 
 * 1. 它将 limit 设置为与 capacity 相同。 
 * 2. 它设置 position 为 0。 
 * 
 * @author yangwm Apr 29, 2010 4:50:49 PM
 */
public class CopyFile {

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        if (args.length<2) {
            System.err.println( "Usage: java CopyFile infile outfile" );
            System.exit( 1 );
        }
        
        String infile = args[0];
        String outfile = args[1];
        
        FileInputStream fin = new FileInputStream(infile);
        FileOutputStream fout = new FileOutputStream(outfile);
        
        FileChannel fcin = fin.getChannel();
        FileChannel fcout = fout.getChannel();
        
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        
        /*
         * 下面的内部循环概括了使用缓冲区将数据从输入通道拷贝到输出通道的过程。 
         * read() 和 write() 调用得到了极大的简化，因为许多工作细节都由缓冲区完成了。 
         * clear() 和 flip() 方法用于让缓冲区在读和写之间切换。 
         */
        while (true) {
            buffer.clear();
            
            int r = fcin.read(buffer);
            
            // 当没有更多的数据时，拷贝就算完成，并且可以在 read() 方法返回 -1 是判断这一点
            if (r <= 0) {//r == -1
                break;
            }
            
            buffer.flip();
            
            fcout.write(buffer);
        }
        
    }

}

/*
java jnio.CopyFile copyFileIn.txt copyFileOut.txt

*/

