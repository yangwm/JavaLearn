/**
 * 
 */
package extra.i18n.format;

import java.util.*;
import java.text.*;

public class MessageFormatDemo {

   static  void displayMessage(Locale currentLocale) {

      System.out.println("currentLocale = " + currentLocale.toString());
      System.out.println();

      ResourceBundle messages = 
         ResourceBundle.getBundle("extra/i18n/format/MessageBundle",currentLocale);

      Object[] messageArguments = {
         messages.getString("planet"),
         new Integer(7),
         new Date()
      };

      MessageFormat formatter = new MessageFormat("");
      formatter.setLocale(currentLocale);

      formatter.applyPattern(messages.getString("template"));
      String output = formatter.format(messageArguments);

      System.out.println(output);

   }

   static public void main(String[] args) {
      displayMessage(new Locale("en", "US"));
      System.out.println();
      displayMessage(new Locale("de", "DE"));
   }
} 
