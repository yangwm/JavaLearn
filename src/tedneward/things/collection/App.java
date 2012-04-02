/**
 * 
 */
package tedneward.things.collection;

/**
 * For loop through any Iterable
 * 
 * @author yangwm Jul 4, 2010 12:13:04 PM
 */
public class App {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Person ted = new Person("Ted", "Neward", 39,
            new Person("Michael", "Neward", 16),
            new Person("Matthew", "Neward", 10));

        // Iterate over the kids
        for (Person kid : ted)
        {
            System.out.println(kid.getFirstName());
        }
    }

}

/*
Michael
Matthew

*/

