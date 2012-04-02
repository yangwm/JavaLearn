/**
 * 
 */
package jref.hello4;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author yangwm in Nov 29, 2009 9:56:36 PM
 */
public class MessageProviderXml implements MessageProvider {
    
    private String fileName = "message.xml";

    public String getMessage() {
        SAXReader saxReader = new SAXReader();
        try {
            //System.out.println(MessageProviderXml.class.getClassLoader().getResource(fileName).getPath());
            Document document = saxReader.read(MessageProviderXml.class.getClassLoader().getResourceAsStream(fileName));

            Element messageElement = document.getRootElement(); // or
            return messageElement.getText();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
