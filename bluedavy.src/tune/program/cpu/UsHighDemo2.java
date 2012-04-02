/**
 * 
 */
package tune.program.cpu;

import java.util.ArrayList;

/**
 * CPU us 高的原因主要是执行线程不需要任何挂起动作，且一直执行，导致CPU 没有机会去调度执行其他的线程。
 * 
 * 调优方案： 增加Thread.sleep，以释放CPU 的执行权，降低CPU 的消耗。
 * 以损失单次执行性能为代价的，但由于其降低了CPU 的消耗，对于多线程的应用而言，反而提高了总体的平均性能。
 * 
 * @author yangwm in Mar 29, 2010 11:50:54 AM
 */
public class UsHighDemo2 {

    public static void main(String[] args) throws Exception{
        UsHighDemo2 demo=new UsHighDemo2();
            demo.runTest();
        }
        private void runTest() throws Exception{
        int count=Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < count; i++) {
            new Thread(new ConsumeCPUTask()).start();
        }
        for (int i = 0; i < 200; i++) {
            new Thread(new NotConsumeCPUTask()).start();
        }
    }
    
    
    class ConsumeCPUTask implements Runnable{
        public void run() {
            String str="fwljfdsklvnxcewewrewrew12wre5rewf1ew2few4few2few2few3few3few5fsd1sdewu3249gdfkvdvx" +
            "wefsdjfewvmdxlvdsfofewmvdmvfd;lvds;vds;vdsvdsxcnzgewgdfuvxmvx.;f" +
            "fsaffsdjlvcx.vcxgdfjkf;dsfdas#vdsjlfdsmv.xc.vcxjk;fewipvdmsvzlfsjlf;afdjsl;fdsp[euiprenvs" +
            "fsdovxc.vmxceworupg;";
            float i=0.002f;
            float j=232.13243f;
        
            while (true) {
                j=i*j;
                str.indexOf("#");
                ArrayList<String> list=new ArrayList<String>();
                for (int k = 0; k < 10000; k++) {
                    list.add(str+String.valueOf(k));
                    
                    if(k%50 == 0){
                        try{
                            Thread.sleep(1);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                list.contains("iii");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class NotConsumeCPUTask implements Runnable{
        public void run() {
            while (true) {
                try {
                    Thread.sleep(100000);
                    //Thread.sleep(10000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
}

//consume cpu 2%--9%

/*
D:\Program Files\Java\jdk1.6.0_13\bin>jstack 8268
2010-03-29 13:54:22
Full thread dump Java HotSpot(TM) Client VM (11.3-b02 mixed mode, sharing):

"DestroyJavaVM" prio=6 tid=0x00848800 nid=0x2040 waiting on condition [0x00000000..0x0092fd4c]
   java.lang.Thread.State: RUNNABLE

"Thread-201" prio=6 tid=0x045f7800 nid=0x1158 waiting on condition [0x06f3f000..0x06f3fb14]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.UsHighDemo2$NotConsumeCPUTask.run(UsHighDemo2.java:72)
        at java.lang.Thread.run(Thread.java:619)

"Thread-200" prio=6 tid=0x045f6000 nid=0x10a8 waiting on condition [0x06eef000..0x06eefb94]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.UsHighDemo2$NotConsumeCPUTask.run(UsHighDemo2.java:72)
        at java.lang.Thread.run(Thread.java:619)

......


"Thread-1" prio=6 tid=0x02b21800 nid=0x200c waiting on condition [0x02ebf000..0x02ebfd94]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.UsHighDemo2$ConsumeCPUTask.run(UsHighDemo2.java:60)
        at java.lang.Thread.run(Thread.java:619)

"Thread-0" prio=6 tid=0x02b39c00 nid=0x13a8 waiting on condition [0x02e6f000..0x02e6fb94]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.UsHighDemo2$ConsumeCPUTask.run(UsHighDemo2.java:51)
        at java.lang.Thread.run(Thread.java:619)

"Low Memory Detector" daemon prio=6 tid=0x02b14400 nid=0x2014 runnable [0x00000000..0x00000000]
   java.lang.Thread.State: RUNNABLE

"CompilerThread0" daemon prio=10 tid=0x02b0d800 nid=0x202c waiting on condition [0x00000000..0x02d7f
940]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" daemon prio=10 tid=0x02b08c00 nid=0x2020 waiting on condition [0x00000000..0x00000
000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" daemon prio=10 tid=0x02b13400 nid=0x2024 runnable [0x00000000..0x00000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" daemon prio=8 tid=0x02ac5c00 nid=0x2028 in Object.wait() [0x02c8f000..0x02c8fa94]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x22ee31f0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:116)
        - locked <0x22ee31f0> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:132)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:159)

"Reference Handler" daemon prio=10 tid=0x02ac1400 nid=0x1c90 in Object.wait() [0x02c3f000..0x02c3fb1
4]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x22ee2fd0> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:485)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:116)
        - locked <0x22ee2fd0> (a java.lang.ref.Reference$Lock)

"VM Thread" prio=10 tid=0x02abf800 nid=0x203c runnable

"VM Periodic Task Thread" prio=10 tid=0x02b15c00 nid=0x201c waiting on condition

JNI global references: 622


D:\Program Files\Java\jdk1.6.0_13\bin>

*/
