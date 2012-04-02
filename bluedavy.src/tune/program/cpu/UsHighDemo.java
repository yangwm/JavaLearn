/**
 * 
 */
package tune.program.cpu;

import java.util.ArrayList;

/**
 * 总结来说，当us 值高时，主要是由于启动的Java 线程一直在执行（例如循环执行），
 * 并且线程中所执行的步骤不太需要等待IO 或进入sleep、wait 等状态，
 * 又或者是启动的线程很多，当一个线程sleep、wait 后，其他的又在运行。
 * 
 * @author yangwm in Mar 29, 2010 10:46:21 AM
 */
public class UsHighDemo {

    public static void main(String[] args) throws Exception{
        UsHighDemo demo=new UsHighDemo();
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

// consume cpu 52%--59%

/*

C:\Documents and Settings\yangwm>jstack 812
2010-08-21 21:36:56
Full thread dump Java HotSpot(TM) Client VM (17.0-b05 mixed mode):

"DestroyJavaVM" prio=6 tid=0x00868c00 nid=0xbe0 waiting on condition [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Thread-201" prio=6 tid=0x0af59000 nid=0xa50 waiting on condition [0x0ef3f000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.UsHighDemo$NotConsumeCPUTask.run(UsHighDemo.java:62)
        at java.lang.Thread.run(Thread.java:717)

"Thread-200" prio=6 tid=0x0af57c00 nid=0xe88 waiting on condition [0x0eeef000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.UsHighDemo$NotConsumeCPUTask.run(UsHighDemo.java:62)
        at java.lang.Thread.run(Thread.java:717)

......

"Thread-2" prio=6 tid=0x0ae31400 nid=0xfcc waiting on condition [0x0b10f000]
   java.lang.Thread.State: TIMED_WAITING (sleeping)
        at java.lang.Thread.sleep(Native Method)
        at tune.UsHighDemo$NotConsumeCPUTask.run(UsHighDemo.java:62)
        at java.lang.Thread.run(Thread.java:717)

"Thread-1" prio=6 tid=0x0ab9dc00 nid=0xdf8 runnable [0x0b0bf000]
   java.lang.Thread.State: RUNNABLE
        at java.util.Arrays.copyOfRange(Arrays.java:3384)
        at java.lang.String.<init>(String.java:233)
        at java.lang.StringBuilder.toString(StringBuilder.java:405)
        at tune.UsHighDemo$ConsumeCPUTask.run(UsHighDemo.java:46)
        at java.lang.Thread.run(Thread.java:717)

"Thread-0" prio=6 tid=0x0ab9d000 nid=0xf90 runnable [0x0b06f000]
   java.lang.Thread.State: RUNNABLE
        at java.lang.String.<init>(String.java:233)
        at java.lang.StringBuilder.toString(StringBuilder.java:405)
        at tune.UsHighDemo$ConsumeCPUTask.run(UsHighDemo.java:46)
        at java.lang.Thread.run(Thread.java:717)

"Low Memory Detector" daemon prio=6 tid=0x0ab73000 nid=0xb54 runnable [0x00000000]
   java.lang.Thread.State: RUNNABLE

"CompilerThread0" daemon prio=10 tid=0x0ab6c800 nid=0xe94 waiting on condition [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Attach Listener" daemon prio=10 tid=0x0ab67800 nid=0x938 waiting on condition [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Signal Dispatcher" daemon prio=10 tid=0x0ab66800 nid=0x934 runnable [0x00000000]
   java.lang.Thread.State: RUNNABLE

"Finalizer" daemon prio=8 tid=0x0ab54000 nid=0xfa8 in Object.wait() [0x0ac8f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x02f16c08> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:135)
        - locked <0x02f16c08> (a java.lang.ref.ReferenceQueue$Lock)
        at java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:151)
        at java.lang.ref.Finalizer$FinalizerThread.run(Finalizer.java:177)

"Reference Handler" daemon prio=10 tid=0x0ab4f800 nid=0xbf4 in Object.wait() [0x0ac3f000]
   java.lang.Thread.State: WAITING (on object monitor)
        at java.lang.Object.wait(Native Method)
        - waiting on <0x02f169e8> (a java.lang.ref.Reference$Lock)
        at java.lang.Object.wait(Object.java:502)
        at java.lang.ref.Reference$ReferenceHandler.run(Reference.java:133)
        - locked <0x02f169e8> (a java.lang.ref.Reference$Lock)

"VM Thread" prio=10 tid=0x0ab4a800 nid=0xe48 runnable

"VM Periodic Task Thread" prio=10 tid=0x0ab7d800 nid=0xcfc waiting on condition

JNI global references: 673


C:\Documents and Settings\yangwm>

*/
