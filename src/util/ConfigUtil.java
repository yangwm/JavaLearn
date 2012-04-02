/**
 * 
 */
package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyStore;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import uri路径.Const;

/**
 * configuratin files utils 
 * 
 * @author yangwm in May 11, 2009 3:33:04 PM
 */
public class ConfigUtil {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(ConfigUtil.class);
     

    /**
     * 通过文件名（相对路径）得到文件名（绝对路径）
     * 
     * @param fileName
     * @return
     */
    public static String getConfigName(String fileName) {
        String result = UrlHelper.decode(Const.configUtilPath + fileName);
        logger.debug(result);
        return result;
    }
    
    /**
     * 通过文件名（相对路径）得到文件
     * 
     * @param fileName
     * @return
     */
    public static File getConfigFile(String fileName) {
        return new File(getConfigName(fileName));
    }
    
    /**
     * 通过文件名（相对路径）得到文件URL 
     * 
     * @param fileName
     * @return
     */
    public static URL getConfigUrl(String fileName) {
        URL url = Const.class.getClassLoader().getResource(Const.relativeConfigPath + fileName);
        return url;
    }
    
    /**
     * 通过文件名（相对路径）得到文件
     * 
     * modify by yangwm in Mar 8, 2010 11:16:55 AM 把getConfigName改为getConfigUrl 
     * @param fileName
     * @return
     * @throws FileNotFoundException 
     */
    public static InputStream getConfigInputStream(String fileName) throws IOException {
        //return new FileInputStream(getConfigName(fileName));
        return getConfigUrl(UrlHelper.decode(fileName)).openStream();
    }

    /**
     * 通过配置文件名（相对路径）得到Properties  
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
        } catch (Exception e) {
            logger.error("getConfigProperties(): error" + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("getConfigProperties(): Close InputStream error");
                }
            }
        }
        return env;
    }
    
    
    /**
     * 通过配置文件名（相对路径）、密码得到KeyStore  
     * 
     * @param fileName
     * @return
     */
    public static KeyStore getConfigKeyStore(String jksPath, String jksPassword) {
        KeyStore trustStore  = null;
        InputStream is = null;
        try {
            is = getConfigInputStream(jksPath);
            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(is, jksPassword.toCharArray());
        } catch (Exception e) {
            logger.error("getConfigKeyStore(): error" + e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("getConfigKeyStore(): Close InputStream error");
                }
            }
        }
        return trustStore;
    }
    
    
    /**
     * 通过文件名（相对路径）得到文件输出流 
     * 
     * @param fileName
     * @return
     * @throws FileNotFoundException 
     */
    public static OutputStream getConfigOutputStream(String fileName) throws FileNotFoundException {
        return new FileOutputStream(getConfigName(fileName));
    }
    
    /**
     * 通过配置文件名（相对路径）修改Properties 
     * 
     * @param fileName
     * @return
     */
    public static Properties saveConfigProperties(String fileName, Properties env) {
        OutputStream os = null;
        try {
            os = getConfigOutputStream(fileName);
            env.store(os, "");
        } catch (Exception e) {
            logger.error("saveConfigProperties(): error" + e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("saveConfigProperties(): Close OutputStream error");
                }
            }
        }
        return env;
    }
    
    /**
     * create by yangwm in Aug 14, 2009 5:12:16 PM
     * @param args
     * @throws FileNotFoundException 
     * @throws UnsupportedEncodingException 
     * @throws IOException 
     */
    public static void main(String[] args) {
        System.out.println(ConfigUtil.getConfigProperties("config test.properties"));
        System.out.println(ConfigUtil.getConfigProperties("config%20test.properties"));
    }
    
}
