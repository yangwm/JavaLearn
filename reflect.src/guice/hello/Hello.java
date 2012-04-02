/**
 * 
 */
package guice.hello;

import com.google.inject.ImplementedBy;

/**
 * @author yangwm in Jan 3, 2010 11:04:06 PM
 */
@ImplementedBy(HelloImpl.class)
public interface Hello {
    
    void sayHello(String userName);
    
}
