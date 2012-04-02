/**
 * 
 */
package concurrent.interrupt;

/**
 * 
 * 
 * @author yangwm Feb 20, 2011 12:49:13 AM
 */
public class InterruptSample extends Thread {

    public static void main(String args[]) throws Exception {
        InterruptSample thread = new InterruptSample();
        System.out.println("Starting thread...");
        thread.start();
        Thread.sleep(3000);
        System.out.println("Interrupting thread...");
        thread.interrupt();
        
        System.out.println("main end...");
    }

    public void run() {
        try {
            while (true) {
                System.out.println("Thread is running...");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("happen interrupte...");
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }
        System.err.println("Thread exiting under request...");
    }

}
