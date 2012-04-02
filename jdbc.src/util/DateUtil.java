/**
 * 
 */
package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;


/**
 * 日期操作相关工具类 
 * 
 * @author yangwm in Jan 20, 2010 9:59:21 AM
 */
public class DateUtil {
    
    /**
     * 解析日期.
     * @param input 输入字符串
     * @param pattern 类型
     * @return Date 对象
     */
    public static Date parseDate(String input, String pattern) {
        if(StringUtils.isEmpty(input)) {
            return null;
        }
        
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        
        try {
            return df.parse(input);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 格式化日期为字符串.
     * @param date 日期字符串
     * @param pattern 类型
     * @return 结果字符串
     */
    public static String formatDate(Date date, String pattern) {
        if(date == null || pattern == null) { return null; }
        return new SimpleDateFormat(pattern).format(date);
    }
    
}
