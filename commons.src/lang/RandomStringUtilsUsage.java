/**
 * 
 */
package lang;

import org.apache.commons.lang.RandomStringUtils;

/**
 * @author yangwm in Sep 22, 2009 5:35:54 PM
 */
public class RandomStringUtilsUsage {

    /**
     * create by yangwm in Sep 22, 2009 5:35:54 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("random(0):" + RandomStringUtils.random(0));
        System.out.println("random(50):" + RandomStringUtils.random(50));
        System.out.println("randomAscii(50):" + RandomStringUtils.randomAscii(50));
        System.out.println("randomAlphabetic(50):" + RandomStringUtils.randomAlphabetic(50));
        System.out.println("randomAlphanumeric(50):" + RandomStringUtils.randomAlphanumeric(50));
        System.out.println("randomNumeric(50):" + RandomStringUtils.randomNumeric(50));
    }

}
