/**
 * 
 */
package nio.sm;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * 往 "共享内存" 写入数据
 * 
 * @author yangwm Jul 17, 2010 9:29:36 PM
 */
public class WriteShareMemory {

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        RandomAccessFile raf = new RandomAccessFile("D:/study/tempProject/JavaLearn/src/nio/swap.txt", "rw");
        FileChannel fc = raf.getChannel();
        MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, 0, 1024 * 1024 * 256);
        
        //清除文件内容
        for(int i=0;i<1024;i++){
            mbb.put(i,(byte)0);
        }
         
        //从文件的第二个字节开始，依次写入 A-Z 字母，第一个字节指明了当前操作的位置
        for(int i=65;i<91;i++){
            int index = i-63;
            int flag = mbb.get(0); //可读标置第一个字节为 0
            if(flag != 0){ //不是可写标示 0，则重复循环，等待
                i--;
                continue;
            }
            mbb.put(0,(byte)1); //正在写数据，标志第一个字节为 1
            mbb.put(1,(byte)(index)); //写数据的位置
            
            System.out.println("程序 WriteShareMemory：" + System.currentTimeMillis() 
                    + "：位置：" + index +" 写入数据：" + (char)i);
             
            mbb.put(index,(byte)i);//index 位置写入数据
            mbb.put(0,(byte)2); //置可读数据标志第一个字节为 2
            Thread.sleep(100);
        }
    }

}
