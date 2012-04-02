/**
 * 
 */
package jref.hello5;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author yangwm in Nov 29, 2009 9:58:00 PM
 */
public class MessageProviderProp implements MessageProvider {
    
    private String fileName;
    
    public MessageProviderProp(String fileName) {
        this.fileName = fileName;
    }

    public String getMessage() {
        Properties prop = new Properties();
        try {
            //System.out.println(MessageProviderProp.class.getClassLoader().getResource(fileName).getPath());
            prop.load(MessageProviderProp.class.getClassLoader().getResourceAsStream(fileName));
            return prop.getProperty("message");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
