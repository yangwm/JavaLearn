package extra.generics;

import java.util.HashMap;
import java.util.Map;

public class Census {
    public static void addRegistry(Map<String, ? extends Person> registry) {
        //...
    }
    
    public static void main(String... args) {
        Map<String, Driver> allDrivers = new HashMap<String, Driver>() ;
        Census.addRegistry(allDrivers);
    }
}

class Person {
    
}

class Driver extends Person {
    
}
