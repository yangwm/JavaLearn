/**
 * 
 */
package junitstudy;

import java.util.ArrayList;

import junit.framework.JUnit4TestAdapter;

import org.junit.Test;

/**
 * @author yangwm in Apr 8, 2010 6:50:02 PM
 */
public class ExpectedTest {


    public static junit.framework.Test suite() { 
        return new JUnit4TestAdapter(ExpectedTest.class); 
    }
    
    
    @Test(expected= IndexOutOfBoundsException.class) public void empty() { 
        new ArrayList<Object>().get(100); 
    }
    
}
