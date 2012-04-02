/**
 * 
 */
package strings;

/**
 * @author yangwm in Mar 22, 2010 12:02:46 AM
 */
public class StringCoding {

    /**
     * create by yangwm in Mar 22, 2010 12:02:46 AM
     * @param args
     */
    public static void main(String[] args) {
        String cnStr = "中文";
        System.out.println(cnStr.length() + ", " + cnStr.getBytes().length);
        String enStr = "en";
        System.out.println(enStr.length() + ", " + enStr.getBytes().length);
    }

}

/*
文件编码为UTF-8：
2, 4
2, 2
文件编码为ISO-8859-1后：
6, 6
2, 2
*/

