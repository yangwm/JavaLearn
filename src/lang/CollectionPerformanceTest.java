package lang;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */

/**
 * @author yangwm in Jun 13, 2009 8:58:59 PM
 */
public class CollectionPerformanceTest {
    
    private static int totalSize = 10000000;
    private static int loopSize = 100000;
    
    static class ArrayListUseSpaceTester {
        /**
         * create by yangwm in Jun 13, 2009 8:59:00 PM
         * @param args
         */
        public static void main(String[] args) {
            List<String> testList = new ArrayList<String>();
            System.out.println("ArrayListUseSpaceTester");
            for (int i = 0; i < totalSize; i++) {
                String str = "[" + i + "]";
                if (i % loopSize == 0) {
                    System.out.println(str);
                }
                testList.add(str);
            }
        }
    }
    
    static class LinkedListUseSpaceTester {
        /**
         * create by yangwm in Jun 13, 2009 8:59:00 PM
         * @param args
         */
        public static void main(String[] args) {
            List<String> testList = new LinkedList<String>();
            System.out.println("LinkedListUseSpaceTester");
            for (int i = 0; i < totalSize; i++) {
                String str = "[" + i + "]";
                if (i % loopSize == 0) {
                    System.out.println(str);
                }
                testList.add(str);
            }
        }
    }
}
/*

1. ------------- String str = "ListUseSpaceTester[" + i + "]"; ----------------

ArrayListUseSpaceTester
ListUseSpaceTester[0]
ListUseSpaceTester[100000]
ListUseSpaceTester[200000]
ListUseSpaceTester[300000]
ListUseSpaceTester[400000]
ListUseSpaceTester[500000]
ListUseSpaceTester[600000]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at java.util.Arrays.copyOfRange(Arrays.java:3209)
    at java.lang.String.<init>(String.java:216)
    at java.lang.StringBuilder.toString(StringBuilder.java:430)
    at CollectionPerformaceTest$ArrayListUseSpaceTester.main(CollectionPerformaceTest.java:22)

LinkedListUseSpaceTester
ListUseSpaceTester[0]
ListUseSpaceTester[100000]
ListUseSpaceTester[200000]
ListUseSpaceTester[300000]
ListUseSpaceTester[400000]
ListUseSpaceTester[500000]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at java.util.Arrays.copyOfRange(Arrays.java:3209)
    at java.lang.String.<init>(String.java:216)
    at java.lang.StringBuilder.toString(StringBuilder.java:430)
    at CollectionPerformaceTest$LinkedListUseSpaceTester.main(CollectionPerformaceTest.java:41)

小结： 
ArrayListUseSpaceTester 常常会达到ListUseSpaceTester[600000]后等很久才OutOfMemoryError或不出现OutOfMemoryError而是操作系统崩溃。
原因猜想： ListUseSpaceTester[600000]是25个字符，难道与这有关？？？ 


2. ------------- String str = "ListListUseSpaceTester[" + i + "]"; ----------------

ArrayListUseSpaceTester
ListListUseSpaceTester[0]
ListListUseSpaceTester[100000]
ListListUseSpaceTester[200000]
ListListUseSpaceTester[300000]
ListListUseSpaceTester[400000]
ListListUseSpaceTester[500000]
ListListUseSpaceTester[600000]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at java.util.Arrays.copyOfRange(Arrays.java:3209)
    at java.lang.String.<init>(String.java:216)
    at java.lang.StringBuilder.toString(StringBuilder.java:430)
    at CollectionPerformaceTest$ArrayListUseSpaceTester.main(CollectionPerformaceTest.java:23)

LinkedListUseSpaceTester
ListListUseSpaceTester[0]
ListListUseSpaceTester[100000]
ListListUseSpaceTester[200000]
ListListUseSpaceTester[300000]
ListListUseSpaceTester[400000]
ListListUseSpaceTester[500000]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at java.util.Arrays.copyOfRange(Arrays.java:3209)
    at java.lang.String.<init>(String.java:216)
    at java.lang.StringBuilder.toString(StringBuilder.java:430)
    at CollectionPerformaceTest$LinkedListUseSpaceTester.main(CollectionPerformaceTest.java:41)
    
小结： 


3. ------------- String str = "[" + i + "]"; ----------------

ArrayListUseSpaceTester
[0]
[100000]
[200000]
[300000]
[400000]
[500000]
[600000]
[700000]
[800000]
[900000]
[1000000]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at java.util.Arrays.copyOf(Arrays.java:2760)
    at java.util.Arrays.copyOf(Arrays.java:2734)
    at java.util.ArrayList.ensureCapacity(ArrayList.java:167)
    at java.util.ArrayList.add(ArrayList.java:351)
    at CollectionPerformaceTest$ArrayListUseSpaceTester.main(CollectionPerformaceTest.java:27)

LinkedListUseSpaceTester
[0]
[100000]
[200000]
[300000]
[400000]
[500000]
[600000]
[700000]
[800000]
Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
    at java.lang.StringBuilder.toString(StringBuilder.java:430)
    at CollectionPerformaceTest$LinkedListUseSpaceTester.main(CollectionPerformaceTest.java:41)

小结： 


*/
