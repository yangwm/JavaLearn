/**
 * 
 */
package util;

/**
 * ϵͳ�� 
 * 
 * @author yangwm in May 5, 2009 3:54:05 PM
 */
public class PathUtil {

    private final static String baseClassFilePath = PathUtil.class.getClassLoader().getResource("").getPath();
    
    private final static String basePath = getBaseClassFilePath().replaceAll("/classes","");
    
    public static String getBaseClassFilePath() {
        return baseClassFilePath;
    }

    public static String getBasePath() {
        return basePath;
    }
    
    public static String getXmlPath() {
        return baseClassFilePath + "xml/";
    }
    
}
