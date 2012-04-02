/**
 * 
 */
package betwixt.person;

/**
 * @author yangwm in Apr 28, 2009 12:03:17 AM
 */
public class PersonBean {
    
    private String name;
    private int age;
    
    /** Need to allow bean to be created via reflection */
    public PersonBean() {}
    
    public PersonBean(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }   
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public String toString() {
        return "PersonBean[name='" + name + "',age='" + age + "']";
    }
}

