/**
 * 
 */
package algorithm.top;

import java.util.Arrays;
import java.util.Random;

import org.junit.Test;

import algorithm.ArrayUtil;

/**
 * 
 * 
 * @author yangwm Mar 27, 2012 9:18:46 PM
 */
public class TopNBench {
    
    private static final int vectorSize = 210;
    private static final int friendCount = 2000;
    
    private static Random rand = new Random();
    private static long[] getRandomVector() {
        long[] _values = new long[vectorSize];
        long randValue = rand.nextInt(Integer.MAX_VALUE);
        for (int i = 0; i < vectorSize; i++) {
            _values[i] = randValue + i;
        }
        Arrays.sort(_values);
        ArrayUtil.reverse(_values);
        return _values;
    }
    private static long[][] getAllIds() {
        long[][] allIds = new long[friendCount][];
        for (int i = 0; i < friendCount; i++) {
            long[] values = getRandomVector();
            allIds[i] = values; 
        }
        //System.out.println(allIds.length);
        return allIds;
    }
    private static final long[][] allIds = getAllIds();

    @Test
    public void testTopN() {
        long[][] ids = TopNBench.allIds;
        int times = 10 * 1000;
        int n = 2000;
        
        // swarm up 
        for (int i = 0; i < times;i ++) {
            long[] result = TopN.top(ids, n);
        }
        
        long beginTime = System.nanoTime();
        for (int i = 0; i < times;i ++) {
            long[] result = TopN.top(ids, n);
        }
        long cosumeTime = System.nanoTime() - beginTime;
        System.out.println("testTopN " + TopN.class.getSimpleName() + ", cosume time " + (cosumeTime/1000000.0)
                + " ms, top'n " + n + " from vectorSize " + vectorSize + ", friendCount " + friendCount 
                + ", times " + times + " per time " + (cosumeTime/times/1000000.0)+ " ms." );
        //System.out.println("result length:" + result.length + ", result" + Arrays.toString(result));
    }

}

/*
testTopN TopN, cosume time 4851.307342 ms, top'n 45 from vectorSize 210, friendCount 2000, times 10000 per time 0.48513 ms.
testTopN TopN, cosume time 6126.567053 ms, top'n 500 from vectorSize 210, friendCount 2000, times 10000 per time 0.612656 ms.
testTopN TopN, cosume time 7908.936487 ms, top'n 2000 from vectorSize 210, friendCount 2000, times 10000 per time 0.790893 ms.


testTopN TopNHeap, cosume time 3364.160854 ms, top'n 45 from vectorSize 210, friendCount 2000, times 10000 per time 0.336416 ms.
testTopN TopNHeap, cosume time 21620.403206 ms, top'n 500 from vectorSize 210, friendCount 2000, times 10000 per time 2.16204 ms.
testTopN TopNHeap, cosume time 45305.996404 ms, top'n 2000 from vectorSize 210, friendCount 2000, times 10000 per time 4.530599 ms.


testTopN TopNOld, cosume time 668749.768094 ms, top'n 45 from vectorSize 210, friendCount 2000, times 10000 per time 66.874976 ms.
testTopN TopNOld, cosume time 677682.549496 ms, top'n 500 from vectorSize 210, friendCount 2000, times 10000 per time 67.768254 ms.
testTopN TopNOld, cosume time 671494.445174 ms, top'n 2000 from vectorSize 210, friendCount 2000, times 10000 per time 67.149444 ms.




testTopN TopNArray, cosume time 4654.084864 ms, top'n 45 from vectorSize 210, friendCount 2000, times 10000 per time 0.465408 ms.

*/

