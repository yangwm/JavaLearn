/**
 * 
 */
package tedneward.things.collection;

import java.util.Arrays;
import java.util.List;

/**
 * Collections trump arrays
 * 
 * @author yangwm Jul 4, 2010 12:06:59 PM
 */
public class ArrayToList {

    public static void main(String[] args) {
        // This gives us nothing good
        System.out.println(args);
        
        // Convert args to a List of String
        List<String> argList = Arrays.asList(args);
        
        // Arrays$ArrayList is unmodifiable collection 
        System.out.println(argList.getClass()); 
        
        // Print them out
        System.out.println(argList);

    }

}

/*
java tedneward.things.collection.ArrayToList yangwm test it
[Ljava.lang.String;@18a992f
[yangwm, test, it]

*/

