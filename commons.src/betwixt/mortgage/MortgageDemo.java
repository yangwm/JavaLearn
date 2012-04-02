/**
 * 
 */
package betwixt.mortgage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.betwixt.io.BeanReader;
import org.apache.commons.betwixt.io.BeanWriter;


/**
 * @author yangwm in Apr 27, 2009 10:17:58 PM
 */
public class MortgageDemo {

    /**
     * create by yangwm in Apr 27, 2009 10:17:59 PM
     * @param args
     */
    public static void main(String[] args) {
        new MortgageDemo().beanToXml();
        new MortgageDemo().xmlToBean();
    }
    
    public void beanToXml() {
        FileWriter outputStream = null;
        try {
            StringWriter outputWriter = new StringWriter();
            outputWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
             
            BeanWriter beanWriter = new BeanWriter(outputWriter);
            beanWriter.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
            beanWriter.getBindingConfiguration().setMapIDs(false);
            beanWriter.enablePrettyPrint();
             
            Mortgage mortgage = new Mortgage(6.5f, 25);
            beanWriter.write("mortgage", mortgage);
             
            String xml = outputWriter.toString();
            outputStream = new FileWriter("mortgage.xml");
            outputStream.write(xml);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void xmlToBean() {
        try {
            BeanReader beanReader = new BeanReader();
            beanReader.registerBeanClass("mortgage", Mortgage.class);
            
            Mortgage mortgageConverted = (Mortgage)beanReader.parse(new File("mortgage.xml"));
            System.out.println("Rate: " + mortgageConverted.getRate() 
                    + ", Years: " + mortgageConverted.getYears());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
