/**
 * 
 */
package ids;


/**
 * long的其他进制的长度（二进制为64位） 
 * 
 * @author yangwm Oct 28, 2010 8:43:57 PM
 */
public class LongData {

    /**
     * @param args
     */
    public static void main(String[] args) {
        long userId = 1750715731;
        long m = 16;
        long n = 32;
        System.out.println("(" + userId + " % " + m + ") = " + (userId % m));
        System.out.println("(" + userId + " % " + n + ") = " + (userId % n));
        
        System.out.println("Hex Decimal String length:" + Long.toString(Long.MAX_VALUE, 16).length());
        System.out.println("Ten Decimal String length:" + Long.toString(Long.MAX_VALUE, 10).length());
        System.out.println("Binary Decimal String length:" + Long.toString(Long.MAX_VALUE, 2).length());
        
        long id = 0;
        System.out.println("(Long)id:" + (Long)id);
        Long idObject = id;
        System.out.println("idObject:" + idObject);
        
        long uid = getUid("abc");
        System.out.println("uid:" + uid);
    }
    
    public static Long getUid(String nickname) {
        Long uid = null;
        try{
        } finally {
        }
        return uid;
    }

}
