/**
 * 
 */
package algorithm.hash;

import java.util.BitSet;

/**
 * bloom filter
 * 
 * 那些优雅的数据结构(1) : BloomFilter——大规模数据处理利器
 * http://www.cnblogs.com/heaad/archive/2011/01/02/1924195.html
 * 
 * @author yangwm Apr 8, 2012 9:36:17 PM
 */
public class BloomFilter {

    /* BitSet初始分配2^24个bit */
    private static final int DEFAULT_SIZE = 1 << 25;
    /* 不同哈希函数的种子，一般应取质数 */
    private static final int[] seeds = new int[] { 5, 7, 11, 13, 31, 37, 61 };
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    /* 哈希函数对象 */
    private HashFunction[] func = new HashFunction[seeds.length];

    public BloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    // 将字符串标记到bits中
    public void add(String value) {
        for (HashFunction f : func) {
            bits.set(f.hash(value), true);
        }
    }

    // 判断字符串是否已经被bits标记
    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (HashFunction f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    /* 哈希函数类 */
    public static class SimpleHash implements HashFunction {
        private int cap;
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        // hash函数，采用简单的加权和hash 
        @Override
        public int hash(Object key) {
            int result = seed * key.hashCode();
            return (cap - 1) & result;
        }
    }
    
}
