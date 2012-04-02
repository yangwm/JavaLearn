/**
 * 
 */
package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author yangwm in Sep 24, 2009 11:09:15 AM
 */
public class FileChannelSimple {
    
    private static final int BSIZE = 1024; 
    
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class ReadWritePrimitiveFileUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            writePrimitiveFile("D:/study/tempProject/JavaLearn/src/nio/primitive.txt");
            readPrimitiveFile("D:/study/tempProject/JavaLearn/src/nio/primitive.txt");
        }
    }
    
    /**
     * create by yangwm in Sep 24, 2009 4:43:48 PM
     * @param file
     * @throws IOException
     */
    public static void writePrimitiveFile(String file) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        buffer.putInt(10).putLong(10L).putDouble(29.01).putChar('c');
        
        FileOutputStream fos = new FileOutputStream(file);
        FileChannel outChannel = fos.getChannel();
        buffer.flip();
        outChannel.write(buffer);
        
        outChannel.close();
        fos.close();
    }

    /**
     * create by yangwm in Sep 24, 2009 4:43:51 PM
     * @param file
     * @throws IOException
     */
    public static void readPrimitiveFile(String file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        FileChannel inChannel = fis.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        inChannel.read(buffer);

        buffer.flip();
        System.out.println(buffer.getInt() + ", " + buffer.getLong() + ", " 
                + buffer.getDouble() + ", " + buffer.getChar());
        
        inChannel.close();
        fis.close();
    }

    
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class ReadFileUsingBufferUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            readFileUsingBuffer("D:/study/tempProject/JavaLearn/src/nio/readFileUsingBuffer.txt");
        }
    }
    
    /**
     * read file using buffer
     * 
     * create by yangwm in Sep 24, 2009 9:38:15 AM
     * @param src
     * @param dest
     * @throws IOException 
     * @throws Exception
     */
    public static void readFileUsingBuffer(String file) throws IOException {
        FileInputStream srcStream = new FileInputStream(file);
        FileChannel inChannel=srcStream.getChannel();
        
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE); 
        while(inChannel.read(buffer) != -1) {           //buffer 用来向FileChannel读取数据 
            buffer.flip(); // 为buffer写入通道做准备 
            System.out.println(buffer.asCharBuffer().toString()); //从buffer中提取出数据 
            
            /*String str = "";
            int i = 0;
            while (i < (buffer.limit()-20)) {
                i++;
                str += buffer.getChar();
            }
            System.out.println(str);*/
            buffer.clear();
        } 

        inChannel.close();
        srcStream.close();
    }
        

    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class CopyFileUsingBufferUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            copyFileUsingBuffer("D:/study/tempProject/JavaLearn/src/nio/copyFileUsingBuffer.txt", 
                "D:/study/tempProject/JavaLearn/src/nio/copyFileUsingBuffer2.txt");
        }
    }
    
    /**
     * copy file using buffer
     * 
     * create by yangwm in Sep 24, 2009 9:38:15 AM
     * @param src
     * @param dest
     * @throws IOException 
     * @throws Exception
     */
    public static void copyFileUsingBuffer(String src, String dest) throws IOException {
        FileInputStream srcStream = new FileInputStream(src);
        FileOutputStream destStream = new FileOutputStream(dest);
        FileChannel inChannel=srcStream.getChannel();
        FileChannel outChannel=destStream.getChannel();
        
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE); 
        while(inChannel.read(buffer) != -1) {           //buffer 用来向FileChannel读取数据 
            buffer.flip(); // 为buffer写入通道做准备 
            outChannel.write(buffer); //buffer 用来向FileChannel写入数据 
            buffer.clear();           // 为通道再次读取做准备 
        } 

        inChannel.close();
        outChannel.close();
        srcStream.close();
        destStream.close();
    }

    
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class CopyFileUsingTransferUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            copyFileUsingTransfer("D:/study/tempProject/JavaLearn/src/nio/copyFileUsingTransfer.txt", 
                "D:/study/tempProject/JavaLearn/src/nio/copyFileUsingTransfer2.txt");
        }
    }
    
    /**
     * copy file using transfer(防止没有一次传送所有的数据，使用while循环来处理)
     * 
     * create by yangwm in Sep 24, 2009 9:38:15 AM
     * @param src
     * @param dest
     * @throws IOException 
     * @throws Exception
     */
    public static void copyFileUsingTransfer(String src, String dest) throws IOException {
        FileInputStream srcStream = new FileInputStream(src);
        FileOutputStream destStream = new FileOutputStream(dest);
        FileChannel inChannel=srcStream.getChannel();
        FileChannel outChannel=destStream.getChannel();
        
        long byteTransfered = 0;
        long byteCount = inChannel.size();

        /*while (byteTransfered < byteCount) {
            byteTransfered += inChannel.transferTo(byteTransfered, byteCount - byteTransfered, outChannel);
        }*/
      
        while (byteTransfered < byteCount) {
            byteTransfered += outChannel.transferFrom(inChannel, byteTransfered, byteCount - byteTransfered);
        }/**/

        inChannel.close();
        outChannel.close();
        srcStream.close();
        destStream.close();
    }

        
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class ChannelReadWriteUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            channelReadWrite("D:/study/tempProject/JavaLearn/src/nio/channelReadWrite.txt");
        }
    }
    
    /**
     * random read write file 
     * 
     * create by yangwm in Sep 24, 2009 9:38:15 AM
     * @param src
     * @param dest
     * @throws IOException 
     * @throws Exception
     */
    public static void channelReadWrite(String file) throws IOException {
        // Write a file: 
        FileOutputStream outStream = new FileOutputStream(file);
        FileChannel outChannel = outStream.getChannel(); 
        outChannel.write(ByteBuffer.wrap("Some text ".getBytes())); 
        outChannel.close();
        outStream.close();
        
        // Add to the end of the file: 
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        FileChannel rafChannel = raf.getChannel(); 
        rafChannel.position(rafChannel.size()); // 将通道位置移动到最后，以便我们在通道后面继续写入新数据 
        rafChannel.write(ByteBuffer.wrap("Some more".getBytes())); 
        rafChannel.close(); 
        raf.close();
        
        // Read the file: 
        FileInputStream inStream = new FileInputStream(file);
        FileChannel inChannel = inStream.getChannel(); 
        ByteBuffer buff = ByteBuffer.allocate(BSIZE); 
        inChannel.read(buff); //把通道中的字节序列读入buff中 

        //进行此操作后，buff的位置移动了从FileChannel中读取的字节个数！ 
        buff.flip();           //为下面的buff.get()做准备，将buff的限制设置为当前位置，然后将位置设置为 0 
        //因为我们从buff读取，是从此buff的位置处一直读到限制处! 
        while(buff.hasRemaining()) {
            System.out.print((char)buff.get());
        }
        
        inChannel.close();
        inStream.close();
    }


    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class UseFileMappedUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            useFileMapped("D:/study/tempProject/JavaLearn/src/nio/useFileMapped.txt");
        }
    }
        
    /**
     * file mapped into memory
     * 
     * create by yangwm in Sep 24, 2009 9:45:15 AM
     * @param file
     * @throws IOException
     */
    public static void useFileMapped(String file) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw"); 
        FileChannel inChannel = raf.getChannel(); 

        MappedByteBuffer mbb = inChannel.map(FileChannel.MapMode.READ_WRITE, 0, BSIZE); 
        //MappedByteBuffer mbb = inChannel.map(FileChannel.MapMode.READ_WRITE, 0, BSIZE).load(); 

        mbb.put(0, (byte)97); 
        mbb.put(BSIZE - 1, (byte)122); 

        inChannel.close();
        raf.close(); 
    }

}
