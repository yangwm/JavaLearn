/**
 * 
 */
package lang.math;

import org.apache.commons.lang.math.RandomUtils;

/**
 * @author yangwm in Sep 23, 2009 3:54:11 PM
 */
public class RandomUtilsUsage {

    /**
     * create by yangwm in Sep 23, 2009 3:54:11 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(RandomUtils.nextInt());
        System.out.println(RandomUtils.nextInt(10));
        System.out.println(RandomUtils.nextLong());
        System.out.println(RandomUtils.nextBoolean());
        System.out.println(RandomUtils.nextFloat());
        System.out.println(RandomUtils.nextDouble());
    }

}

/*
1109991605
4
5568490833555061760
true
0.08867421
0.5265682289788781
*/
