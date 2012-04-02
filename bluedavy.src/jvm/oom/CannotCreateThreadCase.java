package jvm.oom;

/**
 * Case: Cannot create native Thread 
 * 
 * startup args: -Xms1536m -Xmx1536m -Xss100m 
 */
public class CannotCreateThreadCase {

	public static void main(String[] args) throws Exception{
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(20000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

}

/*
D:\study\tempProject\JavaLearn\classes>java -Xms1536m -Xmx1536m -Xss100m jvm.oom.CannotCreateThreadCase
Error occurred during initialization of VM
Could not reserve enough space for object heap
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.

*/
