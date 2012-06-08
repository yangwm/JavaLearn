/**
 * 
 */
package algorithm.vector;

/**
 * 
 * @author yangwm Jun 8, 2012 3:30:03 PM
 */
public class OrderedVectorObjectTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
//        Long[] ids = new Long[] { 3328897208577712L, 3328890497428282L, 3328900182596614L, 3328890716314454L,
//                3328897222202156L, 3328890825625138L, 3390685864525824L, 3390478246477824L, };
        
        Long[] ids = new Long[] { 2L, 3L, 4L, 6L, 7L, 8L, 9L, 10L, 11L, 15L };
        OrderedVectorObject vector = new OrderedVectorObject(ids);
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
