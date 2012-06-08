/**
 * 
 */
package algorithm.vector;

/**
 * 
 * @author yangwm Jun 8, 2012 3:30:03 PM
 */
public class OrderedVectorTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        long[] ids = new long[] { 3328897208577712L, 3328890497428282L, 3328900182596614L, 3328890716314454L,
//                3328897222202156L, 3328890825625138L, 3390685864525824L, 3390478246477824L, };
        
        long[] ids = new long[] { 2, 3, 4, 6, 7, 8, 9, 10, 11, 15 };
        OrderedVector vector = new OrderedVector(ids);
        System.out.println(vector);
        
        System.out.println(vector.binarySearch(2L));
        System.out.println(vector.binarySearch(5L));
        System.out.println(vector.binarySearch(10L));
        System.out.println(vector.binarySearch(15L));
        
        System.out.println("------add-------");
        System.out.println(vector);
        vector.add(1L);
        System.out.println(vector);
        vector.add(5L);
        System.out.println(vector);
        vector.add(12L);
        System.out.println(vector);
        vector.add(17L);
        System.out.println(vector);
        vector.add(19L);
        System.out.println(vector);
        vector.add(21L);
        System.out.println(vector);
        
        System.out.println("------delete-------");
        System.out.println(vector);
        vector.delete(21L);
        System.out.println(vector);
        vector.delete(1L);
        System.out.println(vector);
        vector.delete(5L);
        System.out.println(vector);
        vector.delete(5L);
        System.out.println(vector);
        vector.delete(12L);
        System.out.println(vector);
        vector.delete(17L);
        System.out.println(vector);
    }

}
