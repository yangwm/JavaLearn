package tedneward.things.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 
 * @author yangwm Jul 4, 2010 12:19:40 PM
 */
public class Person implements Iterable<Person> {

    private String firstName;
    private String lastName;
    private int age;
    private List<Person> children = new ArrayList<Person>();
    
    public Person(String fn, String ln, int a, Person... kids) {
        this.firstName = fn;
        this.lastName = ln;
        this.age = a;
        for (Person child : kids) {
            children.add(child);
        }
    }

    public int hashCode() {
        return firstName.hashCode() & lastName.hashCode() & age;
    }

    public String toString() {
        return "[Person: " + "firstName=" + firstName + " " + "lastName=" + lastName + " " + "age=" + age + "]";
    }

    public Iterator<Person> iterator() {
        return children.iterator();
    }

    
    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public int getAge() {
        return this.age;
    }

    public List<Person> getChildren() {
        return children;
    }

    public void setFirstName(String value) {
        this.firstName = value;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    public void setAge(int value) {
        this.age = value;
    }

    public void setChildren(List<Person> children) {
        this.children = children;
    }

}
