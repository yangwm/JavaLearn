/**
 * 
 */
package jref.hello5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * reflect method utils 
 * 
 * @author yangwm in Dec 23, 2009 9:28:32 PM
 */
public class MethodUtils {

    /** An empty class array */
    private static final Class<?>[] EMPTY_CLASS_PARAMETERS = new Class[0];
    /** An empty object array */
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    /**
     * 得到obj对象中属性名为fieldName的值  
     * 
     * @param object
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getFieldValue(
            Object object,
            String fieldName) {
        
        StringBuilder sb = new StringBuilder("get")
        .append(fieldName.substring(0, 1).toUpperCase())
        .append(fieldName.substring(1));

        return invokeMethodNoException(object, sb.toString(), null, null);
    }
    
    
    /**
     * 设置obj对象中属性名为fieldName的值  
     * 
     * @param object
     * @param fieldName
     * @param arg
     * @param parameterType
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object setFieldValue(
            Object object,
            String fieldName,
            Object arg,
            Class<?> parameterType) {
        
        StringBuilder sb = new StringBuilder("set")
        .append(fieldName.substring(0, 1).toUpperCase())
        .append(fieldName.substring(1));
        
        Object[] args = {arg};
        Class<?>[] parameterTypes = {parameterType};
        
        return invokeMethodNoException(object, sb.toString(), args, parameterTypes);
    }
    
    
    /**
     * Invoke a named method no exception. 
     * 
     * create by yangwm in Dec 23, 2009 9:56:06 PM
     * @param object
     * @param methodName
     * @param args
     * @param parameterTypes
     * @return
     */
    public static Object invokeMethodNoException(
            Object object,
            String methodName,
            Object[] args,
            Class<?>[] parameterTypes) {
        
        Object result = null;
        try {
            result = invokeMethod(object, methodName, args, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     * <p>Invoke a named method whose parameter type matches the object type.</p>
     *
     * <p>The behaviour of this method is less deterministic 
     * than {@link 
     * #invokeExactMethod(Object object,String methodName,Object [] args,Class[] parameterTypes)}. 
     * It loops through all methods with names that match
     * and then executes the first it finds with compatable parameters.</p>
     *
     * <p>This method supports calls to methods taking primitive parameters 
     * via passing in wrapping classes. So, for example, a <code>Boolean</code> class
     * would match a <code>boolean</code> primitive.</p>
     *
     *
     * @param object invoke method on this object
     * @param methodName get method with this name
     * @param args use these arguments - treat null as empty array
     * @param parameterTypes match these parameters - treat null as empty array
     * @return The value returned by the invoked method
     *
     * @throws NoSuchMethodException if there is no such accessible method
     * @throws InvocationTargetException wraps an exception thrown by the
     *  method invoked
     * @throws IllegalAccessException if the requested method is not accessible
     *  via reflection
     */
    public static Object invokeMethod(
            Object object,
            String methodName,
            Object[] args,
            Class<?>[] parameterTypes)
                throws
                    NoSuchMethodException,
                    IllegalAccessException,
                    InvocationTargetException {
        
        if (parameterTypes == null) {
            parameterTypes = EMPTY_CLASS_PARAMETERS;
        }        
        if (args == null) {
            args = EMPTY_OBJECT_ARRAY;
        }  

        Method method = object.getClass().getMethod(methodName, parameterTypes);
        
        if (method == null) {
            throw new NoSuchMethodException("No such accessible method: " +
                    methodName + "() on object: " + object.getClass().getName());
        }
        return method.invoke(object, args);
    }
    
    
    // ---------- invoke static method -----------------
    


    /**
     * 得到class对象中属性名为fieldName的值  
     * 
     * @param object
     * @param fieldName
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object getStaticFieldValue(
            Class<?> objectClass,
            String fieldName) {
        
        StringBuilder sb = new StringBuilder("get")
        .append(fieldName.substring(0, 1).toUpperCase())
        .append(fieldName.substring(1));

        return invokeStaticMethodNoException(objectClass, sb.toString(), null, null);
    }
    
    
    /**
     * 设置class对象中属性名为fieldName的值  
     * 
     * @param object
     * @param fieldName
     * @param arg
     * @param parameterType
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static Object setStaticFieldValue(
            Class<?> objectClass,
            String fieldName,
            Object arg,
            Class<?> parameterType) {
        
        StringBuilder sb = new StringBuilder("set")
        .append(fieldName.substring(0, 1).toUpperCase())
        .append(fieldName.substring(1));
        
        Object[] args = {arg};
        Class<?>[] parameterTypes = {parameterType};
        
        return invokeStaticMethodNoException(objectClass, sb.toString(), args, parameterTypes);
    }
    
    
    /**
     * Invoke a named static method no exception. 
     * 
     * create by yangwm in Dec 23, 2009 9:56:06 PM
     * @param object
     * @param methodName
     * @param args
     * @param parameterTypes
     * @return
     */
    public static Object invokeStaticMethodNoException(
            Class<?> objectClass,
            String methodName,
            Object[] args,
            Class<?>[] parameterTypes) {
        
        Object result = null;
        try {
            result = invokeStaticMethod(objectClass, methodName, args, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * <p>Invoke a named static method whose parameter type matches the object type.</p>
     *
     * <p>The behaviour of this method is less deterministic 
     * than {@link 
     * #invokeExactStaticMethod(Class objectClass,String methodName,Object [] args,Class[] parameterTypes)}. 
     * It loops through all methods with names that match
     * and then executes the first it finds with compatable parameters.</p>
     *
     * <p>This method supports calls to methods taking primitive parameters 
     * via passing in wrapping classes. So, for example, a <code>Boolean</code> class
     * would match a <code>boolean</code> primitive.</p>
     *
     *
     * @param objectClass invoke static method on this class
     * @param methodName get method with this name
     * @param args use these arguments - treat null as empty array
     * @param parameterTypes match these parameters - treat null as empty array
     * @return The value returned by the invoked method
     *
     * @throws NoSuchMethodException if there is no such accessible method
     * @throws InvocationTargetException wraps an exception thrown by the
     *  method invoked
     * @throws IllegalAccessException if the requested method is not accessible
     *  via reflection
     */
    public static Object invokeStaticMethod(
            Class<?> objectClass,
            String methodName,
            Object[] args,
            Class<?>[] parameterTypes)
                throws
                    NoSuchMethodException,
                    IllegalAccessException,
                    InvocationTargetException {
                    
        if (parameterTypes == null) {
            parameterTypes = EMPTY_CLASS_PARAMETERS;
        }        
        if (args == null) {
            args = EMPTY_OBJECT_ARRAY;
        }  

        Method method = objectClass.getMethod(methodName, parameterTypes);
        
        if (method == null) {
            throw new NoSuchMethodException("No such accessible method: " +
                    methodName + "() on class: " + objectClass.getName());
        }
        return method.invoke(null, args);
    }

    
}
