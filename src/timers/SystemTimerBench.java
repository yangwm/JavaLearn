/**
 * 
 */
package timers;

/**
 * 
 * 获取系统时间基准测试：must be assignment value, like: long t = getCurrentTime 
 * 
 * System.nanoTime()在linux下的实现是调用clock_gettime。
 * System.currentTimeMillis()在linux下的实现是调用gettimeofday。
 * SystemTimer.currentTimeMillis()直接返回缓存值，独立线程定期更新时间缓存，精度取决于定期间隔。
 * 
 * windows下的System.currentTimeMillis()，本身进行了优化（可能是缓存），所以不准但函数调用很快。
 * 
 * @author yangwm Dec 5, 2010 2:42:02 PM
 */
public class SystemTimerBench {

    /**
     * @param args
     */
    public static void main(String[] args) {
        int times = 1 * 1000 * 1000;

        
        long beginTime = System.nanoTime();
        for (int i = 0; i < times; ++i) {
            long t = System.nanoTime();
        }
        long endTime = System.nanoTime();
        System.out.println(times + " times System.nanoTime consume Time: " + (endTime - beginTime) / 1000000 + " ms");
        
        beginTime = System.nanoTime();
        for (int i = 0; i < times; ++i) {
            long t = System.currentTimeMillis();
        }
        endTime = System.nanoTime();
        System.out.println(times + " times System.currentTimeMillis consume Time: " + (endTime - beginTime) / 1000000 + " ms");
        
        beginTime = System.nanoTime();
        for (int i = 0; i < times; ++i) {
            long t = SystemTimer.currentTimeMillis();
        }
        endTime = System.nanoTime();
        System.out.println(times + " times SystemTimer.currentTimeMillis consume Time: " + (endTime - beginTime) / 1000000 + " ms");
    }

}

/*
windows, times = 10 * 1000 * 1000:
D:\study\tempProjects\yangwmProject\JavaLearn\classes>java timers.SystemTimerBench
10000000 times System.nanoTime consume Time: 14004 ms
10000000 times System.currentTimeMillis consume Time: 215 ms
10000000 times SystemTimer.currentTimeMillis consume Time: 47 ms

windows, times = 1 * 1000 * 1000:
D:\study\tempProjects\yangwmProject\JavaLearn\classes>java timers.SystemTimerBench
1000000 times System.nanoTime consume Time: 1370 ms
1000000 times System.currentTimeMillis consume Time: 20 ms
1000000 times SystemTimer.currentTimeMillis consume Time: 11 ms

windows, times = 1 * 100 * 1000:
D:\study\tempProjects\yangwmProject\JavaLearn\classes>java timers.SystemTimerBench
100000 times System.nanoTime consume Time: 151 ms
100000 times System.currentTimeMillis consume Time: 2 ms
100000 times SystemTimer.currentTimeMillis consume Time: 7 ms


linux, times = 10 * 1000 * 1000:
[root@test notify]# java timers.SystemTimerBench
10000000 times System.nanoTime consume Time: 7432 ms
10000000 times System.currentTimeMillis consume Time: 7056 ms
10000000 times SystemTimer.currentTimeMillis consume Time: 87 ms

linux, times = 1 * 1000 * 1000:
[root@test notify]# java timers.SystemTimerBench
1000000 times System.nanoTime consume Time: 720 ms
1000000 times System.currentTimeMillis consume Time: 693 ms
1000000 times SystemTimer.currentTimeMillis consume Time: 25 ms

linux, times = 1 * 100 * 1000:
[root@test notify]# java timers.SystemTimerBench
100000 times System.nanoTime consume Time: 74 ms
100000 times System.currentTimeMillis consume Time: 69 ms
100000 times SystemTimer.currentTimeMillis consume Time: 19 ms


*/
