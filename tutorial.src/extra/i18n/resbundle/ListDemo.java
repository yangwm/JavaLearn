/**
 * 
 */
package extra.i18n.resbundle;

import java.util.Locale;
import java.util.ResourceBundle;

public class ListDemo {

   static void displayValues(Locale currentLocale) {

      ResourceBundle stats = 
         ResourceBundle.getBundle("extra.i18n.resbundle.StatsBundle",currentLocale);

      Integer gdp = (Integer)stats.getObject("GDP");
      System.out.println("GDP = " + gdp.toString());
      Integer pop = (Integer)stats.getObject("Population");
      System.out.println("Population = " + pop.toString());
      Double lit = (Double)stats.getObject("Literacy");
      System.out.println("Literacy = " + lit.toString());
      
   } // displayValues

   static public void main(String[] args) {

      Locale[] supportedLocales = {
         new Locale("en","CA"),
         new Locale("ja","JP"),
         new Locale("fr","FR")
      };

      for (int i = 0; i < supportedLocales.length; i ++) {
         System.out.println("Locale = " + supportedLocales[i]);
         displayValues(supportedLocales[i]);
         System.out.println();
      }

   } // main

} // class

/*
Locale = en_CA
GDP = 24400
Population = 28802671
Literacy = 0.97

Locale = ja_JP
GDP = 21300
Population = 125449703
Literacy = 0.99

Locale = fr_FR
GDP = 20200
Population = 58317450
Literacy = 0.99

*/
