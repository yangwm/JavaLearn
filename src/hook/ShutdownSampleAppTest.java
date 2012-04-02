/**
 * 
 */
package hook;

/**
 * Do a graceful shutdown of your Java Application when Ctr-C, Kill... :
 * add a shutdown hook to the Java runtime, using the Runtime.getRunTime().addShutdownHook method.
 * When it exits, ShutdownInterceptor will call application shutdown method automatically.
 * 
 * @author yangwm Oct 10, 2010 10:43:53 PM
 */
public class ShutdownSampleAppTest {

    public static void main(String args[]) {
        try {
            IApp app = new SampleApp();
            ShutdownInterceptor shutdownInterceptor = new ShutdownInterceptor(app);
            Runtime.getRuntime().addShutdownHook(shutdownInterceptor);
            app.start();
            
            for (int i = 0; i < 1000; i++) {
                try {
                    System.out.println(i + " times sleep 1000 ");
                    Thread.sleep(1000);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } finally {
            System.out.println(" ShutdownSampleAppTest finally end ");
        }
    }
    
}

/*
当输出1 times sleep 1000后，Ctrl+C(can't enter finally body):
D:\study\tempProjects\yangwmProject\JavaLearn\classes>java hook.ShutdownSampleAppTest
do...
0 times sleep 1000
1 times sleep 1000
Call the shutdown routine
Shutdown is called begin
2 times sleep 1000
3 times sleep 1000
Shutdown is called end

D:\study\tempProjects\yangwmProject\JavaLearn\classes>
*/
