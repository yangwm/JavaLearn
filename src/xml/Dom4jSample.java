/**
 * 
 */
package xml;

import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * Dom4j样例：简单读写  
 * 
 * @author yangwm in Dec 19, 2009 11:09:10 PM
 */
public class Dom4jSample {

    /**
     * create by yangwm in Dec 19, 2009 11:09:11 PM
     * @param args
     * @throws DocumentException 
     */
    public static void main(String[] args) {
        String fileName = "D:/study/tempProject/JavaLearn/sample.xml";
        FileWriter writer = null;
        
        try {
            SAXReader saxReader = new SAXReader();
            Document document = saxReader.read(fileName);
            Element rootElement = document.getRootElement();
            
            Element titleElement = rootElement.element("title");
            System.out.println(titleElement.getText());
            
            Element listElement = rootElement.element("list");
            for (Object obj : listElement.elements()) {
                System.out.println(((Element)obj).getText());
            }
            
            // modify rootElement's text 
            titleElement.setText(titleElement.getText() + " | yangwm's modify");
            
            String newFileName = fileName + ".txt";
            System.out.println(newFileName);
            writer = new FileWriter(newFileName);
            document.write(writer);
        } catch (DocumentException e ) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        
    }

}
