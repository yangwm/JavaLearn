/**
 * 
 */
package jvm.memory;

public class DirectFullGC {

    public static void main(String[] args) throws Exception {
        byte[] bytes = new byte[1024 * 1024 * 2];
        byte[] bytes2 = new byte[1024 * 1024 * 2];
        byte[] bytes3 = new byte[1024 * 1024 * 2];
        System.out.println("ready to happen one minor gc,if parallel gc,then should one full gc");
        byte[] bytes4 = new byte[1024 * 1024 * 2];
        Thread.sleep(3000);
        System.out.println("minor gc end");
        byte[] bytes5 = new byte[1024 * 1024 * 2];
        byte[] bytes6 = new byte[1024 * 1024 * 2];
        System.out.println("minor gc again ,and should direct full gc");
        byte[] bytes7 = new byte[1024 * 1024 * 2];
        Thread.sleep(3000);
    }

}

/*
-Xms20M -Xmx20M -Xmn10M -verbose:gc -XX:+PrintGCDetails -XX:+UseParallelGC

ready to happen one minor gc,if parallel gc,then should one full gc
[GC [PSYoungGen: 6621K->312K(8960K)] 6621K->6456K(19200K), 0.0479301 secs] [Times: user=0.02 sys=0.00, real=0.05 secs] 
[Full GC [PSYoungGen: 312K->0K(8960K)] [PSOldGen: 6144K->6423K(10240K)] 6456K->6423K(19200K) [PSPermGen: 2000K->2000K(12288K)], 0.0087849 secs] [Times: user=0.02 sys=0.00, real=0.01 secs] 
minor gc end
minor gc again ,and should direct full gc
[Full GC [PSYoungGen: 6379K->4096K(8960K)] [PSOldGen: 6423K->8472K(10240K)] 12803K->12568K(19200K) [PSPermGen: 2003K->2003K(12288K)], 0.0156087 secs] [Times: user=0.01 sys=0.00, real=0.02 secs] 
Heap
 PSYoungGen      total 8960K, used 6297K [0x07400000, 0x07e00000, 0x07e00000)
  eden space 7680K, 82% used [0x07400000,0x07a26700,0x07b80000)
  from space 1280K, 0% used [0x07b80000,0x07b80000,0x07cc0000)
  to   space 1280K, 0% used [0x07cc0000,0x07cc0000,0x07e00000)
 PSOldGen        total 10240K, used 8472K [0x06a00000, 0x07400000, 0x07400000)
  object space 10240K, 82% used [0x06a00000,0x072460c0,0x07400000)
 PSPermGen       total 12288K, used 2008K [0x02a00000, 0x03600000, 0x06a00000)
  object space 12288K, 16% used [0x02a00000,0x02bf63a0,0x03600000)
Turning off use of shared archive because of choice of garbage collector or large pages 

*/
