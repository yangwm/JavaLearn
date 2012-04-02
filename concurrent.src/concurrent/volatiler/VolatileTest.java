package concurrent.volatiler;
/**
 * 
 */


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * volatile Test
 * 
 * @author yangwm May 9, 2010 7:15:00 PM
 */
public class VolatileTest {
    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final VolatileRefreshView volatileRefreshView = new VolatileRefreshView();
            try {
                //Thread.sleep(200);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            new Thread(new Runnable() {
                public void run() {
                    volatileRefreshView.refreshView();
                }
            }).start();
        }
        
    }

}

class VolatileRefreshView {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(VolatileTest.class);
    
    private volatile static boolean refreshFlag = false;

    public String refreshView() {
        logger.debug(">>>refreshView(), refreshFlag=" + refreshFlag);

        String msg = "normal";

        if(!refreshFlag){
            synchronized (this) {
                refreshFlag = true;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                refreshFlag = false;
            }
        }else{
            msg = "refreshing";
        }

        logger.debug("<<<refreshView():" + msg + "), refreshFlag=" + refreshFlag);
        return msg;
    }

}

/*
Thread.sleep(200) : 
19:22:36,421 [Thread-0] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=false
19:22:36,625 [Thread-1] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:36,625 [Thread-1] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:36,828 [Thread-2] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:36,828 [Thread-2] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:37,015 [Thread-3] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:37,015 [Thread-3] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:37,218 [Thread-4] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:37,218 [Thread-4] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:37,421 [Thread-5] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:37,421 [Thread-5] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:37,625 [Thread-6] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:37,625 [Thread-6] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:37,828 [Thread-7] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:37,828 [Thread-7] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:38,031 [Thread-8] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:38,031 [Thread-8] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:38,218 [Thread-9] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:22:38,218 [Thread-9] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:22:41,437 [Thread-0] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():normal), refreshFlag=false

*/

/*
Thread.sleep(1000) : 
19:26:05,062 [Thread-0] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=false
19:26:06,062 [Thread-1] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:06,062 [Thread-1] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:07,062 [Thread-2] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:07,062 [Thread-2] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:08,062 [Thread-3] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:08,062 [Thread-3] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:09,062 [Thread-4] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:09,078 [Thread-4] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:10,062 [Thread-5] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:10,062 [Thread-5] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:10,062 [Thread-0] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():normal), refreshFlag=false
19:26:11,062 [Thread-6] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=false
19:26:12,062 [Thread-7] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:12,062 [Thread-7] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:13,062 [Thread-8] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:13,062 [Thread-8] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:14,062 [Thread-9] DEBUG [VolatileTest.refreshView(27)]  - >>>refreshView(), refreshFlag=true
19:26:14,062 [Thread-9] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():refreshing), refreshFlag=true
19:26:16,062 [Thread-6] DEBUG [VolatileTest.refreshView(47)]  - <<<refreshView():normal), refreshFlag=false

*/

