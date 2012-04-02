/**
 * 
 */
package jdk.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeSet;
import java.util.Vector;

/**
 * 
 * 
 * @author yangwm in Mar 26, 2010 5:39:40 PM
 */
public class CollectionUtils {
    private static final int ARRAYLIST_TYPE=1;
    private static final int LINKEDLIST_TYPE=2;
    private static final int VECTOR_TYPE=3;
    private static final int STACK_TYPE=4;
    private static final int HASHSET_TYPE=5;
    private static final int TREESET_TYPE=6;
    
    public static Collection<String> get(int type,int size){
        switch (type) {
            case ARRAYLIST_TYPE:
                return new ArrayList<String>(size);
            case LINKEDLIST_TYPE:
                return new LinkedList<String>();
            case VECTOR_TYPE:
                return new Vector<String>(size);
            case STACK_TYPE:
                return new Stack<String>();
            case HASHSET_TYPE:
                return new HashSet<String>(size);
            case TREESET_TYPE:
                return new TreeSet<String>();
            default:
                throw new IllegalArgumentException("Unkown type: "+type);
        }
    }
    
    public static boolean isThreadSafe(int type){
        switch (type) {
            case ARRAYLIST_TYPE:
                return false;
            case LINKEDLIST_TYPE:
                return false;
            case VECTOR_TYPE:
                return true;
            case STACK_TYPE:
                return true;
            case HASHSET_TYPE:
                return false;
            case TREESET_TYPE:
                return false;
            default:
                throw new IllegalArgumentException("Unkown type: "+type);
        }
    }
}
