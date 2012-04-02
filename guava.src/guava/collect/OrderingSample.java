/**
 * 
 */
package guava.collect;

import java.util.Arrays;
import java.util.Collections;

import org.apache.commons.collections.ComparatorUtils;
import org.junit.Test;

import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

/**
 * 
 * 
 * @author yangwm Mar 18, 2012 7:12:59 PM
 */
public class OrderingSample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Ordering<String> byLengthOrdering = new Ordering<String>() {
            public int compare(String left, String right) {
                return Ints.compare(left.length(), right.length());
            }
        };
        String[] strs = {"abc", "1", "123"};
        //Ordering.from(byLengthOrdering).immutableSortedCopy(strs);
        Arrays.sort(strs, byLengthOrdering);
        System.out.println(Arrays.toString(strs));
        
        Long[] inputArray1 = new Long[] { (long) 2, (long) 2, (long)4, (long)6, (long)8, (long)15 };
        Ordering<Object> arbitrary = Ordering.arbitrary(); //Ordering.natural().lexicographical();
        Arrays.sort(inputArray1, arbitrary);
        System.out.println(Arrays.toString(inputArray1));
        
        //Ordering.natural().onResultOf(function)
        //Comparator<Integer> c = Ordering.explicit(2, 8, 6, 1, 7, 5, 3, 4, 0, 9);
        //if (Ordering.from(comparator).reverse().isOrdered(list)) { ... }
    }

    @Test
    public void testArraySort() {
        Long[] inputArray1 = new Long[] { (long) 2, (long) 2, (long)4, (long)6, (long)8, (long)15 };
        // ComparatorUtils.reversedComparator(ComparatorUtils.naturalComparator())) 
        Arrays.sort(inputArray1, ComparatorUtils.reversedComparator(null));
        System.out.println(Arrays.toString(inputArray1));
        
        //Collections.sort(list, c)
        //CollectionUtils.s
    }

}
