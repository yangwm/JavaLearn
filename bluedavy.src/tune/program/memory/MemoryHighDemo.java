/**
 * 
 */
package tune.program.memory;

import java.nio.ByteBuffer;

/**
 * direct bytebuffer消耗的是jvm堆外的内存，但同样是基于GC方式来释放的。
 * 
 * @author yangwm Aug 21, 2010 9:40:18 PM
 */
public class MemoryHighDemo {
    
    public static void main(String[] args) throws Exception{
        int t = 1000;
        
        Thread.sleep(20 * t);
        System.out.println("read to create bytes,so jvm heap will be used");
        byte[] bytes=new byte[128*1000*1000];
        bytes[0]=1;
        bytes[1]=2;
        Thread.sleep(10 * t);
        System.out.println("read to allocate & put direct bytebuffer,no jvm heap should be used");
        ByteBuffer buffer=ByteBuffer.allocateDirect(128*1024*1024);
        buffer.put(bytes);
        buffer.flip();
        Thread.sleep(10 * t);
        System.out.println("ready to gc,jvm heap will be freed");
        bytes=null;
        System.gc();
        Thread.sleep(10 * t);
        System.out.println("read to get bytes,then jvm heap will be used");
        byte[] resultbytes=new byte[128*1000*1000];
        buffer.get(resultbytes);
        System.out.println("resultbytes[1] is: "+resultbytes[1]);
        Thread.sleep(10 * t);
        System.out.println("read to gc all");
        buffer=null;
        resultbytes=null;
        System.gc();
        Thread.sleep(10 * t);
    }
    
}

/*
D:\study\tempProject\JavaLearn\classes>java -Xms140M -Xmx140M tune.MemoryHighDemo
read to create bytes,so jvm heap will be used
read to allocate & put direct bytebuffer,no jvm heap should be used
ready to gc,jvm heap will be freed
read to get bytes,then jvm heap will be used
resultbytes[1] is: 2
read to gc all

*/
