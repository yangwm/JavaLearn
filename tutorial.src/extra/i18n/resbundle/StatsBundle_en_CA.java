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
public class StatsBundle_en_CA extends ListResourceBundle {

   public Object[][] getContents() {
     return contents;
   }

   private Object[][] contents = {
      {"GDP", new Integer(24400)},
      {"Population", new Integer(28802671)},
      {"Literacy", new Double(0.97)},
   };

} 
