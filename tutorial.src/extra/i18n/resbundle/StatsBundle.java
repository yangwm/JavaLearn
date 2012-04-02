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
public class StatsBundle extends ListResourceBundle {

   public Object[][] getContents() {
     return contents;
   }

   private Object[][] contents = {
      {"GDP", new Integer(0)},
      {"Population", new Integer(0)},
      {"Literacy", new Double(0.00)},
   };

} 
