/**
 * 
 */
package concurrent;

/**
 * 
 * 
 * @author yangwm Mar 24, 2011 10:27:17 AM
 */
public class SimpleHashBench {

    
    public static int modHashCount = 32;
    public static int andHashCount = modHashCount - 1;
    
    
    private static final long executeTimes = 1000 * 1000 * 1000;
    

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * swarm up
         */
        for (long i = 0; i < executeTimes; i++) {
            int hash = (int)(i % modHashCount);
        }
        for (long i = 0; i < executeTimes; i++) {
            int hash = (int)(i & andHashCount);
        }
        
        /*
         * test 
         */
        long beginTime = System.nanoTime();
        for (long i = 0; i < executeTimes; i++) {
            int hash = (int)(i % modHashCount);
        }
        long consumeTime = System.nanoTime() - beginTime;
        System.out.println("% executeTimes: " + executeTimes + ", summary cosume: " 
                + (consumeTime / 1000000) + " ms");
        
        beginTime = System.nanoTime();
        for (long i = 0; i < executeTimes; i++) {
            int hash = (int)(i & andHashCount);
        }
        consumeTime = System.nanoTime() - beginTime;
        System.out.println("& executeTimes: " + executeTimes + ", summary cosume: " 
                + (consumeTime / 1000000) + " ms");
    }

}

/*
% executeTimes: 100000000, summary cosume: 955 ms
& executeTimes: 100000000, summary cosume: 194 ms

*/

