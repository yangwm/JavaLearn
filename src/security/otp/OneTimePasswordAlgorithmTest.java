/**
 * 
 */
package security.otp;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author yangwm in Mar 1, 2010 9:34:45 AM
 */
public class OneTimePasswordAlgorithmTest {

    /**
     * create by yangwm in Mar 1, 2010 9:34:46 AM
     * @param args
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
        System.out.println(System.currentTimeMillis() + " time top pwd: " + OneTimePasswordAlgorithm.generateOTP("12345678901234567890".getBytes(),System.currentTimeMillis()/10000,8,false,3));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() + " time top pwd: " + OneTimePasswordAlgorithm.generateOTP("12345678901234567890".getBytes(),System.currentTimeMillis()/10000,8,false,3));
        System.out.println();
    }

}
/*
1267407709859 time top pwd: 45554749
1267407716109 time top pwd: 22971039

*/

