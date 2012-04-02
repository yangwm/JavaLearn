/**
 * 
 */
package extra.i18n.format;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Numbers and Currencies: Using Predefined Formats
 * 
 * @author yangwm Jun 2, 2010 5:05:15 PM
 */
public class NumberFormatDemo {

    static public void displayNumber(Locale currentLocale) {

       Integer quantity = new Integer(123456);
       Double amount = new Double(345987.246);
       NumberFormat numberFormatter;
       String quantityOut;
       String amountOut;

       numberFormatter = NumberFormat.getNumberInstance(currentLocale);
       quantityOut = numberFormatter.format(quantity);
       amountOut = numberFormatter.format(amount);
       System.out.println(quantityOut + "   " + currentLocale.toString());
       System.out.println(amountOut + "   " + currentLocale.toString());
    }

    static public void displayCurrency(Locale currentLocale) {

       Double currency = new Double(9876543.21);
       NumberFormat currencyFormatter;
       String currencyOut;

       currencyFormatter = NumberFormat.getCurrencyInstance(currentLocale);
       currencyOut = currencyFormatter.format(currency);
       System.out.println(currencyOut + "   " + currentLocale.toString());
    }

    static public void displayPercent(Locale currentLocale) {

       Double percent = new Double(0.75);
       NumberFormat percentFormatter;
       String percentOut;

       percentFormatter = NumberFormat.getPercentInstance(currentLocale);
       percentOut = percentFormatter.format(percent);
       System.out.println(percentOut + "   " + currentLocale.toString());
    }

    static public void main(String[] args) {

       Locale[] locales = {
           new Locale("zh","CN"),           
           new Locale("fr","FR"),
           new Locale("de","DE"),
           new Locale("en","US")
       };

       for (int i = 0; i < locales.length; i++) {
          System.out.println();
          displayNumber(locales[i]);
          displayCurrency(locales[i]);
          displayPercent(locales[i]);
       }
    }

}

/*

123,456   zh_CN
345,987.246   zh_CN
￥9,876,543.21   zh_CN
75%   zh_CN

123?456   fr_FR
345?987,246   fr_FR
9?876?543,21 ?   fr_FR
75 %   fr_FR

123.456   de_DE
345.987,246   de_DE
9.876.543,21 ?   de_DE
75%   de_DE

123,456   en_US
345,987.246   en_US
$9,876,543.21   en_US
75%   en_US

*/

