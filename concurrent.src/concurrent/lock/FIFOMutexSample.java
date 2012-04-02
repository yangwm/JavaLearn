/**
 * 
 */
package concurrent.lock;


/**
 * 
 * 
 * @author yangwm Jan 15, 2012 11:08:24 PM
 */
public class FIFOMutexSample {
    private static int x;
    private static int y;

    private static FIFOMutex lockObject = new FIFOMutex();
    private static class Thread1 extends Thread { 
      public void run() { 
          lockObject.lock();
          x = y = 0;
          System.out.print(x);
          lockObject.unlock();
      }
    }

    private static class Thread2 extends Thread { 
      public void run() { 
          lockObject.lock();
          x = y = 1;
          System.out.print(y);
          lockObject.unlock();
      }
    }

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }
    
}
