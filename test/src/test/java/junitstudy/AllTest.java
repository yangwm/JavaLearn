/**
 * 
 */
package junitstudy;

import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author yangwm in Apr 8, 2010 6:47:24 PM
 */
@RunWith(Suite.class)
@SuiteClasses({
    MoneyTest.class,
    ExpectedTest.class
})
public class AllTest {

    /*
    public static void main(String[] args) {
        junit.textui.TestRunner.run (suite());
    }
    */
    
    public static junit.framework.Test suite() { 
        return new JUnit4TestAdapter(AllTest.class); 
    }
    
}

//java -classpath ".;..\lib\junit-4.8.1.jar" junit.textui.TestRunner junitstudy.AllTest
