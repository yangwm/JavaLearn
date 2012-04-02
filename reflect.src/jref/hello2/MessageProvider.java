/**
 * 
 */
package jref.hello2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author yangwm in Nov 29, 2009 9:19:10 PM
 */
public class MessageProvider {
    public String getMessage() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream("msg.properties"));
            return prop.getProperty("message");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
