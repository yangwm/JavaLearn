/**
 * 
 */
package tedneward.things.collection;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author yangwm Jul 4, 2010 4:11:46 PM
 */
public class MissingHash {
    
    public static void main(String[] args) {
        Person p1 = new Person("Ted", "Neward", 39);
        Person p2 = new Person("Charlotte", "Neward", 38);
        System.out.println(p1.hashCode());
        
        Map<Person, Person> map = new HashMap<Person, Person>();
        map.put(p1, p2);
        
        p1.setLastName("Finkelstein");
        System.out.println(p1.hashCode());
        
        System.out.println(map.get(p1));
    }

}

/*
3
34
null
*/
