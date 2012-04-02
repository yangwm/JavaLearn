/**
 * 
 */
package tune.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author yangwm Aug 21, 2010 9:19:23 PM
 */
public class GCDemo {

    public static void main(String[] args) throws Exception {
        System.out.println("ready to start");
        Thread.sleep(10000);
        List<GCDataObject> oldGenObjects = new ArrayList<GCDataObject>();
        for (int i = 0; i < 51200; i++) {
            oldGenObjects.add(new GCDataObject(2));
        }
        System.gc();
        oldGenObjects.size();
        oldGenObjects = null;
        Thread.sleep(5000);
        List<GCDataObject> tmpObjects = new ArrayList<GCDataObject>();
        for (int i = 0; i < 3200; i++) {
            tmpObjects.add(new GCDataObject(5));
        }
        tmpObjects.size();
        tmpObjects = null;
    }

}

class GCDataObject {

    byte[] bytes = null;

    RefObject object = null;

    public GCDataObject(int factor) {
        // create object in kb
        bytes = new byte[factor * 1024];
        object = new RefObject();
    }

}

class RefObject {

    RefChildObject object;

    public RefObject() {
        object = new RefChildObject();
    }

}

class RefChildObject {

    public RefChildObject() {
        ;
    }

}

/*
新生代大小设置过小
-Xms135M -Xmx135M -Xmn20M -XX:+UseSerialGC -XX:+PrintGCDetails :
ready to start
[GC [DefNew: 16384K->2048K(18432K), 0.0305148 secs] 16384K->16076K(136192K), 0.0305676 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18432K->2048K(18432K), 0.0342636 secs] 32460K->31991K(136192K), 0.0343127 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18432K->2048K(18432K), 0.0316529 secs] 48375K->48228K(136192K), 0.0316990 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
[GC [DefNew: 18432K->2048K(18432K), 0.0322924 secs] 64612K->64416K(136192K), 0.0323427 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18432K->2048K(18432K), 0.0321560 secs] 80800K->80788K(136192K), 0.0324245 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18432K->2048K(18432K), 0.0316208 secs] 97172K->97160K(136192K), 0.0316820 secs] [Times: user=0.02 sys=0.02, real=0.03 secs] 
[Full GC (System) [Tenured: 95112K->105863K(117760K), 0.0739868 secs] 106036K->105863K(136192K), [Perm : 2005K->2005K(12288K)], 0.0877301 secs] [Times: user=0.06 sys=0.00, real=0.08 secs] 
[GC [DefNew: 16384K->16384K(18432K), 0.0000517 secs][Tenured: 105863K->279K(118784K), 0.0198651 secs] 122247K->279K(137216K), [Perm : 2005K->2005K(12288K)], 0.0200377 secs] [Times: user=0.01 sys=0.00, real=0.01 secs] 
Heap
 def new generation   total 18432K, used 327K [0x02a00000, 0x03e00000, 0x03e00000)
  eden space 16384K,   2% used [0x02a00000, 0x02a51f38, 0x03a00000)
  from space 2048K,   0% used [0x03a00000, 0x03a00000, 0x03c00000)
  to   space 2048K,   0% used [0x03c00000, 0x03c00000, 0x03e00000)
 tenured generation   total 118784K, used 279K [0x03e00000, 0x0b200000, 0x0b200000)
   the space 118784K,   0% used [0x03e00000, 0x03e45ec0, 0x03e46000, 0x0b200000)
 compacting perm gen  total 12288K, used 2011K [0x0b200000, 0x0be00000, 0x0f200000)
   the space 12288K,  16% used [0x0b200000, 0x0b3f6db0, 0x0b3f6e00, 0x0be00000)
No shared spaces configured.


新生代大小设置过大
-Xms135M -Xmx135M -Xmn105M -XX:+UseSerialGC -XX:+PrintGCDetails :
ready to start
[GC [DefNew (promotion failed): 86016K->96768K(96768K), 0.1167447 secs][Tenured: 31744K->31744K(31744K), 0.1359810 secs] 86016K->85324K(128512K), [Perm : 2003K->2003K(12288K)], 0.2528503 secs] [Times: user=0.20 sys=0.05, real=0.25 secs] 
[Full GC (System) [Tenured: 31744K->31744K(31744K), 0.1398815 secs] 108103K->105710K(128512K), [Perm : 2005K->2005K(12288K)], 0.1399580 secs] [Times: user=0.13 sys=0.02, real=0.14 secs] 
Heap
 def new generation   total 96768K, used 90211K [0x02a00000, 0x09300000, 0x09300000)
  eden space 86016K, 100% used [0x02a00000, 0x07e00000, 0x07e00000)
  from space 10752K,  39% used [0x08880000, 0x08c98eb8, 0x09300000)
  to   space 10752K,   0% used [0x07e00000, 0x07e00000, 0x08880000)
 tenured generation   total 31744K, used 31744K [0x09300000, 0x0b200000, 0x0b200000)
   the space 31744K, 100% used [0x09300000, 0x0b200000, 0x0b200000, 0x0b200000)
 compacting perm gen  total 12288K, used 2011K [0x0b200000, 0x0be00000, 0x0f200000)
   the space 12288K,  16% used [0x0b200000, 0x0b3f6e70, 0x0b3f7000, 0x0be00000)
No shared spaces configured.


Survivor设置过小或过大
-Xms135M -Xmx135M -Xmn20M -XX:SurvivorRatio=10 -XX:+UseSerialGC -XX:+PrintGCDetails :
ready to start
[GC [DefNew: 17152K->1664K(18816K), 0.0324927 secs] 17152K->16780K(136576K), 0.0325511 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18816K->1664K(18816K), 0.0351710 secs] 33932K->33513K(136576K), 0.0352268 secs] [Times: user=0.05 sys=0.00, real=0.05 secs] 
[GC [DefNew: 18816K->1664K(18816K), 0.0334009 secs] 50665K->50548K(136576K), 0.0334520 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18816K->1664K(18816K), 0.0326852 secs] 67700K->67530K(136576K), 0.0327832 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18816K->1664K(18816K), 0.0343644 secs] 84682K->84440K(136576K), 0.0344091 secs] [Times: user=0.03 sys=0.00, real=0.03 secs] 
[GC [DefNew: 18816K->1664K(18816K), 0.0328114 secs] 101592K->101504K(136576K), 0.0328612 secs] [Times: user=0.00 sys=0.03, real=0.03 secs] 
[Full GC (System) [Tenured: 99840K->105710K(117760K), 0.0691803 secs] 105794K->105710K(136576K), [Perm : 2005K->2005K(12288K)], 0.0693004 secs] [Times: user=0.08 sys=0.00, real=0.08 secs] 
Heap
 def new generation   total 18816K, used 16778K [0x02a00000, 0x03e00000, 0x03e00000)
  eden space 17152K,  97% used [0x02a00000, 0x03a62b30, 0x03ac0000)
  from space 1664K,   0% used [0x03ac0000, 0x03ac0000, 0x03c60000)
  to   space 1664K,   0% used [0x03c60000, 0x03c60000, 0x03e00000)
 tenured generation   total 118784K, used 105710K [0x03e00000, 0x0b200000, 0x0b200000)
   the space 118784K,  88% used [0x03e00000, 0x0a53bb68, 0x0a53bc00, 0x0b200000)
 compacting perm gen  total 12288K, used 2011K [0x0b200000, 0x0be00000, 0x0f200000)
   the space 12288K,  16% used [0x0b200000, 0x0b3f6e70, 0x0b3f7000, 0x0be00000)
No shared spaces configured.


*/
