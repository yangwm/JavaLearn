/**
 * 
 */
package uri路径;


/**
 * global system constant 
 * 
 * @author yangwm in May 5, 2009 3:54:05 PM
 */
public class Const {

    public final static String baseClassPath = 
        Const.class.getClassLoader().getResource("").getPath();

    public final static String basePath = 
        baseClassPath.replaceAll("WEB-INF/classes/","");
    
    public final static String fullClassPath = 
        Const.class.getResource("").getPath();
    
    public final static String relativeConfigPath = "uri路径/";
    
    public final static String configPath = 
        Const.class.getClassLoader().getResource(relativeConfigPath).getPath();
    
    public final static String webConfigPath = 
        baseClassPath.replaceAll("WEB-INF/classes/","WEB-INF/conf/");
    
    public final static String configUtilPath = Const.configPath;
    
}
