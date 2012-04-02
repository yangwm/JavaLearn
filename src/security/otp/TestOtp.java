package security.otp;

import java.lang.reflect.UndeclaredThrowableException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HOTP: An HMAC-Based One-Time Password Algorithm
 * 
 * A one-time dynamic password authentication algorithm OPT. 
 * According to dynamic support for TOTP and HOTP authentication.
 * 
 * @author yangwm in Jan 17, 2010 12:09:42 AM
 */
public class TestOtp {
     private static final int[] DIGITS_POWER
         = {1,10,100,1000,10000,100000,1000000,10000000,100000000};
    
    
    public static byte[] hmac_sha1(byte[] keyBytes, byte[] text) //用于提取摘要，生成一个HMAC-SHA-1的值
        throws NoSuchAlgorithmException, InvalidKeyException {
        try {
            Mac hmacSha1;
            try {
                hmacSha1 = Mac.getInstance("HmacSHA1");
            } catch (NoSuchAlgorithmException nsae) {
                hmacSha1 = Mac.getInstance("HMAC-SHA-1");
            }
            SecretKeySpec macKey =
                new SecretKeySpec(keyBytes, "RAW");
            hmacSha1.init(macKey);//hmacSha1.update(input);
            return hmacSha1.doFinal(text);
        } catch (GeneralSecurityException gse) {
            throw new UndeclaredThrowableException(gse);
        }
    }
    
    public static String generateOTP(byte[] secret, 
            long movingFactor, int codeDigits)
    throws NoSuchAlgorithmException, InvalidKeyException {
        StringBuffer result = new StringBuffer("");
        byte[] text = new byte[8];
        for (int i =text.length-1; i >=0; i--) {
            text[i] = (byte) (movingFactor & 0xff );   //保留最后8位
            movingFactor >>= 8;
        }
        byte[] hash = hmac_sha1(secret, text);     //Step 1: Generate an HMAC-SHA-1 value 
        int offset = (hash[hash.length - 1] & 0xf)+3;   //小于20大于3的一个数 
        int binary =
            ((hash[offset] & 0x7f) << 24)
            | ((hash[offset + 1] & 0xff) << 16)
            | ((hash[offset + 2] & 0xff) << 8)
            | (hash[offset + 3] & 0xff);           //Generate a 4-byte string 
        int otp = binary % DIGITS_POWER[codeDigits-1];
        result .append(Integer.toString(otp));
        while (result.length() < codeDigits) {
            result.insert(0, "0");                    //Compute an HOTP value
        }
        return result.toString();
    }


    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
        System.out.println(System.currentTimeMillis() + " time top pwd: " + generateOTP("12345678901234567890".getBytes(),System.currentTimeMillis()/10000,8));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(System.currentTimeMillis() + " time top pwd: " + generateOTP("12345678901234567890".getBytes(),System.currentTimeMillis()/10000,8));
        System.out.println();
    }

}

/*
1267407709859 time top pwd: 45554749
1267407716109 time top pwd: 22971039

1267113862593 time top pwd: 05568488
1267113868875 time top pwd: 05568488
*/

