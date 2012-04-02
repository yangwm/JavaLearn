/**
 * 
 */
package extra.i18n.resbundle;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 
 * 
 * @author yangwm Jun 1, 2010 1:05:29 PM
 */
public class ButtonLabelDemo {
    
    /**
     * OkKey = OK
     * CancelKey = Cancel
     * 
     * @param args
     */
    static public void main(String[] args) {
        ResourceBundle buttonLabel = 
            ResourceBundle.getBundle("extra.i18n.resbundle.ButtonLabel", Locale.ENGLISH);
        String okLabel = buttonLabel.getString("OkKey");
        String cancelLabel = buttonLabel.getString("CancelKey");
        System.out.println(okLabel);
        System.out.println(cancelLabel);
    }
    
}

/*
OK
Cancel

*/
