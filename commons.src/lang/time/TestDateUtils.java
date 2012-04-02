package lang.time;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

public class TestDateUtils {

    /**
     * create by yangwm in Mar 24, 2009 11:58:02 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("current date:" + new Date());
        System.out.println(DateUtils.addDays(new Date(), 1));
        System.out.println(DateUtils.addWeeks(new Date(), 1));
        
        System.out.println(DateUtils.setYears(new Date(), 2020));
        
        System.out.println(DateUtils.round(new Date(), 2));
        System.out.println(DateUtils.truncate(new Date(), 2));
    }

}
