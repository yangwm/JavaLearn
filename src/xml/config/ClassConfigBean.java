/**
 * 
 */
package xml.config;

/**
 * 
 * 
 * @author yangwm Apr 19, 2010 3:23:34 PM
 */
public class ClassConfigBean {

    private static String ip;
    private static String port;
    
    public static String getIp() {
        return ip;
    }
    public static void setIp(String ip) {
        ClassConfigBean.ip = ip;
    }
    public static String getPort() {
        return port;
    }
    public static void setPort(String port) {
        ClassConfigBean.port = port;
    }

}
