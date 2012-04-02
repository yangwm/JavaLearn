/**
 * 
 */
package tune.program.cpu;

import java.util.Random;

/**
 * 当sy 值高时，表示系统调用耗费了较多的CPU，对于Java 应用程序而言，造成这种现象的主要原因是启动的线程比较多，
 * 并且这些线程多数都处于不断的等待（例如锁等待状态）和执行状态的变化过程中，
 * 这就导致了操作系统要不断的调度这些线程，切换执行。
 * 
 * @author yangwm in Mar 29, 2010 11:22:28 AM
 */
public class SyHighDemo {
    private static int threadCount=500;
    
    /**
    * @param args
    */
    public static void main(String[] args) throws Exception {
        if(args.length==1){
            threadCount=Integer.parseInt(args[0]);
        }
        
        SyHighDemo demo = new SyHighDemo();
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

//consume cpu 95%--98%

/*
D:\Program Files\Java\jdk1.6.0_13\bin>jstack 2684
2010-03-29 14:06:28
Full thread dump Java HotSpot(TM) Client VM (11.3-b02 mixed mode, sharing):

"DestroyJavaVM" prio=6 tid=0x00848800 nid=0x211c waiting on condition [0x00000000..0x0092fd4c]
   java.lang.Thread.State: RUNNABLE

"Thread-999" prio=6 tid=0x14e11800 nid=0x229c waiting on condition [0x1749f000..0x1749fc14]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.SyHighDemo$BTask.run(SyHighDemo.java:81)
        at java.lang.Thread.run(Thread.java:619)

"Thread-998" prio=6 tid=0x14e10400 nid=0x2294 in Object.wait() [0x1744f000..0x1744fb14]
   java.lang.Thread.State: TIMED_WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        at tune.SyHighDemo$ATask.run(SyHighDemo.java:58)
        - locked <0x22a2a560> (a java.lang.Object)
        at java.lang.Thread.run(Thread.java:619)

......


"Thread-1" prio=6 tid=0x02b21c00 nid=0x2148 waiting on condition [0x02ebf000..0x02ebfb14]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.SyHighDemo$BTask.run(SyHighDemo.java:81)
        at java.lang.Thread.run(Thread.java:619)

"Thread-0" prio=6 tid=0x02b39800 nid=0x22e8 in Object.wait() [0x02e6f000..0x02e6fb94]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        at tune.SyHighDemo$ATask.run(SyHighDemo.java:58)
        - locked <0x22a295c8> (a java.lang.Object)
        at java.lang.Thread.run(Thread.java:619)

"Low Memory Detector" daemon prio=6 tid=0x02b13c00 nid=0x2138 runnable [0x00000000..0x00000000]
   java.lang.Thread.State: RUNNABLE

"CompilerThread0" daemon prio=10 tid=0x02b0e800 nid=0x2128 waiting on condition [0x00000000..0x02d7f
940]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" daemon prio=10 tid=0x02b09c00 nid=0x13f0 waiting on condition [0x00000000..0x00000
000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" daemon prio=10 tid=0x02b08800 nid=0x11e4 runnable [0x00000000..0x00000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" daemon prio=8 tid=0x02ac5c00 nid=0x1120 in Object.wait() [0x02c8f000..0x02c8fa94]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x229e0b28> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:116)
        - locked <0x229e0b28> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:132)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:159)

"Reference Handler" daemon prio=10 tid=0x02ac1400 nid=0x2120 in Object.wait() [0x02c3f000..0x02c3fb1
4]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x229e0a30> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:485)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:116)
        - locked <0x229e0a30> (a java.lang.ref.Reference$Lock)

"VM Thread" prio=10 tid=0x02abf800 nid=0x23ac runnable

"VM Periodic Task Thread" prio=10 tid=0x02b15c00 nid=0xa70 waiting on condition

JNI global references: 611


D:\Program Files\Java\jdk1.6.0_13\bin>

*/
