/**
 * 
 */

import com.ultrapower.log.Logger;
import com.ultrapower.log.LoggerFactory;
import com.ultrapower.logback.classic.LoggerContext;
import com.ultrapower.logback.classic.joran.JoranConfigurator;
import com.ultrapower.logback.core.joran.spi.JoranException;
import com.ultrapower.logback.core.util.StatusPrinter;

/**
 * @author yangwm in Jul 25, 2009 11:18:51 AM
 */
public class MyAppWithConfigFile {
    
    Logger logger = LoggerFactory.getLogger(MyAppWithConfigFile.class);
    
    public void doIt() {
        logger.debug("doing my job");
    }

    /**
     * create by yangwm in Jul 25, 2009 11:18:51 AM
     * @param args
     */
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(MyAppWithConfigFile.class);
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        
        try {
            JoranConfigurator configurator = new JoranConfigurator();
            lc.reset();
            configurator.setContext(lc);
            configurator.doConfigure("logback-config-3.xml");
        } catch (JoranException je) {
            StatusPrinter.print(lc.getStatusManager());
        }
        logger.info("Entering application.");
        MyAppWithConfigFile myApp = new MyAppWithConfigFile();
        myApp.doIt();
        logger.info("Exiting application.");
        
    }

}
