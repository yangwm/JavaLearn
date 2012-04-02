package lang;
/**
 * 
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yangwm in Sep 3, 2009 10:36:56 PM
 */
public class ArraysTest {
    
    static class AsListCase {
        public static void main(String[] args) {
            System.out.println(Arrays.asList(2, 4, 8, 16));
            
            Set<String> keys = new HashSet<String>();
            keys.addAll(Arrays.asList(" red ", " white ", " blue "));
            System.out.println(keys);
            System.out.println(keys.toString());
            System.out.println(keys.toArray());
            System.out.println(keys.toArray(new String[]{}));
        }
    }
    
    static class BinarySearchCase {
        public static void main(String[] args) {
            int[] ints = {2, 4, 8, 16, 20, 26, 28, 36, 42, 50};
            
            System.out.println("match...");
            System.out.println(Arrays.binarySearch(ints, 2));
            System.out.println(Arrays.binarySearch(ints, 26));
            System.out.println(Arrays.binarySearch(ints, 50));
            
            System.out.println("no match...");
            System.out.println(Arrays.binarySearch(ints, 0));
            System.out.println(Arrays.binarySearch(ints, 22));
            System.out.println(Arrays.binarySearch(ints, 100));
        }
    }
    
}
