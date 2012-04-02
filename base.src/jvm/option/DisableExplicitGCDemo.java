/**
 * 
 */
package jvm.option;

import java.nio.ByteBuffer;

/**
 * 
 * 
 * @author yangwm Nov 20, 2011 10:25:46 PM
 */
public class DisableExplicitGCDemo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            ByteBuffer.allocateDirect(128);
        }
        System.out.println("Done");
    }

}

/*
$ java -version
java version "1.6.0_26"
Java(TM) SE Runtime Environment (build 1.6.0_26-b03)
Java HotSpot(TM) Server VM (build 20.1-b02, mixed mode)

$ java -XX:MaxDirectMemorySize=10m -XX:+PrintGC -XX:+DisableExplicitGC DisableExplicitGCDemo
[GC 7552K->7452K(28800K), 0.0354450 secs]
Exception in thread "main" java.lang.OutOfMemoryError: Direct buffer memory
    at java.nio.Bits.reserveMemory(Bits.java:633)
    at java.nio.DirectByteBuffer.<init>(DirectByteBuffer.java:98)
    at java.nio.ByteBuffer.allocateDirect(ByteBuffer.java:288)
    at jvm.option.DisableExplicitGCDemo.main(DisableExplicitGCDemo.java:20)

$ java -XX:MaxDirectMemorySize=10m -XX:+PrintGC DisableExplicitGCDemo
[GC 7552K->7452K(28800K), 0.0319710 secs]
[GC 11912K->11776K(36352K), 0.0197300 secs]
[Full GC 11776K->10744K(36352K), 0.0403720 secs]
Done

*/
