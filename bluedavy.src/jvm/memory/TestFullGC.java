/**
 * 
 */
package jvm.memory;

import java.util.*;

public class TestFullGC {

    public static void main(String[] args) throws Exception {
        List<MemoryObject> objects = new ArrayList<MemoryObject>(6);
        for (int i = 0; i < 10; i++) {
            objects.add(new MemoryObject(1024 * 1024));
        }
        // 让上面的对象尽可能地转入旧生代中
        System.gc();
        System.gc();
        Thread.sleep(2000);
        for (int i = 0; i < 7; i++) {
            if (i == 6) {
                System.out.println("should happen full gc");
            }
            objects.add(new MemoryObject(1024 * 1024));
            if (i % 3 == 0) {
                objects.remove(0);
            }
        }
        Thread.sleep(5000);
    }

}

/*
-Xms20M -Xmx20M -Xmn10M -verbose:gc -XX:+PrintGCDetails

[GC [DefNew: 7659K->280K(9216K), 0.0122585 secs] 7659K->7448K(19456K), 0.0123222 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
[Full GC (System) [Tenured: 7168K->9216K(10240K), 0.0117590 secs] 10771K->10520K(19456K), [Perm : 2004K->2004K(12288K)], 0.0118635 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
[Full GC (System) [Tenured: 9216K->9216K(10240K), 0.0074811 secs] 10520K->10520K(19456K), [Perm : 2004K->2004K(12288K)], 0.0075660 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
should happen full gc
[Full GC [Tenured: 9216K->9496K(10240K), 0.0219533 secs] 16751K->14616K(19456K), [Perm : 2004K->2004K(12288K)], 0.0220260 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
Heap
 def new generation   total 9216K, used 6308K [0x02a00000, 0x03400000, 0x03400000)
  eden space 8192K,  77% used [0x02a00000, 0x03029028, 0x03200000)
  from space 1024K,   0% used [0x03300000, 0x03300000, 0x03400000)
  to   space 1024K,   0% used [0x03200000, 0x03200000, 0x03300000)
 tenured generation   total 10240K, used 9496K [0x03400000, 0x03e00000, 0x03e00000)
   the space 10240K,  92% used [0x03400000, 0x03d46350, 0x03d46400, 0x03e00000)
 compacting perm gen  total 12288K, used 2010K [0x03e00000, 0x04a00000, 0x07e00000)
   the space 12288K,  16% used [0x03e00000, 0x03ff6888, 0x03ff6a00, 0x04a00000)
No shared spaces configured.

*/
