/**
 * 
 */
package extra.i18n.resbundle;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Customizing Resource Bundle Loading
 * 
 * @author yangwm Jun 1, 2010 11:12:51 AM
 */
public class RBControl {
    public static void main(String[] args) {
    	test(Locale.CHINA);
    	test(new Locale("zh", "HK"));
    	test(Locale.TAIWAN);
    	test(Locale.CANADA);
    }

    private static void test(Locale locale) {
    	ResourceBundle rb = ResourceBundle.getBundle("extra.i18n.resbundle.RBControl", locale,
    	     new ResourceBundle.Control() {
        		 @Override
        		 public List<Locale> getCandidateLocales(String baseName, Locale locale) {
        		     if (baseName == null) {
        		         throw new NullPointerException();
        		     }
        		     
        		     if (locale.equals(new Locale("zh", "HK"))) {
            			 return Arrays.asList(
            			     locale,
            			     Locale.TAIWAN,
            			     // no Locale.CHINESE here
            			     Locale.ROOT);
        		     } else if (locale.equals(Locale.TAIWAN)) {
            			 return Arrays.asList(
            			     locale,
            			     // no Locale.CHINESE here
            			     Locale.ROOT);
        		     }
        		     return super.getCandidateLocales(baseName, locale);
        		 }
        	}
    	);
    	
    	System.out.println("locale: " + locale);
    	System.out.println("\tregion: " + rb.getString("region"));
    	System.out.println("\tlanguage: " + rb.getString("language"));
    }
}

/*
locale: zh_CN
    region: China
    language: Simplified Chinese
locale: zh_HK
    region: Hong Kong
    language: Traditional Chinese
locale: zh_TW
    region: Taiwan
    language: Traditional Chinese
locale: en_CA
    region: China
    language: Simplified Chinese

*/
