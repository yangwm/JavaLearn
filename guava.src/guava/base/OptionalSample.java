/**
 * 
 */
package guava.base;

import com.google.common.base.Optional;

/**
 * 
 * 
 * @author yangwm Mar 18, 2012 6:41:46 PM
 */
public class OptionalSample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Optional<Integer> possible = Optional.of(5);
        possible.isPresent(); // returns true
        Integer value = possible.get(); // returns 5
        System.out.println(value);
    }

}
