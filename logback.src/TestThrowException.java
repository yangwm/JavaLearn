import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 */

/**
 * @author yangwm in Dec 4, 2009 3:38:02 PM
 */
public class TestThrowException {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(TestThrowException.class);

    /**
     * create by yangwm in Jul 25, 2009 10:58:55 AM
     * @param args
     */
    public static void main(String[] args) {
        try {
            throwsException();
            
            System.out.println("throws Exception end");
        } catch (Exception e) {
            System.out.println("b" + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    public static void throwsException() {
        try {
            Object obj = null;
            obj.toString();
        } catch (NullPointerException e) {
            logger.error("hha", e);
        }
    }

}
