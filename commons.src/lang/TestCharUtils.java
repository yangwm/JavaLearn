package lang;

import org.apache.commons.lang.CharUtils;

public class TestCharUtils {

    /**
     * create by yangwm in Mar 16, 2009 11:04:28 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("CharUtils.unicodeEscaped('.')=" + CharUtils.unicodeEscaped('.'));
        System.out.println("CharUtils.unicodeEscaped('a')=" + CharUtils.unicodeEscaped('a'));
        System.out.println("CharUtils.unicodeEscaped('武')=" + CharUtils.unicodeEscaped('武'));
        
        System.out.println("CharUtils.toString('c')=" + CharUtils.toString('c'));
    }

}
