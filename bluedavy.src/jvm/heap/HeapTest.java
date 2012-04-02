/**
 * 
 */
package jvm.heap;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangwm in Mar 26, 2010 3:14:09 PM
 */
public class HeapTest {

    /**
     * create by yangwm in Mar 26, 2010 3:14:09 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("HeapTest begin");
        
        Map<String, byte[]> bytes=new HashMap<String, byte[]>();
        for (int i = 0; i < 64*1024; i++) {
            bytes.put(String.valueOf(i),new byte[1024]);
        }
        
        System.out.println("HeapTest end");
    }

}

/*
java -Xms64M -Xmx64M -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCApplicationStoppedTime jvm.heap.HeapTest

HeapTest begin
0.086: [GC 0.086: [DefNew: 1088K->64K(1152K), 0.0031091 secs] 1088K->952K(16320K), 0.0031820 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0034105 seconds
0.092: [GC 0.092: [DefNew: 1152K->64K(1152K), 0.0026610 secs] 2040K->2026K(16320K), 0.0027325 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0029127 seconds
0.096: [GC 0.096: [DefNew: 1152K->63K(1152K), 0.0027976 secs] 3114K->3109K(16320K), 0.0028613 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0030526 seconds
0.100: [GC 0.100: [DefNew: 1151K->63K(1152K), 0.0027090 secs] 4197K->4183K(16320K), 0.0030068 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0031884 seconds
0.105: [GC 0.105: [DefNew: 1151K->63K(1152K), 0.0023386 secs] 5271K->5266K(16320K), 0.0024106 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0026408 seconds
0.108: [GC 0.108: [DefNew: 1151K->63K(1152K), 0.0025554 secs] 6354K->6341K(16320K), 0.0026221 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0028152 seconds
0.112: [GC 0.112: [DefNew: 1151K->64K(1152K), 0.0027967 secs] 7429K->7417K(16320K), 0.0028666 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
Total time for which application threads were stopped: 0.0031040 seconds
0.117: [GC 0.117: [DefNew: 1151K->63K(1152K), 0.0027981 secs] 8505K->8489K(16320K), 0.0028649 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0030727 seconds
0.121: [GC 0.121: [DefNew: 1151K->63K(1152K), 0.0028152 secs] 9577K->9562K(16320K), 0.0028789 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0030582 seconds
0.125: [GC 0.126: [DefNew: 1151K->63K(1152K), 0.0031105 secs] 10650K->10636K(16320K), 0.0040966 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0043363 seconds
0.131: [GC 0.131: [DefNew: 1151K->63K(1152K), 0.0024903 secs] 11724K->11709K(16320K), 0.0025626 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0027612 seconds
0.135: [GC 0.135: [DefNew: 1151K->63K(1152K), 0.0026428 secs] 12797K->12782K(16320K), 0.0027037 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0028856 seconds
0.139: [GC 0.139: [DefNew: 1151K->64K(1152K), 0.0033624 secs] 13870K->13856K(16320K), 0.0034236 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0035988 seconds
0.144: [GC 0.144: [DefNew: 1152K->63K(1152K), 0.0026593 secs] 14944K->14927K(16320K), 0.0027151 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
Total time for which application threads were stopped: 0.0028984 seconds
0.148: [GC 0.148: [DefNew: 1151K->63K(1152K), 0.0026478 secs]0.150: [Tenured: 15948K->16012K(16064K), 0.0270392 secs] 16015K->16012K(17216K), [Perm : 362K->362K(12288K)], 0.0299206 secs] [Times: user=0.01 sys=0.02, real=0.03 secs] 
Total time for which application threads were stopped: 0.0301429 seconds
0.180: [GC 0.180: [DefNew: 1856K->192K(2048K), 0.0039399 secs] 17868K->17846K(28736K), 0.0040346 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0042092 seconds
0.186: [GC 0.186: [DefNew: 2048K->191K(2048K), 0.0044975 secs] 19702K->19694K(28736K), 0.0045754 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0047584 seconds
0.193: [GC 0.193: [DefNew: 2047K->191K(2048K), 0.0042287 secs] 21550K->21539K(28736K), 0.0042924 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
Total time for which application threads were stopped: 0.0045802 seconds
0.199: [GC 0.199: [DefNew: 2047K->192K(2048K), 0.0046813 secs] 23395K->23392K(28736K), 0.0047743 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0049654 seconds
0.206: [GC 0.206: [DefNew: 2047K->191K(2048K), 0.0049501 secs] 25248K->25220K(28736K), 0.0050146 secs] [Times: user=0.00 sys=0.01, real=0.01 secs] 
Total time for which application threads were stopped: 0.0052040 seconds
0.213: [GC 0.213: [DefNew: 2047K->192K(2048K), 0.0048515 secs]0.218: [Tenured: 26856K->26943K(26944K), 0.0347117 secs] 27076K->27048K(28992K), [Perm : 362K->362K(12288K)], 0.0399576 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
Total time for which application threads were stopped: 0.0401459 seconds
0.257: [GC 0.258: [DefNew: 3136K->319K(3456K), 0.0122471 secs] 30079K->30067K(48364K), 0.0123158 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0124913 seconds
0.273: [GC 0.274: [DefNew: 3455K->319K(3456K), 0.0081686 secs] 33203K->33152K(48364K), 0.0082265 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0084142 seconds
0.285: [GC 0.285: [DefNew: 3455K->319K(3456K), 0.0078622 secs] 36288K->36280K(48364K), 0.0079183 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
Total time for which application threads were stopped: 0.0080890 seconds
0.297: [GC 0.297: [DefNew: 3455K->320K(3456K), 0.0080848 secs] 39416K->39399K(48364K), 0.0081471 secs] [Times: user=0.00 sys=0.02, real=0.02 secs] 
Total time for which application threads were stopped: 0.0084067 seconds
0.308: [GC 0.308: [DefNew: 3456K->320K(3456K), 0.0072143 secs] 42535K->42507K(48364K), 0.0072724 secs] [Times: user=0.00 sys=0.00, real=0.00 secs] 
Total time for which application threads were stopped: 0.0074543 seconds
0.318: [GC 0.318: [DefNew: 3456K->319K(3456K), 0.0072526 secs]0.326: [Tenured: 45304K->45419K(45420K), 0.0481902 secs] 45643K->45624K(48876K), [Perm : 362K->362K(12288K)], 0.0557479 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
Total time for which application threads were stopped: 0.0559462 seconds
0.378: [GC 0.378: [DefNew: 4096K->447K(4544K), 0.0179601 secs] 49515K->49513K(65088K), 0.0180210 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
Total time for which application threads were stopped: 0.0182149 seconds
0.403: [GC 0.404: [DefNew: 4543K->447K(4544K), 0.0114023 secs] 53609K->53584K(65088K), 0.0114562 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
Total time for which application threads were stopped: 0.0116501 seconds
0.421: [GC 0.421: [DefNew: 4543K->447K(4544K), 0.0132455 secs] 57680K->57659K(65088K), 0.0133129 secs] [Times: user=0.02 sys=0.00, real=0.02 secs] 
Total time for which application threads were stopped: 0.0135344 seconds
0.439: [GC 0.439: [DefNew: 4543K->4543K(4544K), 0.0000525 secs]0.439: [Tenured: 57211K->60543K(60544K), 0.1174264 secs] 61755K->61245K(65088K), [Perm : 362K->362K(12288K)], 0.1175747 secs] [Times: user=0.11 sys=0.00, real=0.13 secs] 
Total time for which application threads were stopped: 0.1177650 seconds
0.561: [Full GC 0.561: [Tenured: 60543K->60543K(60544K), 0.0674899 secs] 65086K->65049K(65088K), [Perm : 362K->362K(12288K)], 0.0676083 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
Total time for which application threads were stopped: 0.0678329 seconds
0.629: [Full GC 0.629: [Tenured: 60543K->60543K(60544K), 0.0654488 secs] 65086K->65086K(65088K), [Perm : 362K->362K(12288K)], 0.0655360 secs] [Times: user=0.08 sys=0.00, real=0.08 secs] 
0.695: [Full GC 0.695: [Tenured: 60543K->60543K(60544K), 0.1168621 secs] 65086K->65083K(65088K), [Perm : 362K->361K(12288K)], 0.1169285 secs] [Times: user=0.11 sys=0.00, real=0.11 secs] 
Total time for which application threads were stopped: 0.1827053 seconds
0.812: [Full GC 0.812: [Tenured: 60543K->60543K(60544K), 0.0658478 secs] 65086K->65086K(65088K), [Perm : 361K->361K(12288K)], 0.0659338 secs] [Times: user=0.06 sys=0.00, real=0.06 secs] 
0.878: [Full GC 0.878: [Tenured: 60543K->60543K(60544K), 0.0658505 secs] 65086K->65086K(65088K), [Perm : 361K->361K(12288K)], 0.0659254 secs] [Times: user=0.06 sys=0.00, real=0.08 secs] 
Total time for which application threads were stopped: 0.1320444 seconds
Exception in thread "main" 0.945: [Full GC 0.945: [Tenuredjava.lang.OutOfMemoryError: Java heap space
: 60543K->933K(60544K), 0.0201908 secs] 65087K->933K(65088K), [Perm : 361K->361K(12288K)], 0.0202847 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
Total time for which application threads were stopped: 0.0204417 seconds
    at jvm.heap.HeapTest.main(HeapTest.java:23)
Heap
 def new generation   total 4544K, used 865K [0x229e0000, 0x22ec0000, 0x22ec0000)
  eden space 4096K,  21% used [0x229e0000, 0x22ab87f8, 0x22de0000)
  from space 448K,   0% used [0x22e50000, 0x22e50000, 0x22ec0000)
  to   space 448K,   0% used [0x22de0000, 0x22de0000, 0x22e50000)
 tenured generation   total 60544K, used 933K [0x22ec0000, 0x269e0000, 0x269e0000)
   the space 60544K,   1% used [0x22ec0000, 0x22fa9450, 0x22fa9600, 0x269e0000)
 compacting perm gen  total 12288K, used 361K [0x269e0000, 0x275e0000, 0x2a9e0000)
   the space 12288K,   2% used [0x269e0000, 0x26a3a650, 0x26a3a800, 0x275e0000)
    ro space 8192K,  67% used [0x2a9e0000, 0x2af47ea8, 0x2af48000, 0x2b1e0000)
    rw space 12288K,  53% used [0x2b1e0000, 0x2b855530, 0x2b855600, 0x2bde0000)

*/
