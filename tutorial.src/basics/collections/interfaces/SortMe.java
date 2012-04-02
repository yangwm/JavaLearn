package basics.collections.interfaces;

import java.util.*;

// TreeSort elements must be instances of a class that implements Comparable. 
// StringBuffer does not.
public class SortMe {
    public static void main(String args[]) {
        SortedSet<StringBuffer> s = new TreeSet<StringBuffer>();
        s.add(new StringBuffer("Red"));
        s.add(new StringBuffer("White"));
        s.add(new StringBuffer("Blue"));
        System.out.println(s.first());
    }
}
/*
Exception in thread "main" java.lang.ClassCastException: java.lang.StringBuffer
cannot be cast to java.lang.Comparable
        at java.util.TreeMap.put(TreeMap.java:542)
        at java.util.TreeSet.add(TreeSet.java:238)
        at SortMe.main(SortMe.java:8)
*///~
