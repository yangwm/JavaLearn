/**
 * 
 */
package util;

import java.util.Arrays;

/**
 * base62 util 
 * 
 * @author yangwm Apr 3, 2012 10:22:57 PM
 */
public class Base62Util {

	private static final int BASE = 62;
	private static final char[] SEED_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final int SEED_INTS[] = new int[128]; // ascii code 
    static {
        Arrays.fill(SEED_INTS, -1);
        for (int i = 0; i < SEED_CHARS.length; i++) {
            char c = SEED_CHARS[i];
            SEED_INTS[c] = i;
        }
    }

	public static String encode(long id) {
		StringBuilder sb = new StringBuilder();
		long val = id;
		while (val > 0) {
			int i = (int) (val % BASE);
			sb.append(SEED_CHARS[i]);
			
			val = (val - i) / BASE;
		}

		return sb.reverse().toString();
	}

	public static long decode(String str) {
		long val = 0;
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
		    char c = chars[i];
		    int pos = SEED_INTS[c]; // <==> pos = SEED_STR.indexOf(c);
			if (pos < 0) {
				throw new RuntimeException("decode " + str + " err, c is " + c);
			}
			
			val = (val * BASE) + pos; // <==> val = val + (pos * (long)Math.pow(62, chars.length - i - 1));
		}
		return val;
	}

	/**
	 * @param args
	 * @throws SinaurlException
	 */
	public static void main(final String[] args) {
	    System.out.println(encode(16203434962L));
		System.out.println(decode("hGzXDc"));
	}

}
