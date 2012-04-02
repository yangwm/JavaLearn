/**
 * 
 */
package jref.begin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yangwm in Nov 28, 2009 8:15:12 PM
 */
public class RunClassMethod {

    /**
     * create by yangwm in Nov 28, 2009 8:15:18 PM
     * @param args
     * @throws ClassNotFoundException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IllegalArgumentException 
     * @throws NoSuchMethodException 
     * @throws SecurityException 
     */
    public static void main(String[] args) throws ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException, SecurityException, NoSuchMethodException {
        if (args.length != 2) {
            System.out.println("args.length != 2, use java className methodName");
            return;
        }
        
        String className = args[0];
        String methodName = args[1];
        
        Class<?> cls = Class.forName(className);
        
        Constructor<?>[] cons = cls.getConstructors();
        Constructor<?> con = cons[0];
        
        Class<?>[] paramTypes = con.getParameterTypes();
        
        Object[] params = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            if (paramTypes[i].isPrimitive()==true ) {
                params[i] = i;
            }
        }
        
        Object obj = con.newInstance(params);
        Method method = cls.getMethod(methodName, paramTypes);
        
        Object returnObj = method.invoke(obj, params);
        System.out.println("returnObj=" + returnObj);
    }

}

/*
java.lang.String hashCode
returnObj=0
*/

