/**
 * 
 */
package betwixt.person;

import java.io.StringReader;

import org.apache.commons.betwixt.io.BeanReader;

/**
 * @author yangwm in Apr 28, 2009 12:05:21 AM
 */
public class ReadExampleApp {
    
    public static final void main(String args[]) throws Exception{
        
        // First construct the xml which will be read in
        // For this example, read in from a hard coded string
        StringReader xmlReader = new StringReader(
                    "<?xml version='1.0' ?><person><age>25</age><name>James Smith</name></person>");
        
        // Now convert this to a bean using betwixt
        // Create BeanReader
        BeanReader beanReader  = new BeanReader();
        
        // Configure the reader
        // If you're round-tripping, make sure that the configurations are compatible!
        beanReader.getXMLIntrospector().getConfiguration().setAttributesForPrimitives(false);
        beanReader.getBindingConfiguration().setMapIDs(false);
        
        // Register beans so that betwixt knows what the xml is to be converted to
        // Since the element mapped to a PersonBean isn't called the same 
        // as Betwixt would have guessed, need to register the path as well
        beanReader.registerBeanClass("person", PersonBean.class);
        
        // Now we parse the xml
        PersonBean person = (PersonBean) beanReader.parse(xmlReader);
        
        // send bean to system out
        System.out.println(person);
    }
    
}

