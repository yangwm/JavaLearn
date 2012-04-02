/**
 * 
 */
package json.ext;

/**
 * 基础对象 
 * 
 * @author yangwm May 10, 2010 3:36:17 PM
 */
public class BaseInfo {
    
    private static final String DEFAULT_FORMAT = "JSON";
    
    private static final String JSON_FORMAT = "JSON";
    private static final String XML_FORMAT = "XML";

    // 输出ext可用的XML
    public String toXmlString() {
        //return new XmlHelper().getXmlFromBean(this);
        return null;
    }

    // 输出ext可用的JSON字符串
    public String toJSONString() {
        return JsonUtil.toJSONObjectFromBean(this).toString();
    }

    // 输出字符串
    public String toString(String format) {
        if (JSON_FORMAT.equalsIgnoreCase(format)) {
            return toJSONString();
        } else if (XML_FORMAT.equalsIgnoreCase(format)) {
            return toXmlString();
        }
        return toJSONString();
    }
    
    public String toString() {
        return toString(DEFAULT_FORMAT);
    }
    
}
