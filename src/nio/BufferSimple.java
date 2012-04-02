/**
 * 
 */
package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

/**
 * @author yangwm in Sep 24, 2009 9:41:43 AM
 */
public class BufferSimple {
    
    private static final int BSIZE = 1024; 
    
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class ReadWritePrimitiveUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
            writePrimitive(buffer);
            readPrimitive(buffer);
        }
    }
    
    /**
     * create by yangwm in Sep 24, 2009 4:43:48 PM
     * @param file
     * @throws IOException
     */
    public static void writePrimitive(ByteBuffer buffer) throws IOException {
        buffer.putInt(10).putLong(99L).putDouble(29.01).putChar('c');
    }

    /**
     * create by yangwm in Sep 24, 2009 4:43:51 PM
     * @param file
     * @throws IOException
     */
    public static void readPrimitive(ByteBuffer buffer) throws IOException {
        buffer.flip();
        System.out.println(buffer.getInt() + ", " + buffer.getLong() + ", " 
                + buffer.getDouble() + ", " + buffer.getChar());
    }

    
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class UseCharBufferViewUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            useCharBufferView();
        }
    }
    /*
    0, 5
    5 512 512
    Hwdy!
    0 5 512
    java.nio.HeapByteBuffer[pos=0 lim=1024 cap=1024]
     */
        
    /**
     * create by yangwm in Sep 24, 2009 9:45:15 AM
     * @param file
     * @throws IOException
     */
    public static void useCharBufferView() {
        /*
         * 
         */
        ByteBuffer bb = ByteBuffer.allocate(1024); 
        CharBuffer cb = bb.asCharBuffer(); //通过ByteBuffer创建新的CharBuffer视图, 对CharBuffer视图操作不会影响bb的limit和positionbb的position 

        cb.put("Hwdy!"); //字符串中的每个字符占1个字节！共占5个字节!所以此句会使cb的position移动5个字节, 但不会移动bb的position 
        System.out.println(bb.position() + ", " + cb.position()); //所以bb的postion值为0, cb的position值为5 
        
        System.out.println(cb.position() + " " + cb.limit() + " " + cb.capacity());
        cb.flip(); // 为cb写入标准输入流做准备 
        System.out.println(cb);
        System.out.println(cb.position() + " " + cb.limit() + " " + cb.capacity());
        System.out.println(bb.toString());
    }

    
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class UseBufferSliceUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            useBufferSlice();
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
        
    /**
     * create by yangwm in Sep 24, 2009 9:45:17 AM
     * @throws IOException
     */
    public static void useBufferSlice() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(10); 
        for (int i = 0; i < buffer.capacity(); i++) { 
             buffer.put((byte)i); 
        }
        
        buffer.position(3); 
        buffer.limit(7); 
        ByteBuffer slice = buffer.slice(); 
        for (int i = 0; i < slice.capacity(); i++) { 
             byte b = slice.get(i); 
             b *= 11; 
             slice.put(i, b); 
        }
        
        buffer.position(0); 
        buffer.limit(buffer.capacity()); 
        while (buffer.hasRemaining()) { 
             System.out.println(buffer.get()); 
        }
    }

    
    /**
     * @author yangwm in Sep 24, 2009 10:28:35 AM
     */
    static class UseBufferUsage {
        /**
         * create by yangwm in Sep 24, 2009 9:41:43 AM
         * @param args
         * @throws IOException 
         */
        public static void main(String[] args) throws IOException {
            useBuffer();
        }
    }
    /*
    java.nio.HeapByteBuffer[pos=0 lim=18 cap=18]
    86
    */
        
    /**
     * create by yangwm in Sep 24, 2009 9:45:17 AM
     * @throws IOException
     */
    public static void useBuffer() throws IOException {
        String text = "Value of e";
        ByteBuffer buf = ByteBuffer.allocate(text.length() + 8/*sizeof(Math.E)*/);
        buf.put(text.getBytes()).putDouble(Math.E);

        buf.flip(); // 为buf写入标准输入流做准备 
        System.out.println(buf.toString());
        System.out.println(buf.get());
        
    }
    
}

