package basics.essential.environments;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class SimplePropertie {

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        
        // create and load default properties
        Properties defaultProps = new Properties();
        FileInputStream in = new FileInputStream("defaultProperties");
        defaultProps.load(in);
        in.close();

        // create application properties with default
        Properties applicationProps = new Properties(defaultProps);

        // now load properties from last invocation
        in = new FileInputStream("appProperties");
        applicationProps.load(in);
        in.close();
        
        /*Enumeration<Object> enumerat = applicationProps.elements();
        for ( ; enumerat.hasMoreElements(); ) {
            System.out.println(enumerat.nextElement());
        }*/
        
        /*Set<String> names = applicationProps.stringPropertyNames();
        for (Iterator<String> iter = names.iterator(); iter.hasNext(); ) {
            String key = iter.next();
            System.out.println(key + "=" + applicationProps.getProperty(key));
        }*/
        
        applicationProps.list(System.out);
    }

}
