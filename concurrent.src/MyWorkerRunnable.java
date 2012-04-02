
public class MyWorkerRunnable implements Runnable {
	private MyCallable call;

	public MyWorkerRunnable() {
		call = new MyCallable();
	}
	public MyWorkerRunnable(MyFuture future, MyCallable call) {
		this.call = call;
		future = new MyFuture();
	}
	
	public void setCall(MyCallable call) {
		this.call = call;
	}
	
	public MyFuture getFuture() {
		return null;// future;
	}
	
	public void run() {
		while (true) {
		}
	}

}
