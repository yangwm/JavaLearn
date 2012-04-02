/**
 * 
 */
package json.ext;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * config file util  
 * 
 * @author yangwm in May 11, 2009 3:33:04 PM
 */
class ConfigUtil {

    /**
	 * use file name get file URL 
     * 
     * @param fileName
     * @return
     */
    public static URL getConfigUrl(String fileName) {
        URL url = ConfigUtil.class.getClassLoader().getResource(fileName);
        return url;
    }
    
    /**
     * use file name get file input stream 
     * 
     * @param fileName
     * @return
     * @throws IOException 
     */
    public static InputStream getConfigInputStream(String fileName) throws IOException {
        return getConfigUrl(fileName).openStream();
    }
    
    /**
     * use file name get file URL Properties  
     * 
     * @param fileName
     * @return
     */
    public static Properties getConfigProperties(String fileName) {
        Properties env = new Properties();
        InputStream is = null;
        try {
            is = getConfigInputStream(fileName);
            env.load(is);
            System.err.println("getProperties(" + fileName + "): success");
        } catch (Exception e) {
            System.err.println("getProperties(" + fileName + "): " + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.err.println("getProperties(" + fileName + "): Close InputStream error");
                }
            }
        }
        return env;
    }
    
}
