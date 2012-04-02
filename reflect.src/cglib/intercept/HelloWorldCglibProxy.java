/**
 * 
 */
package cglib.intercept;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * 
 * 
 * @author yangwm Apr 15, 2010 6:17:02 PM
 */
public class HelloWorldCglibProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) 
            throws Throwable {
        System.out.println(">>>intercept " + method.getName());
        methodProxy.invokeSuper(object, args);
        System.out.println("<<<intercept " + method.getName());
        return null;
    }
}
