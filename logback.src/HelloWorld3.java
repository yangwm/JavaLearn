/**
 * 
 */

import com.ultrapower.log.Logger;
import com.ultrapower.log.LoggerFactory;

/**
 * 先import com.ultrapower.log.Logger和com.ultrapower.log.LoggerFactory，
 * 然后调用静态方法com.ultrapower.log.LoggerFactory方法得到logger。
 * 
 * @author yangwm in Jul 25, 2009 10:53:29 AM
 */
public class HelloWorld3 {

    /**
     * create by yangwm in Jul 25, 2009 10:53:29 AM
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("HelloWorld3");
        logger.debug("Hello world.");
    }

}

/*
12:16:07.703 [main] DEBUG HelloWorld3 - Hello world.
*/
