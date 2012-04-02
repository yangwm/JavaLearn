package tedneward.things.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classic and custom algorithms ReverseIterator made simpler
 * 
 * @author yangwm Jul 4, 2010 12:19:35 PM
 */
public class MyCollections {

    public static void main(String[] args) {
        Person ted = new Person("Ted", "Neward", 39,
            new Person("Michael", "Neward", 16),
            new Person("Matthew", "Neward", 10));

        // Make a copy of the List
        List<Person> kids = new ArrayList<Person>(ted.getChildren());
        // Reverse it
        List<Person> results = MyCollections.reverse(kids);
        // Display it
        System.out.println(kids);
        System.out.println(results);
    }

    public static <T> List<T> reverse(List<T> src) {
        List<T> results = new ArrayList<T>(src);
        Collections.reverse(results);
        return results;
    }

}

/*
[[Person: firstName=Michael lastName=Neward age=16], [Person: firstName=Matthew lastName=Neward age=10]]
[[Person: firstName=Matthew lastName=Neward age=10], [Person: firstName=Michael lastName=Neward age=16]]

*/
