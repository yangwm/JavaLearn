/**
 * 
 */


import org.apache.log4j.Logger;

/**
 * 先import org.apache.log4j.Logger，
 * 然后调用静态方法org.apache.log4j.Logger.getLogger()得到logger。
 * 
 * @author yangwm in Jul 25, 2009 10:58:55 AM
 */
public class HelloWorld2 {
    
    /**
     * create by yangwm in Jul 25, 2009 10:58:55 AM
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("HelloWorld2");
        logger.debug("Hello world.");   
    }

}

/*
12:16:46,000 [main] DEBUG [HelloWorld2.main(20)]  - Hello world.
*/

