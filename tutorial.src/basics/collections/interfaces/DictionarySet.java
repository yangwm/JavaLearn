package basics.collections.interfaces;

import java.util.*;

public class DictionarySet {

    public static void main(String[] args) {
        
        SortedSet<String> dictionary = new TreeSet<String> ();
        for (char ch = 'a'; ch <= 'z'; ) {
        	dictionary.add( String.valueOf(ch++) );
        }
        System.out.println(dictionary);
        
        /*int count = dictionary.subSet("f", "m").size();
        System.out.println(count);*/
        
        /*System.out.println(dictionary);
        dictionary.subSet("f", "g").clear();
        System.out.println(dictionary);*/
        
        /*for (char ch = 'a'; ch <= 'z'; ) {
            String from = String.valueOf(ch++);
            String to = String.valueOf(ch);
            System.out.println(from + ": " +
                dictionary.subSet(from, to).size());
        }*/
        
        System.out.println(dictionary.headSet("f"));
        
        /*String predecessor = dictionary.headSet("f").last();
        System.out.println(predecessor);*/
    
    }

}