/**
 * 
 */
package tedneward.things.collection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * TreeSet, which requires objects to either implement Comparable  directly or have a Comparator 
 * passed in at the time of construction, doesn't use equals() to compare the objects
 * 
 * So, objects stored in a Set will have two potential means of determining equality: the expected equals() method 
 * and the Comparable/Comparator method, depending on the context of who is asking.
 * 
 * @author yangwm Jul 4, 2010 4:16:02 PM
 */
public class UsingSortedSet {
    
    public static void main(String[] args) {
        List<Person> persons = Arrays.asList(
            new Person("Ted", "Neward", 39),
            new Person("Ron", "Reynolds", 39),
            new Person("Charlotte", "Neward", 38),
            new Person("Matthew", "McCullough", 18)
        );
        SortedSet<Person> ss = new TreeSet<Person>(new Comparator<Person>() {
            public int compare(Person lhs, Person rhs) {
                //int lastCmp = lhs.getFirstName().compareTo(rhs.getFirstName());
                //return lastCmp != 0 ? lastCmp : lhs.getLastName().compareTo(rhs.getLastName());
                return lhs.getLastName().compareTo(rhs.getLastName());
            }
        });
        ss.addAll(persons);
        System.out.println(ss);
    }

}

/*
ERROR: JDWP Unable to get JNI 1.2 environment, jvm->GetEnv() return code = -2
JDWP exit error AGENT_ERROR_NO_JNI_ENV(183):  [../../../src/share/back/util.c:820]
*/
/*
return lhs.getLastName().compareTo(rhs.getLastName());  : 
[[Person: firstName=Matthew lastName=McCullough age=18], [Person: firstName=Ted lastName=Neward age=39], [Person: firstName=Ron lastName=Reynolds age=39]]

*/
/*
int lastCmp = lhs.getFirstName().compareTo(rhs.getFirstName());
return lastCmp != 0 ? lastCmp : lhs.getLastName().compareTo(rhs.getLastName()); : 
[[Person: firstName=Charlotte lastName=Neward age=38], [Person: firstName=Matthew lastName=McCullough age=18], [Person: firstName=Ron lastName=Reynolds age=39], [Person: firstName=Ted lastName=Neward age=39]]

*/
