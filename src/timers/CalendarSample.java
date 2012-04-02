package timers;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class CalendarSample {
    

    static class CalendarSampleTest1 {
        public static void main(String[] args) {
            Calendar calendar = Calendar.getInstance();;
            System.out.println("before calendar.getTime()=" + calendar.getTime());
            calendar.setTime(new Date(108, 7, 1, 13, 54));
            System.out.println("after calendar.getTime()=" + calendar.getTime());
            
            System.out.println("" + (calendar.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()));

        }
    }
    
    
    static class CalendarSampleTest2 {
        public static void main(String[] args) {
            // get the supported ids for GMT-08:00 (Pacific Standard Time)
           String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
           // if no ids were returned, something is wrong. get out.
           if (ids.length == 0)
               System.exit(0);

            // begin output
           System.out.println("Current Time");

           // create a Pacific Standard Time time zone
           SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

           // set up rules for daylight savings time
           pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
           pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

           // create a GregorianCalendar with the Pacific Daylight time zone
           // and the current date and time
           Calendar calendar = new GregorianCalendar(pdt);
           Date trialTime = new Date();
           calendar.setTime(trialTime);

           // print out a bunch of interesting things
           System.out.println("ERA: " + calendar.get(Calendar.ERA));
           System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
           System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
           System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
           System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
           System.out.println("DATE: " + calendar.get(Calendar.DATE));
           System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
           System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
           System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
           System.out.println("DAY_OF_WEEK_IN_MONTH: "
                              + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
           System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
           System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
           System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
           System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
           System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
           System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
           System.out.println("ZONE_OFFSET: "
                              + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000)));
           System.out.println("DST_OFFSET: "
                              + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000)));

           System.out.println("Current Time, with hour reset to 3");
           calendar.clear(Calendar.HOUR_OF_DAY); // so doesn't override
           calendar.set(Calendar.HOUR, 3);
           System.out.println("ERA: " + calendar.get(Calendar.ERA));
           System.out.println("YEAR: " + calendar.get(Calendar.YEAR));
           System.out.println("MONTH: " + calendar.get(Calendar.MONTH));
           System.out.println("WEEK_OF_YEAR: " + calendar.get(Calendar.WEEK_OF_YEAR));
           System.out.println("WEEK_OF_MONTH: " + calendar.get(Calendar.WEEK_OF_MONTH));
           System.out.println("DATE: " + calendar.get(Calendar.DATE));
           System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
           System.out.println("DAY_OF_YEAR: " + calendar.get(Calendar.DAY_OF_YEAR));
           System.out.println("DAY_OF_WEEK: " + calendar.get(Calendar.DAY_OF_WEEK));
           System.out.println("DAY_OF_WEEK_IN_MONTH: "
                              + calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH));
           System.out.println("AM_PM: " + calendar.get(Calendar.AM_PM));
           System.out.println("HOUR: " + calendar.get(Calendar.HOUR));
           System.out.println("HOUR_OF_DAY: " + calendar.get(Calendar.HOUR_OF_DAY));
           System.out.println("MINUTE: " + calendar.get(Calendar.MINUTE));
           System.out.println("SECOND: " + calendar.get(Calendar.SECOND));
           System.out.println("MILLISECOND: " + calendar.get(Calendar.MILLISECOND));
           System.out.println("ZONE_OFFSET: "
                  + (calendar.get(Calendar.ZONE_OFFSET)/(60*60*1000))); // in hours
           System.out.println("DST_OFFSET: "
                  + (calendar.get(Calendar.DST_OFFSET)/(60*60*1000))); // in hours
           
           
           System.out.println("-------------------------------------------------");
           for (int i = 0; i <= 12; i++) {
               System.out.println("calendar.getTime(): " + calendar.getTime());
               //System.out.println("DAY_OF_MONTH: " + calendar.get(Calendar.DAY_OF_MONTH));
               //System.out.println("DATE: " + calendar.get(Calendar.DATE));
               System.out.println("getActualMaximum DAY_OF_MONTH: " + calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
               System.out.println("getActualMaximum DATE: " + calendar.getActualMaximum(Calendar.DATE));
               System.out.println("getMaximum DAY_OF_MONTH: " + calendar.getMaximum(Calendar.DAY_OF_MONTH));
               System.out.println("getMaximum DATE: " + calendar.getMaximum(Calendar.DATE));
               /*calendar.add(Calendar.DAY_OF_MONTH, calendar
                       .getActualMaximum(Calendar.DAY_OF_MONTH) - 1);*/
               calendar.add(Calendar.MONTH, 1);
           }
           
           /*System.out.println("-------------------------------------------------");
           for (int i = 0; i <= 12; i++) {
               int thisDay = calendar.get(Calendar.DAY_OF_YEAR);
               System.out.println("calendar.getTime(): " + calendar.getTime());
               calendar.add(Calendar.MONTH,1);
               System.out.println("calendar.getTime(): " + calendar.getTime());
               int nextDay = calendar.get(Calendar.DAY_OF_YEAR);
               System.out.println("(nextDay - thisDay): " + (nextDay - thisDay));
           }*/
       }
    }
    
}
