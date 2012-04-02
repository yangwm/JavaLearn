/**
 * 
 */
package extra.i18n.format;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatSymbolsDemo {

   static public void changeWeekDays() {

      Date today;
      String result;
      SimpleDateFormat formatter;
      DateFormatSymbols symbols;
      String[] defaultDays;
      String[] modifiedDays;

      symbols = new DateFormatSymbols(new Locale("en","US"));
      defaultDays = symbols.getShortWeekdays();

      for (int i = 0; i < defaultDays.length; i++) {
         System.out.print(defaultDays[i] + "  ");
      }
      System.out.println();

      String[] capitalDays = {
         "", "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
      symbols.setShortWeekdays(capitalDays);

      modifiedDays = symbols.getShortWeekdays();
      for (int i = 0; i < modifiedDays.length; i++) {
         System.out.print(modifiedDays[i] + "  ");
      }

      System.out.println();
      System.out.println();

      formatter = new SimpleDateFormat("E", symbols);
      today = new Date();
      result = formatter.format(today);
      System.out.println(result);
   }

   static public void main(String[] args) {
      changeWeekDays();
   }
}

/*
  Sun  Mon  Tue  Wed  Thu  Fri  Sat  
  SUN  MON  TUE  WED  THU  FRI  SAT  

WED

*/
