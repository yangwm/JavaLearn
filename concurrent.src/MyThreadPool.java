//: MyThreadPool.java

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MyThreadPool {
	private BlockingQueue pool = new ArrayBlockingQueue(100);
	
	public MyThreadPool() {
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread(new MyWorkerRunnable());
			pool.add(t);
		}
	}
	
	public MyFuture submit(MyCallable c) {
		return null;
	}
}
