/**
 * 
 */
package guava.hash;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

/**
 * 
 * 
 * @author yangwm Mar 19, 2012 12:50:31 AM
 */
public class HashSample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        HashFunction hf = Hashing.md5();
        System.out.println(hf.hashString("yangwm"));
        
        HashCode hc = hf.newHasher()
        .putLong(1)
        .putString("yangwm")
        .hash();
        System.out.println(hc);
        
        BloomFilter<Object> bf = BloomFilter.create(BAD_FUNNEL, 10000);
        Object o = new Object();
        System.out.println(bf.mightContain(o));
        bf.put(o);
        System.out.println(bf.mightContain(o));
    }

    static final Funnel<Object> BAD_FUNNEL = null; /*new Funnel<Object>() {
        @Override
        public void funnel(Object object, PrimitiveSink bytePrimitiveSink) {
            bytePrimitiveSink.putInt(object.hashCode());
        }
    };*/
      
}
