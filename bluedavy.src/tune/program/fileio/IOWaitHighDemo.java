/**
 * 
 */
package tune.program.fileio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * 文件IO消耗严重的原因主要是多个线程在写进行大量的数据到同一文件，
 * 导致文件很快变得很大，从而写入速度越来越慢，并造成各线程激烈争抢文件锁。
 * 
 * @author yangwm Aug 21, 2010 9:48:34 PM
 */
public class IOWaitHighDemo {
    private String fileName = "iowait.log";
    
    private static int threadCount = Runtime.getRuntime().availableProcessors();
    
    private Random random = new Random();
    
    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            threadCount = Integer.parseInt(args[1]);
        }
        
        IOWaitHighDemo demo = new IOWaitHighDemo();
        demo.runTest();
    }
    
    private void runTest() throws Exception {
        File file = new File(fileName);
        file.createNewFile();
        
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Task()).start();
        }
        
    }
    
    class Task implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    StringBuilder strBuilder = new StringBuilder("====begin====\n");
                    String threadName = Thread.currentThread().getName();
                    for (int i = 0; i < 100000; i++) {
                        strBuilder.append(threadName);
                        strBuilder.append("\n");
                    }
                    strBuilder.append("====end====\n");
                    
                    BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
                    writer.write(strBuilder.toString());
                    writer.close();
                    Thread.sleep(random.nextInt(10));
                } catch (Exception e) {
                    
                }
            }
        }
        
    }
    
}

/*
C:\Documents and Settings\yangwm>jstack 2656
2010-08-21 23:24:17
Full thread dump Java HotSpot(TM) Client VM (17.0-b05 mixed mode):

"DestroyJavaVM" prio=6 tid=0x00868c00 nid=0xde0 waiting on condition [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Thread-1" prio=6 tid=0x0ab9dc00 nid=0xb7c runnable [0x0b0bf000]
   java.lang.Thread.State: RUNNABLE
        at java.io.FileOutputStream.close0(Native Method)
        at java.io.FileOutputStream.close(FileOutputStream.java:336)
        at sun.nio.cs.StreamEncoder.implClose(StreamEncoder.java:320)
        at sun.nio.cs.StreamEncoder.close(StreamEncoder.java:149)
        - locked <0x034dd268> (a java.io.FileWriter)
        at java.io.OutputStreamWriter.close(OutputStreamWriter.java:233)
        at java.io.BufferedWriter.close(BufferedWriter.java:265)
        - locked <0x034dd268> (a java.io.FileWriter)
        at tune.IOWaitHighDemo$Task.run(IOWaitHighDemo.java:58)
        at java.lang.Thread.run(Thread.java:717)

"Thread-0" prio=6 tid=0x0ab9d400 nid=0x80c runnable [0x0b06f000]
   java.lang.Thread.State: RUNNABLE
        at java.io.FileOutputStream.writeBytes(Native Method)
        at java.io.FileOutputStream.write(FileOutputStream.java:292)
        at sun.nio.cs.StreamEncoder.writeBytes(StreamEncoder.java:221)
        at sun.nio.cs.StreamEncoder.implWrite(StreamEncoder.java:282)
        at sun.nio.cs.StreamEncoder.write(StreamEncoder.java:125)
        - locked <0x034e1290> (a java.io.FileWriter)
        at java.io.OutputStreamWriter.write(OutputStreamWriter.java:207)
        at java.io.BufferedWriter.flushBuffer(BufferedWriter.java:128)
        - locked <0x034e1290> (a java.io.FileWriter)
        at java.io.BufferedWriter.write(BufferedWriter.java:229)
        - locked <0x034e1290> (a java.io.FileWriter)
        at java.io.Writer.write(Writer.java:157)
        at tune.IOWaitHighDemo$Task.run(IOWaitHighDemo.java:57)
        at java.lang.Thread.run(Thread.java:717)

"Low Memory Detector" daemon prio=6 tid=0x0ab6f800 nid=0xfb0 runnable [0x00000000]
   java.lang.Thread.State: RUNNABLE

"CompilerThread0" daemon prio=10 tid=0x0ab6c800 nid=0x5fc waiting on condition [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" daemon prio=10 tid=0x0ab67800 nid=0x6fc waiting on condition [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" daemon prio=10 tid=0x0ab66800 nid=0x5a0 runnable [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" daemon prio=8 tid=0x0ab54000 nid=0xe74 in Object.wait() [0x0ac8f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x02f15d90> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:135)
        - locked <0x02f15d90> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:151)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:177)

"Reference Handler" daemon prio=10 tid=0x0ab4f800 nid=0x8a4 in Object.wait() [0x0ac3f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x02f15af8> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:133)
        - locked <0x02f15af8> (a java.lang.ref.Reference$Lock)

"VM Thread" prio=10 tid=0x0ab4a800 nid=0x1d0 runnable

"VM Periodic Task Thread" prio=10 tid=0x0ab7d400 nid=0x464 waiting on condition

JNI global references: 693


C:\Documents and Settings\yangwm>

*/
