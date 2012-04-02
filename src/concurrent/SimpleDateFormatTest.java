/**
 * 
 */
package concurrent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * @author yangwm Mar 31, 2012 1:19:19 PM
 */
public class SimpleDateFormatTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            String[] strs = new String[] {"1983-04-00", "1987-06-00", "1963-00-00", "1990-00-00", "0000-00-00"};
            for (String str : strs) {
                Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
                System.out.println(str + ", after parse:" + new SimpleDateFormat ("yyyy-MM-dd").format(date));
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
