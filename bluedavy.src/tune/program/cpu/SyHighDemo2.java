/**
 * 
 */
package tune.program.cpu;

import java.util.Random;

/**
 * CPU sy 高的原因主要是线程的运行状态要经常切换，对于这种情况，常见的一种优化方法是减少线程数。
 * 
 * 调优方案： 将线程数降低，传入参数200 
 * 
 * @author yangwm in Mar 29, 2010 11:57:32 AM
 */
public class SyHighDemo2 {
    private static int threadCount=200;
    
    /**
    * @param args
    */
    public static void main(String[] args) throws Exception {
        if(args.length==1){
            threadCount=Integer.parseInt(args[0]);
        }
        
        SyHighDemo2 demo = new SyHighDemo2();
        demo.runTest();
    }
    
    private Random random = new Random();
    private Object[] locks;
    
    private void runTest() throws Exception {
        locks = new Object[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            locks[i] = new Object();
        }
        
        for (int i = 0; i < threadCount; i++) {
            new Thread(new ATask(i)).start();
            new Thread(new BTask(i)).start();
        }
    }
    

    class ATask implements Runnable {
        private Object lockObject = null;
        
        public ATask(int i) {
            lockObject = locks[i];
        }
        
        public void run() {
            while (true) {
                try {
                    synchronized (lockObject) {
                        lockObject.wait(random.nextInt(10));
                    }
                } catch (Exception e) {
                    ;
                }
            }
        }
    }
    
    class BTask implements Runnable {
        private Object lockObject = null;
        
        public BTask(int i) {
            lockObject = locks[i];
        }
        
        public void run() {
            while (true) {
                synchronized (lockObject) {
                    lockObject.notifyAll();
                }
                
                try {
                    Thread.sleep(random.nextInt(5));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

//consume cpu 1%--3%

/*
D:\Program Files\Java\jdk1.6.0_13\bin>jstack 4092
2010-03-29 14:14:06
Full thread dump Java HotSpot(TM) Client VM (11.3-b02 mixed mode, sharing):

"DestroyJavaVM" prio=6 tid=0x00848800 nid=0x209c waiting on condition [0x00000000..0x0092fd4c]
   java.lang.Thread.State: RUNNABLE

"Thread-399" prio=6 tid=0x09a7f000 nid=0x2cc8 waiting on condition [0x0b11f000..0x0b11fc14]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.SyHighDemo2$BTask.run(SyHighDemo2.java:81)
        at java.lang.Thread.run(Thread.java:619)

"Thread-398" prio=6 tid=0x09a7dc00 nid=0x2cc4 in Object.wait() [0x0b0cf000..0x0b0cfa14]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        at tune.SyHighDemo2$ATask.run(SyHighDemo2.java:58)
        - locked <0x22a29750> (a java.lang.Object)
        at java.lang.Thread.run(Thread.java:619)

......


"Thread-1" prio=6 tid=0x02b22800 nid=0x2c20 waiting on condition [0x02ebf000..0x02ebfb14]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.SyHighDemo2$BTask.run(SyHighDemo2.java:81)
        at java.lang.Thread.run(Thread.java:619)

"Thread-0" prio=6 tid=0x02b39800 nid=0x1acc in Object.wait() [0x02e6f000..0x02e6fb94]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        at tune.SyHighDemo2$ATask.run(SyHighDemo2.java:58)
        - locked <0x22a29118> (a java.lang.Object)
        at java.lang.Thread.run(Thread.java:619)

"Low Memory Detector" daemon prio=6 tid=0x02b14400 nid=0x2c1c runnable [0x00000000..0x00000000]
   java.lang.Thread.State: RUNNABLE

"CompilerThread0" daemon prio=10 tid=0x02b0d800 nid=0x12bc waiting on condition [0x00000000..0x02d7f
940]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" daemon prio=10 tid=0x02b08c00 nid=0x1468 waiting on condition [0x00000000..0x00000
000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" daemon prio=10 tid=0x02b13400 nid=0x130c runnable [0x00000000..0x00000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" daemon prio=8 tid=0x02ac2800 nid=0x23c0 in Object.wait() [0x02c8f000..0x02c8fa94]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x229e0b28> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:116)
        - locked <0x229e0b28> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:132)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:159)

"Reference Handler" daemon prio=10 tid=0x02ac1400 nid=0x16e0 in Object.wait() [0x02c3f000..0x02c3fb1
4]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x229e0a30> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:485)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:116)
        - locked <0x229e0a30> (a java.lang.ref.Reference$Lock)

"VM Thread" prio=10 tid=0x02abf800 nid=0x2030 runnable

"VM Periodic Task Thread" prio=10 tid=0x02b15c00 nid=0x1360 waiting on condition

JNI global references: 607


D:\Program Files\Java\jdk1.6.0_13\bin>


*/
