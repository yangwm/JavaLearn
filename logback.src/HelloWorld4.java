/**
 * 
 */

import com.ultrapower.log.Logger;
import com.ultrapower.log.LoggerFactory;
import com.ultrapower.logback.classic.LoggerContext;
import com.ultrapower.logback.core.util.StatusPrinter;

/**
 * 先import com.ultrapower.log.Logger和com.ultrapower.log.LoggerFactory，
 * 然后调用静态方法com.ultrapower.log.LoggerFactory方法得到logger。
 * 
 * 
 * @author yangwm in Jul 25, 2009 11:15:32 AM
 */
public class HelloWorld4 {

    /**
     * create by yangwm in Jul 25, 2009 11:15:32 AM
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("HelloWorld4");
        logger.debug("Hello world.");
        // print internal state
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }

}

/*
12:17:59.640 [main] DEBUG HelloWorld4 - Hello world.
12:17:59,578 |-INFO in com.ultrapower.logback.classic.LoggerContext[default] - Could NOT find resource [logback-test.xml]
12:17:59,578 |-INFO in com.ultrapower.logback.classic.LoggerContext[default] - Could NOT find resource [logback.xml]
12:17:59,578 |-INFO in com.ultrapower.logback.classic.LoggerContext[default] - Setting up default configuration.

*/
