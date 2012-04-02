/**
 * 
 */
package extra.i18n.resbundle;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * ResourceBundle with Properties Files
 * 
 * @author yangwm Jun 1, 2010 11:28:52 AM
 */
public class PropertiesDemo {

    static void displayValue(Locale currentLocale, String key) {

       ResourceBundle labels = 
          ResourceBundle.getBundle("extra/i18n/resbundle/LabelsBundle",currentLocale);
       String value  = labels.getString(key);
       System.out.println(
            "Locale = " + currentLocale.toString() + ", " +
            "key = " + key + ", " +
            "value = " + value);

    } // displayValue


    static void iterateKeys(Locale currentLocale) {

       ResourceBundle labels = 
          ResourceBundle.getBundle("extra/i18n/resbundle/LabelsBundle",currentLocale);

       Enumeration bundleKeys = labels.getKeys();

       while (bundleKeys.hasMoreElements()) {
          String key = (String)bundleKeys.nextElement();
          String value  = labels.getString(key);
          System.out.println("key = " + key + ", " +
            "value = " + value);
       }

    } // iterateKeys


    static public void main(String[] args) {

       Locale[] supportedLocales = {
          Locale.FRENCH,
          Locale.GERMAN,
          Locale.ENGLISH
       };

       for (int i = 0; i < supportedLocales.length; i ++) {
           displayValue(supportedLocales[i], "s2");
       }

       System.out.println();

       iterateKeys(supportedLocales[0]);

    } // main

} // class

/*
Locale = fr, key = s2, value = Disque dur
Locale = de, key = s2, value = Platte
Locale = en, key = s2, value = disk

key = s2, value = Disque dur
key = s1, value = Ordinateur
key = s3, value = Moniteur
key = s4, value = Clavier

*/

