//: MyFuture.java

public class MyFuture {
	private long result;
	private boolean ready;
	
	public long getResult() {
		while (!ready) {
		}
		return result;
	}
	public void setResult(long result) {
		this.result = result;
		ready = true;
	}
}


