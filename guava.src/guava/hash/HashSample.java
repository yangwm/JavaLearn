/**
 * 
 */
package guava.hash;

import com.google.common.hash.BloomFilter;
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
    }

}
