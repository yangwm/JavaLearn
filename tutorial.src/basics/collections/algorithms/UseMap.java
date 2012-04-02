/**
 * 
 */
package basics.collections.algorithms;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yangwm in Dec 23, 2009 9:46:00 AM
 */
public class UseMap {

    /**
     * create by yangwm in Dec 23, 2009 9:46:00 AM
     * @param args
     */
    public static void main(String[] args) {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put(null, null);
        System.out.println(hashMap.get(null));
        for (int i = 0; i < 5; i++) {
            hashMap.put(String.valueOf(i), String.valueOf(i));
            System.out.println(hashMap.get(String.valueOf(i)));
        }
        
        Map<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
        concurrentHashMap.put(null, null);
        System.out.println(concurrentHashMap.get(null));
        for (int i = 0; i < 5; i++) {
            concurrentHashMap.put(String.valueOf(i), String.valueOf(i));
            System.out.println(concurrentHashMap.get(String.valueOf(i)));
        }
    }

}

/*
null
0
1
2
3
4
Exception in thread "main" java.lang.NullPointerException
    at java.util.concurrent.ConcurrentHashMap.put(ConcurrentHashMap.java:881)
    at basics.collections.algorithms.UseMap.main(UseMap.java:29)

null
0
1
2
3
4
Exception in thread "main" java.lang.NullPointerException
    at java.util.concurrent.ConcurrentHashMap.get(ConcurrentHashMap.java:768)
    at basics.collections.algorithms.UseMap.main(UseMap.java:30)

*/
