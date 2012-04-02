/**
 * 
 */
package hook;

/**
 * Shutdown Interceptor class which extends the Thread or Runnable 
 * 
 * @author yangwm Oct 10, 2010 10:41:10 PM
 */
public class ShutdownInterceptor extends Thread {

    private IApp app;

    public ShutdownInterceptor(IApp app) {
        this.app = app;
    }

    public void run() {
        System.out.println("Call the shutdown routine");
        app.shutDown();
    }
}
