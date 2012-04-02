//: Teacher.java

package concurrent;

public class Teacher {
	private boolean busy;
	private String name;
	
	public Teacher(String name) {
		this.name = name;
		this.busy = false;
	}
	public void teach() {
		busy = true;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public void dismiss() {
		busy = false;
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public boolean isBusy() {
		return busy;
	}
	public void setBusy(boolean busy) {
		this.busy = busy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
