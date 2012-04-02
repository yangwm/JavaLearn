//: 

package concurrent;

public class Semaphore {
    private final int num;
	private int count;
	
	public Semaphore(int count) {
	    num = count;
		this.count = count;
	}
	
	synchronized public void acquire() throws InterruptedException {
		while (count == 0) wait();
		count--;
		notifyAll();
		
	}
	
	synchronized public void release() throws InterruptedException {
	    while (count == num) wait();
		count++;
		notifyAll();
	}
}
