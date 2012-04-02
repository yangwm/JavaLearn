/**
 * 
 */
package junitstudy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

/**
 * @author yangwm in Apr 8, 2010 6:25:23 PM
 */
public class MoneyTest { 
    /*
    private Money f12CHF; 
    private Money f14CHF; 
    private Money f28USD; 
    
    @Before public void setUp() { 
        f12CHF= new Money(12, "CHF"); 
        f14CHF= new Money(14, "CHF"); 
        f28USD= new Money(28, "USD"); 
    }
    */

    public static junit.framework.Test suite() { 
        return new JUnit4TestAdapter(MoneyTest.class); 
    }
    
    
    @Test public void simpleAdd() {
        Money m12CHF= new Money(12, "CHF"); 
        Money m14CHF= new Money(14, "CHF"); 
        Money expected= new Money(26, "CHF"); 
        Money result= m12CHF.add(m14CHF); 
        assertTrue(expected.equals(result));
    }
    
    @Test public void testEquals() {
        Money m12CHF= new Money(12, "CHF");
        Money m14CHF= new Money(14, "CHF");

        assertTrue(!m12CHF.equals(null));
        assertEquals(m12CHF, m12CHF);
        assertEquals(m12CHF, new Money(12, "CHF")); // (1)
        assertTrue(!m12CHF.equals(m14CHF));
    }
}
