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
public class StatsBundle_ja_JP extends ListResourceBundle {

   public Object[][] getContents() {
      return contents;
   }
  
   private Object[][] contents = {
      {"GDP", new Integer(21300)},
      {"Population", new Integer(125449703)},
      {"Literacy", new Double(0.99)},
   };
} 
