package lang;
import java.util.BitSet;

/**
 * 
 * @author yangwm in Dec 23, 2008 2:12:12 PM
 */
public class TestBitSet {
    protected static final BitSet WWW_FORM_URL;

    static {
        WWW_FORM_URL = new BitSet(256);
        for (int i = 97; i <= 122; i++)
            WWW_FORM_URL.set(i);

        for (int i = 65; i <= 90; i++)
            WWW_FORM_URL.set(i);

        for (int i = 48; i <= 57; i++)
            WWW_FORM_URL.set(i);

        WWW_FORM_URL.set(45);
        WWW_FORM_URL.set(95);
        WWW_FORM_URL.set(46);
        WWW_FORM_URL.set(42);
        WWW_FORM_URL.set(32);
    }
    
    /**
     * 
     * create by yangwm in Dec 23, 2008 2:13:04 PM
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(WWW_FORM_URL);
    }
}
