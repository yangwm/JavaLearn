/**
 * 
 */
package jvm.memory;

import java.util.*;

public class TestCMSGC {

    public static void main(String[] args) throws Exception {
        List<MemoryObject> objects = new ArrayList<MemoryObject>(6);
        for (int i = 0; i < 9; i++) {
            objects.add(new MemoryObject(1024 * 1024));
        }
        // System.gc();
        Thread.sleep(2000);
        objects.remove(0);
        objects.remove(0);
        objects.remove(0);
        for (int i = 0; i < 20; i++) {
            objects.add(new MemoryObject(1024 * 1024));
            if (i % 2 == 0) {
                objects.remove(0);
            }
        }
        Thread.sleep(5000);
    }

}

/*
java -Xms20M -Xmx20M -Xmn10M -verbose:gc -XX:+PrintGCDetails -XX:+UseConcMarkSweepGC jvm.memory.TestCMSGC

[GC [ParNew: 7659K->284K(9216K), 0.0103010 secs] 7659K->7454K(19456K), 0.0104784 secs] [Times: user=0.03 sys=0.00, real=0.02 secs] 
[GC [1 CMS-initial-mark: 7170K(10240K)] 9753K(19456K), 0.0005624 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark: 0.007/0.007 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC [ParNew: 7703K->7703K(9216K), 0.0000500 secs][CMS (concurrent mode failure): 7170K->8473K(10240K), 0.0219573 secs] 14873K->8473K(19456K), [CMS Perm : 2004K->2003K(12288K)], 0.0221386 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [ParNew: 7225K->7225K(9216K), 0.0000497 secs][CMS: 8473K->9497K(10240K), 0.0249314 secs] 15698K->12569K(19456K), [CMS Perm : 2003K->2003K(12288K)], 0.0250859 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [1 CMS-initial-mark: 9497K(10240K)] 13593K(19456K), 0.0002168 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[Full GC [CMS[CMS-concurrent-mark: 0.007/0.007 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
 (concurrent mode failure): 9497K->9497K(10240K), 0.0320496 secs] 16703K->14617K(19456K), [CMS Perm : 2003K->2003K(12288K)], 0.0321586 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[Full GC [CMS: 9497K->9497K(10240K), 0.0316982 secs] 16690K->15641K(19456K), [CMS Perm : 2003K->2003K(12288K)], 0.0318054 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[Full GC [CMS: 9497K->9497K(10240K), 0.0314138 secs] 16665K->15641K(19456K), [CMS Perm : 2003K->2003K(12288K)], 0.0315023 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [1 CMS-initial-mark: 9497K(10240K)] 16665K(19456K), 0.0002266 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark: 0.006/0.006 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-preclean: 0.004/0.004 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
[CMS-concurrent-abortable-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC[YG occupancy: 7168 K (9216 K)][Rescan (parallel) , 0.0001657 secs][weak refs processing, 0.0000081 secs] [1 CMS-remark: 9497K(10240K)] 16665K(19456K), 0.0002567 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[GC [1 CMS-initial-mark: 9497K(10240K)] 16665K(19456K), 0.0001483 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-mark: 0.005/0.005 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
[CMS-concurrent-preclean: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-abortable-preclean: 0.004/3.142 secs] [Times: user=0.00 sys=0.00, real=3.14 secs] 
[GC[YG occupancy: 7332 K (9216 K)][Rescan (parallel) , 0.0001805 secs][weak refs processing, 0.0000081 secs] [1 CMS-remark: 9497K(10240K)] 16829K(19456K), 0.0002685 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-sweep: 0.000/0.000 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
[CMS-concurrent-reset: 0.002/0.002 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Heap
 par new generation   total 9216K, used 7332K [0x02ba0000, 0x035a0000, 0x035a0000)
  eden space 8192K,  89% used [0x02ba0000, 0x032c9098, 0x033a0000)
  from space 1024K,   0% used [0x034a0000, 0x034a0000, 0x035a0000)
  to   space 1024K,   0% used [0x033a0000, 0x033a0000, 0x034a0000)
 concurrent mark-sweep generation total 10240K, used 9497K [0x035a0000, 0x03fa0000, 0x03fa0000)
 concurrent-mark-sweep perm gen total 12288K, used 2009K [0x03fa0000, 0x04ba0000, 0x07fa0000)
Turning off use of shared archive because of choice of garbage collector or large pages 

*/
