/**
 * 
 */
package guice.hello;

import com.google.inject.Singleton;

/**
 * @author yangwm in Jan 3, 2010 11:05:25 PM
 */
@Singleton
public class HelloImpl implements Hello{
    
    @Override
    public void sayHello(String userName) {
        System.out.println("Hello: " + userName);
    }
    
}
