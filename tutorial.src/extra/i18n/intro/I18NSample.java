/**
 * 
 */
package extra.i18n.intro;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Internationalizing the Sample Program
 * 
 * @author yangwm Jun 1, 2010 9:27:19 AM
 */
public class I18NSample {

    static public void main(String[] args) {

        String language;
        String country;

        if (args.length != 2) {
            language = new String("en");
            country = new String("US");
        } else {
            language = new String(args[0]);
            country = new String(args[1]);
        }

        Locale currentLocale;
        ResourceBundle messages;

        currentLocale = new Locale(language, country);

        messages = ResourceBundle.getBundle("extra/i18n//intro/MessagesBundle",
                                           currentLocale);
        System.out.println(messages.getString("greetings"));
        System.out.println(messages.getString("inquiry"));
        System.out.println(messages.getString("farewell"));
    }
}

/*
D:\study\tempProject\JavaLearn\classes>java extra.i18n.I18NSample de DE
Hallo.
Wie geht's?
Tschü?.

D:\study\tempProject\JavaLearn\classes>java extra.i18n.I18NSample fr FR
Bonjour.
Comment allez-vous?
Au revoir.

D:\study\tempProject\JavaLearn\classes>java extra.i18n.I18NSample en US
Hello.
How are you?
Goodbye.

D:\study\tempProject\JavaLearn\classes>java extra.i18n.I18NSample
Hello.
How are you?
Goodbye.

D:\study\tempProject\JavaLearn\classes>java extra.i18n.I18NSample fr CA
Hello.
How are you?
Goodbye.

*/

