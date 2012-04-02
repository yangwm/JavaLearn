package jvm.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Case: show OOM, but dump cannot see which objects occupate memory
 * 
 * startup args: -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError    
 */
public class JavaHeapSpaceCase2 {

	public static void main(String[] args) throws Exception {
		// OOM first,then dump no value
		new Thread(new Runnable() {
			@Override
			public void run() {
				byte[] bytes=new byte[1024*1024*11];
				System.out.println(bytes);
			}
		}).start();
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					List<Case2Object> objects=new ArrayList<Case2Object>();
					// or change to 
					// while(true){
					// 	  objects.add(new Case2Object());
					// }
					for (int j = 0; j < 1000000; j++) {
						objects.add(new Case2Object());
					}
				}
			}).start();
			Thread.sleep(2000);
		}
		Thread.sleep(30000);
		System.out.println("end");
	}

}

class Case2Object{
	private String name="name";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	} 
}

/*
java -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError jvm.oom.JavaHeapSpaceCase2

java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid3956.hprof ...
Heap dump file created [1267679 bytes in 0.095 secs]
Exception in thread "Thread-0" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.JavaHeapSpaceCase2$1.run(JavaHeapSpaceCase2.java:18)
    at java.lang.Thread.run(Thread.java:717)
Exception in thread "Thread-1" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.JavaHeapSpaceCase2$2.run(JavaHeapSpaceCase2.java:32)
    at java.lang.Thread.run(Thread.java:717)
Exception in thread "Thread-2" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.JavaHeapSpaceCase2$2.run(JavaHeapSpaceCase2.java:32)
    at java.lang.Thread.run(Thread.java:717)
Exception in thread "Thread-3" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.JavaHeapSpaceCase2$2.run(JavaHeapSpaceCase2.java:32)
    at java.lang.Thread.run(Thread.java:717)
Exception in thread "Thread-4" java.lang.OutOfMemoryError: Java heap space
Exception in thread "Thread-5" java.lang.OutOfMemoryError: Java heap space
......

*/
