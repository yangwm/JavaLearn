/**
 * 
 */
package extra.i18n.format;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Numbers and Currencies: Customizing Formats
 * 
 * @author yangwm Jun 2, 2010 5:25:44 PM
 */
public class DecimalFormatDemo {

    static public void customFormat(String pattern, double value ) {
       DecimalFormat myFormatter = new DecimalFormat(pattern);
       String output = myFormatter.format(value);
       System.out.println(value + "  " + pattern + "  " + output);
    }

    static public void localizedFormat(String pattern, double value, 
                                       Locale loc ) {
       NumberFormat nf = NumberFormat.getNumberInstance(loc);
       DecimalFormat df = (DecimalFormat)nf;
       df.applyPattern(pattern);
       String output = df.format(value);
       System.out.println(pattern + "  " + output + "  " + loc.toString());
    }

    static public void main(String[] args) {

       customFormat("###,###.###", 123456.789);
       customFormat("###.##", 123456.789);
       customFormat("000000.000", 123.78);
       customFormat("$###,###.###", 12345.67);
       customFormat("\u00a5###,###.###", 12345.67);

       Locale currentLocale = new Locale("en", "US");

       DecimalFormatSymbols unusualSymbols = 
          new DecimalFormatSymbols(currentLocale);
       unusualSymbols.setDecimalSeparator('|');
       unusualSymbols.setGroupingSeparator('^');
       String strange = "#,##0.###";
       DecimalFormat weirdFormatter = new DecimalFormat(strange, unusualSymbols);
       weirdFormatter.setGroupingSize(4);
       String bizarre = weirdFormatter.format(12345.678);
       System.out.println(bizarre);

       Locale[] locales = {
          new Locale("en", "US"),
          new Locale("de", "DE"),
          new Locale("fr", "FR")
       };

       for (int i = 0; i < locales.length; i++) {
          localizedFormat("###,###.###", 123456.789, locales[i]);
       }

    }
}

/*
123456.789  ###,###.###  123,456.789
123456.789  ###.##  123456.79
123.78  000000.000  000123.780
12345.67  $###,###.###  $12,345.67
12345.67  ?###,###.###  ?12,345.67
1^2345|678
###,###.###  123,456.789  en_US
###,###.###  123.456,789  de_DE
###,###.###  123?456,789  fr_FR

*/
