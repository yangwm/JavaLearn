package lang;

/**
 * 在多国语言版JDK，现用的jdk1.5.0_16和jdk1.6.0_10中，
 * UTF-8编码下对一些中文的支持（如：“开始”）有字符集警告和乱码问题（在cmd下），但在eclipse下没问题；
 * UTF-16编码下对一些字符的支持（如：“开始”）有字符集警告问题（在cmd下），但在eclipse下没问题；
 * GBK编码下对一些中文的支持没问题。
 * 解决方法： javac -encoding UTF-8 CharEncodeInUnicodeTest.java 
 * 
 * @author yangwm   11:34:57 AM
 */
public class CharEncodeInUnicodeTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 开始 
        System.out.println("开始");
    }

}
