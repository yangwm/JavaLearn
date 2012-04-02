package basics.collections.algorithms;

import java.util.AbstractList;
import java.util.List;

public class UseMyArrayList {
    public static <T> List<T> asList(T[] a) {
        return new MyArrayList<T>(a);
    }
    
    private static class MyArrayList<T> extends AbstractList<T> {

        private final T[] a;

        MyArrayList(T[] array) {
            a = array;
        }

        public T get(int index) {
            return a[index];
        }

        public T set(int index, T element) {
            T oldValue = a[index];
            a[index] = element;
            return oldValue;
        }

        public int size() {
            return a.length;
        }
    }
    
    public static void main(String[] args) {
        String[] strs = {"yangwm", "diudiu"};
        System.out.println(UseMyArrayList.asList(strs));
    }
}
