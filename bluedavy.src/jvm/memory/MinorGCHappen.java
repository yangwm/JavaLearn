/**
 * 
 */
package jvm.memory;

public class MinorGCHappen{

     public static void main(String args[]) throws Exception{
          byte[] bytes=new byte[1024*1024*10];
          bytes=new byte[1024*1024*10];
          Thread.sleep(10000);
     }

}
/* 
-Xms40M -Xmx40M -Xmn16M -verbose:gc -XX:+PrintGCDetails

[GC [DefNew: 10831K->279K(14784K), 0.0184501 secs] 10831K->10519K(39360K), 0.0185065 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
Heap
 def new generation   total 14784K, used 11185K [0x02a00000, 0x03a00000, 0x03a00000)
  eden space 13184K,  82% used [0x02a00000, 0x034a6770, 0x036e0000)
  from space 1600K,  17% used [0x03870000, 0x038b5e58, 0x03a00000)
  to   space 1600K,   0% used [0x036e0000, 0x036e0000, 0x03870000)
 tenured generation   total 24576K, used 10240K [0x03a00000, 0x05200000, 0x05200000)
   the space 24576K,  41% used [0x03a00000, 0x04400010, 0x04400200, 0x05200000)
 compacting perm gen  total 12288K, used 2008K [0x05200000, 0x05e00000, 0x09200000)
   the space 12288K,  16% used [0x05200000, 0x053f6010, 0x053f6200, 0x05e00000)
No shared spaces configured.

*/
