package lang.builder;

import java.util.Date;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.StandardToStringStyle;


/**
 * 在org.apache.commons.lang.builder包中一共有7个类，用于帮助实现Java对象的一些基础的方法，
 * 如compareTo(), equals(), hashCode(), toString()等。他们分别是：
    
    CompareToBuilder – 用于辅助实现Comparable.compareTo(Object)方法；
    EqualsBuilder – 用于辅助实现Object.equals()方法；
    HashCodeBuilder – 用于辅助实现Object.hashCode()方法；
    ReflectionToStringBuilder – 使用反射机制辅助实现Object.toString()方法；
    ToStringBuilder – 用于辅助实现Object.toString()方法；
    StandardToStringStyle – 辅助ToStringBuilder控制标准格式；
    ToStringStyle – 辅助ToStringBuilder控制输出格式。
 * 
 * 
 * @author yangwm in Feb 18, 2009 11:28:44 PM
 */
public class BuilderTest {

    public static void main(String[] args) {
        Person person1 = new Person("泰岳", 27, new Date());
        Person person2 = new Person("杨武明", 24, new Date());
        Person person3 = new Person("泰岳", 27, new Date());

        System.out.println("person1's info: " + person1);
        System.out.println("person2's info: " + person2);
        System.out.println("person1's hash code: " + person1.hashCode());
        System.out.println("person2's hash code: " + person2.hashCode());
        System.out.println("person1 equals person2? " + person1.equals(person2));
        System.out.println("person1 equals person3? " + person1.equals(person3));
        
        System.out.println("--------------More String Style of Object ------------------------------------");
        System.out.println("person1's info: " + person1.toString2(ToStringStyle.MULTI_LINE_STYLE));
        System.out.println("person1's info: " + person1.toString2(ToStringStyle.NO_FIELD_NAMES_STYLE));
        System.out.println("person1's info: " + person1.toString2(ToStringStyle.SHORT_PREFIX_STYLE));
        System.out.println("person1's info: " + person1.toString2(ToStringStyle.SIMPLE_STYLE));
        System.out.println("person1's info: " + person1.toString2(new StandardToStringStyle()));
        
        System.out.println("--------------Student ------------------------------------");
        Student student1 = new Student("神州", 27, new Date(), 1);
        Student student2 = new Student("神州", 27, new Date(), 1);
        System.out.println("student1's info: " + student1);
        System.out.println("student1's hash code: " + student1.hashCode());
        System.out.println("student1 equals student2? " + student1.equals(student2));
        
    }
}

class Person implements Comparable<Object> {
    private String name;
    private int age;
    private Date dateJoined;

    public Person() {
    };

    public Person(String name, int age, Date dateJoined) {
        this.name = name;
        this.age = age;
        this.dateJoined = dateJoined;
    }

    public int compareTo(Object o) {
        if (!(o instanceof Person)) {
            return -1;
        }
        Person other = (Person) o;
        return new CompareToBuilder().append(name, other.getName()).append(age,
                other.getAge()).append(dateJoined, other.getDateJoined())
                .toComparison();
    }

    public boolean equals(Object o) {
        if (!(o instanceof Person)) {
            return false;
        }
        Person other = (Person) o;
        return new EqualsBuilder().append(name, other.getName()).append(age,
                other.getAge()).append(dateJoined, other.getDateJoined())
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder().append(name).append(age)
                .append(dateJoined).toHashCode();
    }

    public String toString() {
        return new ToStringBuilder(this).append("name", name)
                .append("age", age).append("dateJoined", dateJoined).toString();
    }
    
    public String toString2(ToStringStyle style) {
        ToStringStyle _style = ToStringStyle.DEFAULT_STYLE;
        if (null != style) {
            _style = style;
        }
        return new ToStringBuilder(this, _style).append("name", name)
                .append("age", age).append("dateJoined", dateJoined).toString();
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

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

}

/**
 * 这些builder用起来很简单，只要new一个实例，append需要参与的信息，
 * 然后加上toComparison、isEquals、 toHashCode、toString结尾就可以了。
 * 如果不需要使用append指定信息，则可直接使用反射机制进行自动化实现，如下面的 Student类：
 * 
 * @author yangwm in Feb 18, 2009 11:30:00 PM
 */
class Student extends Person {
    private int grad;
    
    public Student() {super();}
    
    public Student(String name, int age, Date dateJoined, int grad) {
        super(name, age, dateJoined);
        this.grad = grad;
    }
    
    public int compareTo(Object o) {
        return CompareToBuilder.reflectionCompare(this, o);
    }
    
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }
    
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
    
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }

    public int getGrad() {
        return grad;
    }

    public void setGrad(int grad) {
        this.grad = grad;
    }
    
}

/*
person1's info: lang.builder.Person@60aeb0[name=泰岳,age=27,dateJoined=Thu Feb 19 12:03:23 CST 2009]
person2's info: lang.builder.Person@8813f2[name=杨武明,age=24,dateJoined=Thu Feb 19 12:03:23 CST 2009]
person1's hash code: -717489588
person2's hash code: -263868830
person1 equals person2? false
--------------More String Style of Object ------------------------------------
person1's info: lang.builder.Person@60aeb0[
  name=泰岳
  age=27
  dateJoined=Thu Feb 19 12:03:23 CST 2009
]
person1's info: lang.builder.Person@60aeb0[泰岳,27,Thu Feb 19 12:03:23 CST 2009]
person1's info: Person[name=泰岳,age=27,dateJoined=Thu Feb 19 12:03:23 CST 2009]
person1's info: 泰岳,27,Thu Feb 19 12:03:23 CST 2009
person1's info: lang.builder.Person@60aeb0[name=泰岳,age=27,dateJoined=Thu Feb 19 12:03:23 CST 2009]

*/
