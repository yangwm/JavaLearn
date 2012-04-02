//: SchoolLock.java

package concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SchoolLock {
	private static final int NUM_TEACHERS = 4;
	private Semaphore teacherResources = new Semaphore(NUM_TEACHERS);
	private Teacher[] teachers = new Teacher[NUM_TEACHERS];
	
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public SchoolLock() {
		teachers[0] = new Teacher("Geroge");
		teachers[1] = new Teacher("Porix");
		teachers[2] = new Teacher("Hete");
		teachers[3] = new Teacher("Kills");
	}
	
	public void takeClass() throws InterruptedException {
		teacherResources.acquire();
		getTeacher();
	}
	
	synchronized public void dismiss() {
		for (int i = 0; i < NUM_TEACHERS; i++) {
			if (teachers[i].isBusy()) {
				teachers[i].dismiss();
				teacherResources.release();
				return;
			}
		}
		System.out.println("All Teachers are take a rest!!!");
	}
	
	synchronized private void getTeacher() {
		for (int i = 0; i < NUM_TEACHERS; i++) {
			if (!teachers[i].isBusy()) {
				teachers[i].teach();
				System.out.println(teachers[i].getName() + " is teacher " );
				return;
			}
		}
		System.out.println("All Teachers are busy, no teacher available!!!");
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
						s.dismiss();
					}
				}.start();
			}
		}
	}
}
