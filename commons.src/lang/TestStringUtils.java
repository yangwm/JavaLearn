package lang;

import org.apache.commons.lang.StringUtils;

//set classpath=.;D:\Program Files\Java\jdk1.5.0_16\lib;D:\study\tempProject\JavaLearn\lib\commons-lang-2.4.jar
public class TestStringUtils {
	/**
     * separator is "ou=" 
     * 
     * create by yangwm in Mar 7, 2009 1:44:53 PM
     * @param str  the String to get a substring from, may be null
     * @return the substring after the first occurrence of the separator,
     */
    public static String substringAfterSection(String str) {
        if (null != str) {
            str = str.toLowerCase();
        }
        return StringUtils.substringAfter(str, "ou=");
    }
    
    /**
     * separator is ",ou=" 
     * 
     * create by yangwm in Mar 7, 2009 1:44:53 PM
     * @param str  the String to get a substring from, may be null
     * @return the substring before the first occurrence of the separator,
     */
    public static String substringBeforeSection(String str) {
        if (null != str) {
            str = str.toLowerCase();
			String tempStr = str;

			str = StringUtils.substringBefore(str, ",ou=");
			System.out.println(str);
			System.out.println(tempStr);
			if (tempStr.equals(str)) {
				str = StringUtils.substringBefore(str, ", ou=");
			}
        }
        return str;
    }


    public static void main(String[] args) {
        String str1 = "";
        String str2 = ".";
        boolean isContain = StringUtils.contains(str1, str2);
        System.out.println(str1 + " isContain " + str2 + " = " + isContain);
        
        String str = "a4-bid=1,a4-bid=343,ou=bj";
        System.out.println(StringUtils.substringAfter(str, "ou="));
        
        str = "a4-bid=1,a4-bid=343";
        System.out.println(StringUtils.substringAfter(str, "ou="));
        
        str = "a4-bid=1,a4-bid=343,ou=bj";
        System.out.println(StringUtils.substringBefore(str, ",ou="));

		str = "a4-bid=1,a4-bid=343,OU=bj";
		System.out.println(substringBeforeSection(str));

		str = "a4-bid=1,a4-bid=343, oU=bj";
		System.out.println(substringBeforeSection(str));

		System.out.println(Boolean.parseBoolean("true"));
		System.out.println(Boolean.parseBoolean("true34"));
		System.out.println(Boolean.parseBoolean(null));
		System.out.println(Boolean.parseBoolean(""));
		
		System.out.println(Integer.parseInt("8"));
		int length = 0;
        if (StringUtils.isNumeric("m")) {
            Integer.parseInt("m");
        }
        System.out.println(length);
        
        
        System.out.println("12345678".substring(1, 3));
        
        System.out.println("---------leftPad-----------");
        System.out.println(StringUtils.leftPad("abc", 10, "0"));
        System.out.println(StringUtils.leftPad("abc", 10, "01"));
        System.out.println(StringUtils.leftPad("abc", 10, "012"));
    }
    
    
}
