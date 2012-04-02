package strings;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串工具类 
 * 
 * @author yangwm in Jan 3, 2009 9:46:18 PM
 */
public class StringUtil {
    
    /**
     * 加密因子 
     */
    private static final char[] hexDigits = { 
        '0', '1', '2', '3', '4', '5', '6', '7', '8', 
        '9', 'a', 'b', 'c', 'd', 'e', 'f' 
    };
    /**
     * 用某种算法来实现加密  
     */
    private static String algorithmic = "Printf";
    

    /**
     * 用MD5算法加密 
     * 
     * create by yangwm in Jan 3, 2009 9:41:52 PM
     * @param origin
     * @return 加密后的字符串 
     */
    public static String MD5Encode(String origin) {
        String resultStr = null;
        try {
            resultStr=new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultStr = byteArrayToHexStr(md.digest(resultStr.getBytes()));
            //System.out.println(resultStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return resultStr;
    }

    /**
     * 字节数组转换为16进制字串
     * 
     * create by yangwm in Jan 3, 2009 11:21:33 PM
     * @param bs 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexStr(byte[] bs) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < bs.length; i++) {
            resultSb.append( byteToHexStr(bs[i]) );
        }
        return resultSb.toString();
    }
    
    /**
     * 字节转换为16进制字串
     * 
     * create by yangwm in Jan 3, 2009 9:41:36 PM
     * @param b 字节
     * @return "02x"形式16进制字串 
     */
    public static String byteToHexStr(byte b) {
        return byteToHexStr(b, algorithmic);
    }
    
    /**
     * 字节转换为16进制字串
     * 
     * create by yangwm in Jan 3, 2009 9:41:36 PM
     * @param b 字节
     * @return "02x"形式16进制字串 
     */
    public static String byteToHexStr(byte b, String str) {
        if ( "Simple".equalsIgnoreCase(str) ) {
            return byteToHexStrWithSimple(b);
        } else if ( "Bit".equalsIgnoreCase(str) ) {
            return byteToHexStrWithBit(b);
        } else if ( "Integer".equalsIgnoreCase(str) ) {
            return byteToHexStrWithInteger(b);
        }
        
        return byteToHexStrWithPrintf(b);
    }
    
    /**
     * 字节转换为16进制字串
	 * 
     * 参见 http://java.sun.com/developer/technicalArticles/Programming/sprintf/
     * 
     * create by yangwm in Jan 3, 2009 10:15:28 PM
     * @param b 字节
     * @return "02x"形式16进制字串 
     */
    public static String byteToHexStrWithPrintf(byte b) {
        return new PrintfFormat("%02x").sprintf(byteToInt(b)).toString();
    }
    
    /**
     * 字节转换为16进制字串
     * 
     * create by yangwm in Jan 3, 2009 9:41:36 PM
     * @param b 字节
     * @return "02x"形式16进制字串 
     */
    public static String byteToHexStrWithSimple(byte b) {
        char[] chars = { hexDigits[byteToInt(b) / 16], hexDigits[byteToInt(b) % 16] };
        return new String( chars );
    }

    /**
     * 字节转换为16进制字串
     * 
     * create by yangwm in Jan 3, 2009 9:41:36 PM
     * @param b 字节
     * @return "02x"形式16进制字串 
     */
    public static String byteToHexStrWithBit(byte b) {
        char[] chars = { hexDigits[(b >>> 4) & 0x0f], hexDigits[b & 0x0f] };
        return new String(chars);
    }
    
    /**
     * 字节转换为16进制字串 （当返回字符串长度为1时，需补前导"0"）
     * 
     * Integer.toHexString得出的字符串不符合MD5算法， 因为它会去除前导0（如：把"00"会被转换成"00"，把"09"会被转换成"9"）
     * 
     * create by yangwm in Jan 3, 2009 10:39:04 PM
     * @param b 字节
     * @return "02x"形式16进制字串 
     */
    public static String byteToHexStrWithInteger(byte b) {
        String str = Integer.toHexString(byteToInt(b));
        if ( str != null && str.length() == 1 ) {
            str = "0" + str; 
        }
        return str;
    }
    
    /**
     * 字节转换整形 （参数为负整数时，需要把负整数转化为正整数） 
     * 
     * byte表示范围是：-128 ~ 127 
     * 当参数为负整数时，转换后的十六进制字符串不为"%02x", 而为"%08x"（如：-44不为"90", 而为"ffffff90"）
     * 
     * create by yangwm in Jan 3, 2009 10:56:40 PM
     * @param b 字节
     * @return 正整数 
     */
    public static int byteToInt(byte b) {
        //System.out.println(">>>byteToInt(" + b + ")");
        int n = b;
        if (n < 0) {
            n = 256 + b;
        }
        //System.out.println("<<<n=" + n);
        return n;
    }
    

    /**
     * 
     * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
     * 
     * MD5 ("")                                     = d41d8cd98f00b204e9800998ecf8427e
     * MD5 ("a")                                    = 0cc175b9c0f1b6a831c399e269772661
     * MD5 ("abc")                                  = 900150983cd24fb0d6963f7d28e17f72
     * MD5 ("message digest")                       = f96b697d7cb7938d525a2f31aaf161d0
     * MD5 ("abcdefghijklmnopqrstuvwxyz")           = c3fcd3d76192e4007dfb496cca67e13b
     * 
     * create by yangwm in Jan 3, 2009 8:18:09 PM
     * @param args
     */
    public static void main(String[] args) {
        
        System.out.println( "MD5Encode-------------------------------------" );
        String[] md5s = {
                "d41d8cd98f00b204e9800998ecf8427e",
                "0cc175b9c0f1b6a831c399e269772661",
                "900150983cd24fb0d6963f7d28e17f72",
                "f96b697d7cb7938d525a2f31aaf161d0",
                "c3fcd3d76192e4007dfb496cca67e13b"
            };
        String[] algorithmics = { "Printf", "Simple", "Bit", "Integer" };
        for (String s : algorithmics) {
            algorithmic = s;
            System.out.println( "MD5Encode algorithmic is " + algorithmic);
            System.out.println( MD5Encode("").equals(md5s[0]) );
            System.out.println( MD5Encode("a").equals(md5s[1]) );
            System.out.println( MD5Encode("abc").equals(md5s[2]) );
            System.out.println( MD5Encode("message digest").equals(md5s[3]) );
            System.out.println( MD5Encode("abcdefghijklmnopqrstuvwxyz").equals(md5s[4]) );
        }
        
    }

}
