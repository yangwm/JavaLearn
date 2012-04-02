/**
 * 
 */
package jnio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 事实上，文件锁就像常规的 Java 对象锁 — 它们是 劝告式的（advisory） 锁。
 * 
 * 要获取文件的一部分上的锁，FileChannel 上的 lock() 方法。注意，如果要获取一个排它锁，您必须以写方式打开文件。 
 * 在释放锁后，尝试获得锁的其他任何程序都有机会获得它。 
 * 
 * @author yangwm Apr 30, 2010 11:44:33 AM
 */
public class UseFileLocks {
    static private final int start = 10;
    static private final int end = 20;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        // Get file channel
        RandomAccessFile raf = new RandomAccessFile("usefilelocks.txt", "rw");
        FileChannel fc = raf.getChannel();

        // Get lock
        System.out.println("trying to get lock");
        FileLock lock = fc.lock(start, end, false);
        System.out.println("got lock!");

        // Pause
        System.out.println("pausing");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ie) {
        }

        // Release lock
        System.out.println("going to release lock");
        lock.release();
        System.out.println("released lock");

        raf.close();
    }

}

/*
连续2次 java UseFileLocks， 看效果
*/

