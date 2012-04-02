/**
 * 
 */
package jvm.memory;


public class SurvivorMinorGCDemo {
    public static void main(String[] args) throws Exception {
        MemoryObject object = new MemoryObject(1024 * 1024 * 2);
        happenMinorGC(9);
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
-Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails

minor gc should happen
Heap
 def new generation   total 14784K, used 12119K [0x02a00000, 0x03a00000, 0x03a00000)
  eden space 13184K,  91% used [0x02a00000, 0x035d5dc8, 0x036e0000)
  from space 1600K,   0% used [0x036e0000, 0x036e0000, 0x03870000)
  to   space 1600K,   0% used [0x03870000, 0x03870000, 0x03a00000)
 tenured generation   total 24576K, used 0K [0x03a00000, 0x05200000, 0x05200000)
   the space 24576K,   0% used [0x03a00000, 0x03a00000, 0x03a00200, 0x05200000)
 compacting perm gen  total 12288K, used 2007K [0x05200000, 0x05e00000, 0x09200000)
   the space 12288K,  16% used [0x05200000, 0x053f5e30, 0x053f6000, 0x05e00000)
No shared spaces configured.

*/
