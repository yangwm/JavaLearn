/**
 * 
 */
package jvm.memory;

public class MinorGCDemo {
    public static void main(String[] args) throws Exception {
        MemoryObject object = new MemoryObject(1024 * 1024);
        happenMinorGC(11);
        Thread.sleep(2000);
    }

    private static void happenMinorGC(int happenMinorGCIndex) throws Exception {
        for (int i = 0; i < happenMinorGCIndex; i++) {
            if (i == happenMinorGCIndex - 1) {
                Thread.sleep(2000);
                System.out.println("minor gc should happen");
            }
            new MemoryObject(1024 * 1024);
        }
    }
}

/*
-Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime
minor gc should happen
Heap
Total time for which application threads were stopped: 0.0001623 seconds
 def new generation   total 14784K, used 13143K [0x02a00000, 0x03a00000, 0x03a00000)
  eden space 13184K,  99% used [0x02a00000, 0x036d5de8, 0x036e0000)
  from space 1600K,   0% used [0x036e0000, 0x036e0000, 0x03870000)
  to   space 1600K,   0% used [0x03870000, 0x03870000, 0x03a00000)
 tenured generation   total 24576K, used 0K [0x03a00000, 0x05200000, 0x05200000)
   the space 24576K,   0% used [0x03a00000, 0x03a00000, 0x03a00200, 0x05200000)
 compacting perm gen  total 12288K, used 2007K [0x05200000, 0x05e00000, 0x09200000)
   the space 12288K,  16% used [0x05200000, 0x053f5e00, 0x053f5e00, 0x05e00000)
No shared spaces configured.


-Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails

minor gc should happen
Heap
 def new generation   total 14784K, used 13143K [0x02a00000, 0x03a00000, 0x03a00000)
  eden space 13184K,  99% used [0x02a00000, 0x036d5de8, 0x036e0000)
  from space 1600K,   0% used [0x036e0000, 0x036e0000, 0x03870000)
  to   space 1600K,   0% used [0x03870000, 0x03870000, 0x03a00000)
 tenured generation   total 24576K, used 0K [0x03a00000, 0x05200000, 0x05200000)
   the space 24576K,   0% used [0x03a00000, 0x03a00000, 0x03a00200, 0x05200000)
 compacting perm gen  total 12288K, used 2007K [0x05200000, 0x05e00000, 0x09200000)
   the space 12288K,  16% used [0x05200000, 0x053f5d58, 0x053f5e00, 0x05e00000)
No shared spaces configured.


-XX:+UseSerialGC -Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails -XX:SurvivorRatio=6

minor gc should happen
[GC [DefNew: 11819K->1304K(14336K), 0.0043028 secs] 11819K->1304K(38912K), 0.0043760 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Heap
 def new generation   total 14336K, used 2820K [0x02a00000, 0x03a00000, 0x03a00000)
  eden space 12288K,  12% used [0x02a00000, 0x02b7aef0, 0x03600000)
  from space 2048K,  63% used [0x03800000, 0x03946128, 0x03a00000)
  to   space 2048K,   0% used [0x03600000, 0x03600000, 0x03800000)
 tenured generation   total 24576K, used 0K [0x03a00000, 0x05200000, 0x05200000)
   the space 24576K,   0% used [0x03a00000, 0x03a00000, 0x03a00200, 0x05200000)
 compacting perm gen  total 12288K, used 2009K [0x05200000, 0x05e00000, 0x09200000)
   the space 12288K,  16% used [0x05200000, 0x053f66e8, 0x053f6800, 0x05e00000)
No shared spaces configured.


-XX:+UseParNewGC -Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails -XX:SurvivorRatio=6

minor gc should happen
[GC [ParNew: 11819K->1308K(14336K), 0.0040407 secs] 11819K->1308K(38912K), 0.0040896 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Heap
 par new generation   total 14336K, used 2824K [0x02ba0000, 0x03ba0000, 0x03ba0000)
  eden space 12288K,  12% used [0x02ba0000, 0x02d1aef0, 0x037a0000)
  from space 2048K,  63% used [0x039a0000, 0x03ae73a8, 0x03ba0000)
  to   space 2048K,   0% used [0x037a0000, 0x037a0000, 0x039a0000)
 tenured generation   total 24576K, used 0K [0x03ba0000, 0x053a0000, 0x053a0000)
   the space 24576K,   0% used [0x03ba0000, 0x03ba0000, 0x03ba0200, 0x053a0000)
 compacting perm gen  total 12288K, used 2009K [0x053a0000, 0x05fa0000, 0x093a0000)
   the space 12288K,  16% used [0x053a0000, 0x055966e8, 0x05596800, 0x05fa0000)
No shared spaces configured.
Turning off use of shared archive because of choice of garbage collector or large pages 

*/
