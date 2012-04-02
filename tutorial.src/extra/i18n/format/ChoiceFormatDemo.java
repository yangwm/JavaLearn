/**
 * 
 */
package extra.i18n.format;

import java.util.*;
import java.text.*;

public class ChoiceFormatDemo {

   static void displayMessages(Locale currentLocale) {

      System.out.println("currentLocale = " + currentLocale.toString());
      System.out.println();

      ResourceBundle bundle = 
         ResourceBundle.getBundle("extra/i18n/format/ChoiceBundle",currentLocale);

      MessageFormat messageForm = new MessageFormat("");
      messageForm.setLocale(currentLocale);

      double[] fileLimits = {0,1,2};

      String [] fileStrings = {
         bundle.getString("noFiles"),
         bundle.getString("oneFile"),
         bundle.getString("multipleFiles")
      };

      ChoiceFormat choiceForm = new ChoiceFormat(fileLimits, fileStrings);

      String pattern = bundle.getString("pattern");
      Format[] formats = {choiceForm, null, NumberFormat.getInstance()};

      messageForm.applyPattern(pattern);
      messageForm.setFormats(formats);

      Object[] messageArguments = {null, "XDisk", null};

      for (int numFiles = 0; numFiles < 4; numFiles++) {
         messageArguments[0] = new Integer(numFiles);
         messageArguments[2] = new Integer(numFiles);
         String result = messageForm.format(messageArguments);
         System.out.println(result);
      }
   }

   static public void main(String[] args) {
      displayMessages(new Locale("en", "US"));
      System.out.println();
      displayMessages(new Locale("fr", "FR"));
   }
} 

/*
currentLocale = en_US

There are no files on XDisk.
There is one file on XDisk.
There are 2 files on XDisk.
There are 3 files on XDisk.

currentLocale = fr_FR

Il n' y a pas des fichiers sur XDisk.
Il y a un fichier sur XDisk.
Il y a 2 fichiers sur XDisk.
Il y a 3 fichiers sur XDisk.

*/

