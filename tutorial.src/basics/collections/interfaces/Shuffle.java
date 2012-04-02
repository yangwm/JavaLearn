package basics.collections.interfaces;

import java.util.*;

public class Shuffle {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        for (String a : args)
            list.add(a);
	System.out.println(list);
        Collections.shuffle(list, new Random());
        System.out.println(list);
    }
}

