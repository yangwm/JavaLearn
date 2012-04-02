package jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Case: Old Gen have too many objects,then OOM,show GC Overhead time limit and Java Heap Space 
 * 
 * startup args: -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelGC
 */
public class JavaHeapSpaceCase1 {

	public static void main(String[] args) throws Exception{
		Caches.getInstance().init();
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<byte[]> listbytes=new ArrayList<byte[]>();
				for (int i = 0; i < 4028; i++) {
					System.out.println("thread a: "+i);
					listbytes.add(new byte[1024]);
				}
			}
		}).start();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<byte[]> tests=new ArrayList<byte[]>();
				for (int i = 0; i < 10000; i++) {
					System.out.println("thread b: "+i);
					tests.add(new byte[1024*256]);
					try {
						Thread.sleep(200);
						if(i%2==0){
							tests.remove(0);
						}
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}

/*
java -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError -XX:+UseParallelGC jvm.oom.JavaHeapSpaceCase1

thread a: 0
thread a: 1
thread a: 2
thread a: 3
......
thread a: 1584
thread b: 10
thread a: 1585
thread a: 1586
java.lang.OutOfMemoryError: GC overhead limit exceeded
Dumping heap to java_pid3164.hprof ...
Heap dump file created [18844501 bytes in 0.636 secs]
thread b: 11
Exception in thread "Thread-0" java.lang.OutOfMemoryError: GC overhead limit exceeded
    at java.lang.StringBuilder.toString(StringBuilder.java:405)
    at jvm.oom.JavaHeapSpaceCase1$1.run(JavaHeapSpaceCase1.java:20)
    at java.lang.Thread.run(Thread.java:717)
thread b: 12
thread b: 13
thread b: 14
thread b: 15
thread b: 16
thread b: 17
thread b: 18
thread b: 19
thread b: 20
thread b: 21
Exception in thread "Thread-1" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.JavaHeapSpaceCase1$2.run(JavaHeapSpaceCase1.java:32)
    at java.lang.Thread.run(Thread.java:717)
thread b: 22
Turning off use of shared archive because of choice of garbage collector or large pages 

*/
