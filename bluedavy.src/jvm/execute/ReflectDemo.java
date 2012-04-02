/**
 * 
 */
package jvm.execute;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author yangwm Aug 1, 2010 10:40:29 PM
 */
public class ReflectDemo {
    private static final int WARMUP_COUNT = 10700;
    private ForReflection testClass = new ForReflection();
    private static Map<String, Method> methodMap = new HashMap<String, Method>();

    public static void main(String[] args) throws Exception {
        System.out.println(System.getProperty("java.vm.name")
                + ", " + System.getProperty("java.vm.info")
                + ", " + System.getProperty("java.version"));
        
        Method method = ForReflection.class.getMethod("execute", 
                new Class<?>[] {String.class});
        methodMap.put("method", method);
        
        ReflectDemo reflectDemo = new ReflectDemo();
        for (int i = 0; i < 20; i++) {
            reflectDemo.testDirectCall();
            reflectDemo.testNoCacheMethodCall();
            reflectDemo.testCacheMethodCall();
        }
        
        long beginTime = System.currentTimeMillis();
        reflectDemo.testDirectCall();
        long endTime = System.currentTimeMillis();
        System.out.println("direct invoke cosume: " + (endTime - beginTime) + " milliseconds");
        
        beginTime = System.currentTimeMillis();
        reflectDemo.testNoCacheMethodCall();
        endTime = System.currentTimeMillis();
        System.out.println("no cache method reflect invoke cosume: " + (endTime - beginTime) + " milliseconds");
        
        beginTime = System.currentTimeMillis();
        reflectDemo.testCacheMethodCall();
        endTime = System.currentTimeMillis();
        System.out.println("cache method reflect invoke cosume: " + (endTime - beginTime) + " milliseconds");
        
    }
    
    public void testDirectCall() throws Exception {
        for (int i = 0; i < WARMUP_COUNT; i++) {
            testClass.execute("hello");
        }
    }

    public void testNoCacheMethodCall() throws Exception {
        for (int i = 0; i < WARMUP_COUNT; i++) {
            Method method = ForReflection.class.getMethod("execute", 
                    new Class<?>[] {String.class});
            method.invoke(testClass, new Object[]{"hello"});
        }
    }

    public void testCacheMethodCall() throws Exception {
        for (int i = 0; i < WARMUP_COUNT; i++) {
            Method method = methodMap.get("method");
            method.invoke(testClass, new Object[]{"hello"});
        }
    }

}

class ForReflection {
    public void execute(String message) {
        String b = this.toString() + message;
    }
}

/*
-server -Xms128M -Xmx128M:
Java HotSpot(TM) Server VM, mixed mode, 1.7.0-ea
direct invoke cosume: 0 milliseconds
no cache method reflect invoke cosume: 31 milliseconds
cache method reflect invoke cosume: 0 milliseconds


-server -Xms128M -Xmx128M -Xint:
Java HotSpot(TM) Server VM, interpreted mode, 1.7.0-ea
direct invoke cosume: 94 milliseconds
no cache method reflect invoke cosume: 312 milliseconds
cache method reflect invoke cosume: 156 milliseconds

*/
