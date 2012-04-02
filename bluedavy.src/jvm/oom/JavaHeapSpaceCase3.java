package jvm.oom;

/**
 * Case: per thread occupate some memory,but handle too slow,then OOM
 * 
 * startup args: -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError 
 */
public class JavaHeapSpaceCase3 {

	public static void main(String[] args) throws Exception{
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Case3Object object=new Case3Object();
					object.setName(Thread.currentThread().getName());
					try {
						Thread.sleep(20000);
					} 
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

}

class Case3Object{
	private byte[] bytes=null;
	private String name;
	public Case3Object(){
		bytes=new byte[1024*1024];
	}
	public String getName() {
		return name;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	public void setName(String name){
		this.name=name;
	}
}

/*
java -Xms20m -Xmx20m -Xmn10m -XX:+HeapDumpOnOutOfMemoryError jvm.oom.JavaHeapSpaceCase3

java.lang.OutOfMemoryError: Java heap space
Dumping heap to java_pid3272.hprof ...
Heap dump file created [17717721 bytes in 1.244 secs]
Exception in thread "Thread-9" Exception in thread "Thread-19" Exception in thread "Thread-12" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.Case3Object.<init>(JavaHeapSpaceCase3.java:34)
    at jvm.oom.JavaHeapSpaceCase3$1.run(JavaHeapSpaceCase3.java:15)
    at java.lang.Thread.run(Thread.java:717)
Exception in thread "Thread-10" java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.Case3Object.<init>(JavaHeapSpaceCase3.java:34)
    at jvm.oom.JavaHeapSpaceCase3$1.run(JavaHeapSpaceCase3.java:15)
    at java.lang.Thread.run(Thread.java:717)
java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.Case3Object.<init>(JavaHeapSpaceCase3.java:34)
    at jvm.oom.JavaHeapSpaceCase3$1.run(JavaHeapSpaceCase3.java:15)
    at java.lang.Thread.run(Thread.java:717)
java.lang.OutOfMemoryError: Java heap space
    at jvm.oom.Case3Object.<init>(JavaHeapSpaceCase3.java:34)
    at jvm.oom.JavaHeapSpaceCase3$1.run(JavaHeapSpaceCase3.java:15)
    at java.lang.Thread.run(Thread.java:717)

*/
