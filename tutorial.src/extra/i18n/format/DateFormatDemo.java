/**
 * 
 */
package extra.i18n.format;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatDemo {

   static public void displayDate(Locale currentLocale) {

      Date today;
      String dateOut;
      DateFormat dateFormatter;

      dateFormatter = 
         DateFormat.getDateInstance(DateFormat.DEFAULT, currentLocale);
      today = new Date();
      dateOut = dateFormatter.format(today);

      System.out.println(dateOut + "   " + currentLocale.toString());
   }

   static public void showBothStyles(Locale currentLocale) {

      Date today;
      String result;
      DateFormat formatter;

      int[] styles = {
         DateFormat.DEFAULT,
         DateFormat.SHORT, 
         DateFormat.MEDIUM,
         DateFormat.LONG,
         DateFormat.FULL
      };

      System.out.println();
      System.out.println("Locale: " + currentLocale.toString());
      System.out.println();

      today = new Date();

      for (int k = 0; k < styles.length; k++) {
         formatter = DateFormat.getDateTimeInstance(
                     styles[k], styles[k], currentLocale);
         result = formatter.format(today);
         System.out.println(result);
      }
   }

   static public void showDateStyles(Locale currentLocale) {

      Date today = new Date();
      String result;
      DateFormat formatter;

      int[] styles = {
         DateFormat.DEFAULT,
         DateFormat.SHORT, 
         DateFormat.MEDIUM,
         DateFormat.LONG,
         DateFormat.FULL
      };

      System.out.println();
      System.out.println("Locale: " + currentLocale.toString());
      System.out.println();

      for (int k = 0; k < styles.length; k++) {
         formatter = 
            DateFormat.getDateInstance(styles[k], currentLocale);
         result = formatter.format(today);
         System.out.println(result);
      }
   }

   static public void showTimeStyles(Locale currentLocale) {

      Date today = new Date();
      String result;
      DateFormat formatter;

      int[] styles = {
         DateFormat.DEFAULT,
         DateFormat.SHORT, 
         DateFormat.MEDIUM,
         DateFormat.LONG,
         DateFormat.FULL
      };

      System.out.println();
      System.out.println("Locale: " + currentLocale.toString());
      System.out.println();

      for (int k = 0; k < styles.length; k++) {
         formatter = 
            DateFormat.getTimeInstance(styles[k], currentLocale);
         result = formatter.format(today);
         System.out.println(result);
      }
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
      }

      showDateStyles(new Locale("zh","CN"));
      showDateStyles(new Locale("en","US"));
      showDateStyles(new Locale("fr","FR"));

      showTimeStyles(new Locale("zh","CN"));
      showTimeStyles(new Locale("en","US"));
      showTimeStyles(new Locale("de","DE"));
 
      showBothStyles(new Locale("zh","CN"));
      showBothStyles(new Locale("en","US"));
      showBothStyles(new Locale("fr","FR"));

   }
}

/*
2010-6-2   zh_CN
2 juin 2010   fr_FR
02.06.2010   de_DE
Jun 2, 2010   en_US

Locale: zh_CN

2010-6-2
10-6-2
2010-6-2
2010年6月2日
2010年6月2日 星期三

Locale: en_US

Jun 2, 2010
6/2/10
Jun 2, 2010
June 2, 2010
Wednesday, June 2, 2010

Locale: fr_FR

2 juin 2010
02/06/10
2 juin 2010
2 juin 2010
mercredi 2 juin 2010

Locale: zh_CN

17:41:50
下午5:41
17:41:50
下午05时41分50秒
下午05时41分50秒 CST

Locale: en_US

5:41:50 PM
5:41 PM
5:41:50 PM
5:41:50 PM CST
5:41:50 PM CST

Locale: de_DE

17:41:50
17:41
17:41:50
17:41:50 CST
17.41 Uhr CST

Locale: zh_CN

2010-6-2 17:41:50
10-6-2 下午5:41
2010-6-2 17:41:50
2010年6月2日 下午05时41分50秒
2010年6月2日 星期三 下午05时41分50秒 CST

Locale: en_US

Jun 2, 2010 5:41:50 PM
6/2/10 5:41 PM
Jun 2, 2010 5:41:50 PM
June 2, 2010 5:41:50 PM CST
Wednesday, June 2, 2010 5:41:50 PM CST

Locale: fr_FR

2 juin 2010 17:41:50
02/06/10 17:41
2 juin 2010 17:41:50
2 juin 2010 17:41:50 CST
mercredi 2 juin 2010 17 h 41 CST

*/

