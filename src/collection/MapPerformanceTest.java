/**
 * 
 */
package collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * list performance test (single operation vs batch operation)
 * 
 * @author yangwm Oct 11, 2010 11:06:40 AM
 */
public class MapPerformanceTest {
    
    
    private static final long executeTimes = 10 * 100 * 1000;
    private static int threadCount = 100;
    
    private static Map<String, Message> map = new HashMap<String, Message>();
    
    private static CountDownLatch startLatch = new CountDownLatch(1);
    private static CountDownLatch endLatch = new CountDownLatch(threadCount);


    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * init data and warm up
         */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append("abcdefg");
        }
        for (int i = 0; i < 100; i++) {
            Message message = new Message();
            message.setId(i);
            message.setGlobalId(i);
            message.setContent(sb.toString());
            map.put(i + "", message);
        }
        //map = Collections.unmodifiableMap(map);
        for (int i = 1; i < executeTimes; i++) {
            map.get(i + "");
        }
        
        /*
         * map get 
         */
        long begin = System.nanoTime();
        for (int i = 1; i < executeTimes; i++) {
            map.get(i + "");
        }
        long end = System.nanoTime();
        System.out.println("get consume " + (end - begin) / 1000000 + " ms ");
    }

}
/*
get consume 254 ms 
 
*/
