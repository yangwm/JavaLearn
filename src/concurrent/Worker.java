/**
 * 
 */
package concurrent;

import java.util.*;

/**
 * 
 * @author yangwm May 14, 2012 8:55:02 PM
 */
public class Worker extends Thread {
    private volatile boolean quittingTime = false;
    public void run() {
        while (!quittingTime)
            pretendToWork();
        System.out.println("Beer is good");
    }
    
    private void pretendToWork() {
        try {
            System.out.println("quittingTime:" + quittingTime + ", sleep: " + 300);
            Thread.sleep(300); // Sleeping on the job?
        } catch (InterruptedException ex) { }
    }
    
    /* join will be release current thread synchronized 
    // It's quitting time, wait for worker - Called by good boss
    synchronized void quit() throws InterruptedException {
        quittingTime = true;
        join();
    }
    // Rescind quitting time - Called by evil boss
    synchronized void keepWorking() {
        quittingTime = false;
    }
    */
    
    private final Object lock = new Object();
    // It's quitting time, wait for worker - Called by good boss
    void quit() throws InterruptedException {
        synchronized (lock) {
            quittingTime = true;
            join();
        }
    }

    // Rescind quitting time - Called by evil boss
    void keepWorking() {
        synchronized (lock) {
            quittingTime = false;
        }
    }
    
    public static void main(String[] args)
            throws InterruptedException {
        final Worker worker = new Worker();
        worker.start();
        Timer t = new Timer(true); // Daemon thread
        t.schedule(new TimerTask() {
            public void run() { worker.keepWorking(); }
        }, 500);
        Thread.sleep(400);
        worker.quit();
    }
}
