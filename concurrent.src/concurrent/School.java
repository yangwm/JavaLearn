//: School.java

package concurrent;

//import java.util.concurrent.Semaphore;

public class School {
	private static final int NUM_TEACHERS = 4;
	private Teacher[] teachers = new Teacher[NUM_TEACHERS];
	
	//private Lock lock = new ReentrantLock();
	
	public School() {
		teachers[0] = new Teacher("Geroge");
		teachers[1] = new Teacher("Porix");
		teachers[2] = new Teacher("Hete");
		teachers[3] = new Teacher("Kills");
	}
	
	public void takeClass() throws InterruptedException {
		getTeacher();
	}
	
	public void overClass() throws InterruptedException {
	    dismiss();
    }
	
	synchronized public void dismiss() {
		for (int i = 0; i < NUM_TEACHERS; i++) {
			if (teachers[i].isBusy()) {
				teachers[i].dismiss();
				System.out.println(teachers[i].getName() + " overClass " );
				return;
			}
		}
		System.out.println("All Teachers are take a rest!");
	}
	
	synchronized private void getTeacher() {
		for (int i = 0; i < NUM_TEACHERS; i++) {
			if (!teachers[i].isBusy()) {
				teachers[i].teach();
				System.out.println(teachers[i].getName() + " takeClass " );
				return;
			}
		}
		System.out.println("All Teachers are busy, no teacher available!");
	}
	
	public static void main(String[] args) {
		final School s = new School();
		for (int i = 0; i < 20; i++) {
		    if (i % 3 == 0) {
				new Thread() {
					public void run() {
						try {
							s.takeClass();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
			} else {
				new Thread() {
					public void run() {
						try {
                            s.overClass();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
					}
				}.start();
			}
		}
	}
}
