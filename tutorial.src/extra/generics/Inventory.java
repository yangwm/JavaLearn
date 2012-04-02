package extra.generics;

import java.util.Collection;

public class Inventory {
    /**
     * Adds a new Assembly to the inventory database.
     * The assembly is given the name name, and consists of a set
     * parts specified by parts. All elements of the collection parts
     * must support the Part interface.
    **/ 
    public static void addAssembly(String name, Collection parts) {
        //...
    }
    public static Assembly getAssembly(String name) {
        //...
        return null;
    }
}
