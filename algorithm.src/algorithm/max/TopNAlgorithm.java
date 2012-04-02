package algorithm.max;

import java.util.Arrays;

import algorithm.ArrayUtil;

/**
 * top n algorithm
 * 
 * 算法来自一道面试题注记 - 庄周梦蝶 - BlogJava
 * http://www.blogjava.net/killme2008/archive/2010/10/28/336357.html
 * 
 * @author yangwm Oct 31, 2010 6:39:46 PM
 */
public class TopNAlgorithm {
    
    /**
     * 求数组的前n个元素, 且从小到大排序 
     * 
     * @param anyOldOrderValues
     * @param n
     * @return
     */
    public static long[] findTopNOrder(long[] input, int n) {
        long[] result = TopNAlgorithm.findTopNValues(input, n);
        
        Arrays.sort(result);
        ArrayUtil.reverse(result);
        return result;
    }
    
    /**
     * 求数组的前n个元素
     * 
     * @param anyOldOrderValues
     * @param n
     * @return
     */
    public static long[] findTopNValues(long[] anyOldOrderValues, int n) {
        long[] result = new long[n];
        findTopNValues(anyOldOrderValues, 0, anyOldOrderValues.length - 1, n,
                n, result);

        return result;
    }

    private static final void findTopNValues(long[] a, int p, int r, int i,
            int n, long[] result) {
        // 全部取到，直接返回
        if (i == 0)
            return;
        // 只剩一个元素，拷贝到目标数组
        if (p == r) {
            System.arraycopy(a, p, result, n - i, i);
            return;
        }
        int len = r - p + 1;
        if (i > len || i < 0)
            throw new IllegalArgumentException();
        // if (len < 7) {
        // Arrays.sort(a, p, r+1);
        // System.arraycopy(a, r - i+1 , result, n - i, i);
        // return;
        // }

        // 划分
        int q = medPartition(a, p, r);
        // 计算右子段长度
        int k = r - q + 1;
        // 右子段长度恰好等于i
        if (i == k) {
            // 拷贝右子段到结果数组，返回
            System.arraycopy(a, q, result, n - i, i);
            return;
        } else if (k > i) {
            // 右子段比i长，递归到右子段求前i个元素
            findTopNValues(a, q + 1, r, i, n, result);
        } else {
            // 右子段比i短，拷贝右子段到结果数组，递归左子段求前i-k个元素
            System.arraycopy(a, q, result, n - i, k);
            findTopNValues(a, p, q - 1, i - k, n, result);
        }
    }

    private static int medPartition(long[] x, int p, int r) {
        int len = r - p + 1;
        int m = p + (len >> 1);
        if (len > 7) {
            int l = p;
            int n = r;
            if (len > 40) { // Big arrays, pseudomedian of 9
                int s = len / 8;
                l = med3(x, l, l + s, l + 2 * s);
                m = med3(x, m - s, m, m + s);
                n = med3(x, n - 2 * s, n - s, n);
            }
            m = med3(x, l, m, n); // Mid-size, med of 3
        }
        if (m != r) {
            long temp = x[m];
            x[m] = x[r];
            x[r] = temp;
        }
        return partition(x, p, r);
    }

    private static int med3(long[] x, int a, int b, int c) {
        return x[a] < x[b] ? (x[b] < x[c] ? b : x[a] < x[c] ? c : a)
                : x[b] > x[c] ? b : x[a] > x[c] ? c : a;
    }

    private static void swap(long[] a, int i, int j) {
        long temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int partition(long a[], int p, int r) {
        long x = a[r];
        int m = p - 1;
        int j = r;
        while (true) {
            do {
                j--;
            } while (j>=p&&a[j] > x);
            do {
                m++;
            } while (a[m] < x);
            
            if (j < m)
                break;
            swap(a, m, j);
        }
        swap(a, r, j + 1);
        return j + 1;
    }
    /**
     * @param args
     */
    public static void main(String[] args) {
        long[] input = new long[] { 2, 7, 6, 8, 9, 18, 11, 5, 3, 20 };
        int n = 3;
        System.out.println("------TopNAlgorithm Array findTopNValues n-------");

        long[] result = findTopNValues(input, n);
        System.out.println(Arrays.toString(result));
    }

}

/*
------TopNAlgorithm Array findTopNValues n-------
[18, 20, 11]

*/
