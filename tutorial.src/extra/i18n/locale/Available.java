/**
 * 
 */
package extra.i18n.locale;

import java.text.DateFormat;
import java.util.Locale;

/**
 * which types of Locale definitions a locale-sensitive class recognizes
 * 
 * @author yangwm Jun 1, 2010 10:10:25 AM
 */
public class Available {
    static public void main(String[] args) {
        Locale list[] = DateFormat.getAvailableLocales();
        for (Locale aLocale : list) {
            System.out.println(aLocale.toString() 
                    + ", " + aLocale.getDisplayName() 
                    + ", " + aLocale.getDisplayName(Locale.US));
        }
    }
}

/*
ja_JP, 日文 (日本), Japanese (Japan)
es_PE, 西班牙文 (秘鲁), Spanish (Peru)
en, 英文, English
......

*/

