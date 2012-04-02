/**
 * 
 */
package basics.essential.regexs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangwm in Jan 5, 2010 10:14:51 PM
 */
public class ReplaceDemo3 {

    private static String REGEX = "\\s\\w+\\.";
    private static String INPUT = " ca.app, ca.biz";
    private static String REPLACE = "rs.";
 
    public static void main(String[] args) {
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(INPUT); // get a matcher object
        
        int count = 0;
        while(m.find()) {
            count++;
            System.out.println("Match number "+count);
            System.out.println("start(): "+m.start());
            System.out.println("end(): "+m.end());
        }
        
        INPUT = m.replaceAll(REPLACE);
        System.out.println(INPUT);
    }

}
