/**
 * 
 */
package hook;

/**
 * sample application will implement the IApp
 * 
 * @author yangwm Oct 10, 2010 10:42:33 PM
 */
public class SampleApp implements IApp {

    public void start() {
        System.out.println("do...");
        // do... 
    }

    public void shutDown() {
        // Do a graceful shutdown here
        System.out.println("Shutdown is called begin");
        
        // release operation and release resource
        String str = "";
        for (int i = 0; i < 20000; i++) {
            str += i;
        }
        
        System.out.println("Shutdown is called end");
    }

}