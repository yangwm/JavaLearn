
/**
 * 
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 先import org.apache.commons.logging.Log.Logger和org.apache.commons.logging.Log.LoggerFactory，
 * 然后调用静态方法org.apache.commons.logging.Log.LoggerFactory方法得到logger。
 * 
 * @author yangwm in Jul 25, 2009 11:53:42 AM
 */
public class HelloWorld1 {

    /**
     * create by yangwm in Jul 25, 2009 11:53:42 AM
     * @param args
     */
    public static void main(String[] args) {
        Log logger = LogFactory.getLog("HelloWorld4");
        logger.debug("Hello world.");
    }

}

/*
17:16:37,937 [main] DEBUG [HelloWorld1.main(20)]  - Hello world.
*/
