/**
 * 
 */
package tedneward.things.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classic and custom algorithms ReverseIterator 
 * 
 * @author yangwm Jul 4, 2010 12:14:52 PM
 */
public class ReverseIterator {

    public static void main(String[] args) {
        Person ted = new Person("Ted", "Neward", 39,
            new Person("Michael", "Neward", 16),
            new Person("Matthew", "Neward", 10));

        // Make a copy of the List
        List<Person> kids = new ArrayList<Person>(ted.getChildren());
        // Reverse it
        Collections.reverse(kids);
        // Display it
        System.out.println(kids);
    }

}

/*
[[Person: firstName=Matthew lastName=Neward age=10], [Person: firstName=Michael lastName=Neward age=16]]

*/
