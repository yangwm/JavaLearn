/**
 * 
 */
package cmd;

/**
 * @author yangwm in Jan 21, 2010 10:01:08 AM
 */
public class Colon {
    public static final String EN = ":";

    public static final String CN = "：";

    public Colon() {
        super();
    }

    public static boolean endsWith(String str) {
        if (str == null) {
            return false;
        }
        str = str.trim();
        return str.endsWith(EN) || str.endsWith(CN);

    }
}
