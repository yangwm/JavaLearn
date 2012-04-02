/**
 * 
 */
package concurrent;

import java.util.regex.Pattern;

/**
 * 
 * 
 * @author yangwm Mar 20, 2011 1:49:47 AM
 */
public class StringSplit {
    
    private static final long executeTimes = 100 * 1000;
    
    private static final Pattern COMMA_PATTERN = Pattern.compile(",");

    /**
     * @param args
     */
    public static void main(String[] args) {
        stringSplit("abc, ");
        
//        String str = "";
//        for (int i = 0; i < 100; i++) {
//            str += i + ",";
//        }
//        System.out.println("str:" + str);
//
//        /*
//         * swarm up
//         */
//        for (long i = 0; i < executeTimes; i++) {
//            String ids = str + i;
//            stringSplit(ids);
//        }
//        for (long i = 0; i < executeTimes; i++) {
//            String ids = str + i;
//            patternSplit(ids);
//        }
//        
//        /*
//         * test 
//         */
//        long beginTime = System.nanoTime();
//        for (long i = 0; i < executeTimes; i++) {
//            String ids = str + i;
//            userIds(stringSplit(ids));
//        }
//        long consumeTime = System.nanoTime() - beginTime;
//        System.out.println("stringSplit executeTimes: " + executeTimes + ", summary cosume: " 
//                + (consumeTime / 1000000) + " ms");
//        
//        beginTime = System.nanoTime();
//        for (long i = 0; i < executeTimes; i++) {
//            String ids = str + i;
//            userIds(patternSplit(ids));
//        }
//        consumeTime = System.nanoTime() - beginTime;
//        System.out.println("patternSplit executeTimes: " + executeTimes + ", summary cosume: " 
//                + (consumeTime / 1000000) + " ms");
    }
    
    public static String[] stringSplit(String ids) {
        String[] idsStr =  ids.split(","); //Pattern.compile(",").split(ids, 0);
        System.out.println(idsStr.length);
        return idsStr;
    }
    
    public static String[] patternSplit(String ids) {
        String[] idsStr = COMMA_PATTERN.split(ids, 0);
        return idsStr;
    }
    
    private static int userIds(String[] idsStr) {
        //System.out.println(Arrays.toString(idsStr));
        return idsStr.length;
    }

}

/*
stringSplit executeTimes: 100000, summary cosume: 700 ms
patternSplit executeTimes: 100000, summary cosume: 1864 ms

*/

