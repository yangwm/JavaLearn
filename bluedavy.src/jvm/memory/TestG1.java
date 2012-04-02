/**
 * 
 */
package jvm.memory;

import java.util.*;

public class TestG1{
    public static void main(String[] args) throws Exception{
        List<MemoryObject> objects=new ArrayList<MemoryObject>();
        for(int i=0;i<20;i++){
           objects.add(new MemoryObject(1024*1024));
           if(i%3==0){
               objects.remove(0);
           }
        }
        Thread.sleep(2000);
    }
}
/*
-Xms40M -Xmx40M -Xmn20M -verbose:gc -XX:+Unlock-ExperimentalVMOptions -XX:+UseG1GC -XX:MaxGCPauseMillis=5 -XX:GCPauseIntervalMillis=1000 -XX:+PrintGCDetails

D:\study\tempProject\JavaLearn\classes>java -Xms40M -Xmx40M -Xmn20M -verbose:gc -XX:MaxGCPauseMillis=5 -XX:GCPauseIntervalMillis=1000 -XX:+PrintGCDetails jvm.memory.TestG1 -XX:+Unlock-ExperimentalVMOptions -XX:+UseG1GC
[GC [DefNew: 16015K->1285K(18432K), 0.0156227 secs] 16015K->10501K(38912K), 0.0158045 secs] [Times:
user=0.00 sys=0.02, real=0.02 secs]
Heap
 def new generation   total 18432K, used 7231K [0x24200000, 0x25600000, 0x25600000)
  eden space 16384K,  36% used [0x24200000, 0x247ce910, 0x25200000)
  from space 2048K,  62% used [0x25400000, 0x25541588, 0x25600000)
  to   space 2048K,   0% used [0x25200000, 0x25200000, 0x25400000)
 tenured generation   total 20480K, used 9216K [0x25600000, 0x26a00000, 0x26a00000)
   the space 20480K,  45% used [0x25600000, 0x25f00090, 0x25f00200, 0x26a00000)
 compacting perm gen  total 12288K, used 308K [0x26a00000, 0x27600000, 0x2aa00000)
   the space 12288K,   2% used [0x26a00000, 0x26a4d078, 0x26a4d200, 0x27600000)
    ro space 10240K,  57% used [0x2aa00000, 0x2afc6f80, 0x2afc7000, 0x2b400000)
    rw space 12288K,  53% used [0x2b400000, 0x2ba6ae28, 0x2ba6b000, 0x2c000000)

D:\study\tempProject\JavaLearn\classes>
*/
