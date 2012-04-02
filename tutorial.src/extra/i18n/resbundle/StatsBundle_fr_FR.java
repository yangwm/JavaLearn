/**
 * 
 */
package extra.i18n.resbundle;

import java.util.ListResourceBundle;

/**
 * 
 * 
 * @author yangwm Jun 1, 2010 11:12:51 AM
 */
public class StatsBundle_fr_FR extends ListResourceBundle {

   public Object[][] getContents() {
      return contents;
   }

   private Object[][] contents = {
      {"GDP", new Integer(20200)},
      {"Population", new Integer(58317450)},
      {"Literacy", new Double(0.99)},
   };
} 
