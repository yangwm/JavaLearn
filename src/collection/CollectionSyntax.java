/**
 * 
 */
package collection;

import java.util.HashSet;
import java.util.Set;

/**
 * Collection Syntax 
 * 
 * @author yangwm Oct 22, 2010 10:22:46 AM
 */
public class CollectionSyntax {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * Anonymous Inner Class
         */
        Set<String> set = new HashSet<String>() {
            private static final long serialVersionUID = 71155171568468928L;

            {
                add("one");
                add("two");
            }
        };
        System.out.println(set);   
    }

}
