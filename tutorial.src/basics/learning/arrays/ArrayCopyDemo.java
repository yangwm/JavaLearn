package basics.learning.arrays;

public class ArrayCopyDemo {
    public static void main(String[] args) {
        char[] copyFrom = { 'd', 'e', 'c', 'a', 'f', 'f', 'e',
                'i', 'n', 'a', 't', 'e', 'd' };
        char[] copyTo = new char[7];

        System.arraycopy(copyFrom, 2, copyTo, 0, 7);
        System.out.println(copyTo);
        System.out.println(new String(copyTo));
        
        String[] s = { "1", "2" };
        System.out.println(s);
        
        int[] i = { 1, 2 };
        System.out.println(i);
        
        char[] c = { '1', '2' };
        System.out.println(c);
    }
}

