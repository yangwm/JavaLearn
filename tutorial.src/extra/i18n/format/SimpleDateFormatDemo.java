/**
 * 
 */
package extra.i18n.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SimpleDateFormatDemo {

   static public void displayDate(Locale currentLocale) {

      Date today;
      String result;
      SimpleDateFormat formatter;

      formatter = new SimpleDateFormat("EEE d MMM yy", currentLocale);
      today = new Date();
      result = formatter.format(today);

      System.out.println("Locale: " + currentLocale.toString());
      System.out.println("Result: " + result);
   }


   static public void displayPattern(String pattern, Locale currentLocale) {

      Date today;
      SimpleDateFormat formatter;
      String output;

      formatter = new SimpleDateFormat(pattern, currentLocale);
      today = new Date();
      output = formatter.format(today);

      System.out.println(pattern + "   " + output);
   }

   static public void main(String[] args) {

      Locale[] locales = {
          new Locale("zh","CN"),              
          new Locale("fr","FR"),
          new Locale("de","DE"),
          new Locale("en","US")
      };

      for (int i = 0; i < locales.length; i++) {
         displayDate(locales[i]);
         System.out.println();
      }

      String[] patterns = {
         "dd.MM.yy",
         "yyyy.MM.dd G 'at' hh:mm:ss z",
         "EEE, MMM d, ''yy",
         "h:mm a",
         "H:mm",
         "H:mm:ss:SSS",
         "K:mm a,z",
         "yyyy.MMMMM.dd GGG hh:mm aaa"
      };

      for (int k = 0; k < patterns.length; k++) {
         displayPattern(patterns[k], new Locale("en","US"));
         System.out.println();
      }

      System.out.println();
      
      
   }
}

/*
Locale: zh_CN
Result: 星期三 2 六月 10

Locale: fr_FR
Result: mer. 2 juin 10

Locale: de_DE
Result: Mi 2 Jun 10

Locale: en_US
Result: Wed 2 Jun 10

dd.MM.yy   02.06.10

yyyy.MM.dd G 'at' hh:mm:ss z   2010.06.02 AD at 05:36:06 CST

EEE, MMM d, ''yy   Wed, Jun 2, '10

h:mm a   5:36 PM

H:mm   17:36

H:mm:ss:SSS   17:36:06:531

K:mm a,z   5:36 PM,CST

yyyy.MMMMM.dd GGG hh:mm aaa   2010.June.02 AD 05:36 PM


*/
