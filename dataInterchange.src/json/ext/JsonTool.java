package json.ext;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonTool {
    private static Class[] classes = {
        byte.class, short.class, int.class,long.class,
        float.class, double.class, boolean.class, char.class,
        Byte.class, Short.class, Integer.class, Long.class,
        Float.class, Double.class, Boolean.class, Character.class,
        String.class
    };
    
    private static Class[] arrayClasses = {
        byte[].class, short[].class, int[].class,long[].class,
        float[].class, double[].class, boolean[].class, char[].class,
        Byte[].class, Short[].class, Integer[].class, Long[].class,
        Float[].class, Double[].class, Boolean[].class, Character[].class,
        String[].class
    };
    
    public static Object json2Bean(JSONObject json, Class beanClass, Map<String, Class> collectionComponentTypes) throws Exception{
        Object bean = null;
        bean = beanClass.newInstance();
                
        Method[] setters = getSetters(beanClass);
        for(int i = 0; i < setters.length; i++){
            Method setter = setters[i];
            String property = getProperty(setter.getName());
            if(property == null || json.get(property) == null) continue;
            Class paramType = setter.getParameterTypes()[0];
            if(isPrimitive(paramType)){
                        setter.invoke(bean, json.get(property));
            } else if(isPrimitiveArray(paramType)){
                JSONArray jarr = json.getJSONArray(property);
                Object arr = Array.newInstance(getType(paramType), jarr.length());
                for(int j = 0; j < jarr.length(); j++)
                    Array.set(arr, j, jarr.get(j));
                setter.invoke(bean, arr);
            }else if(paramType == java.util.Date.class){
                setter.invoke(bean, new java.util.Date((long)json.getDouble(property)));
            }else if(paramType == java.sql.Date.class){
                setter.invoke(bean, new java.sql.Date((long)json.getDouble(property)));
            }else if(paramType == java.sql.Time.class){
                setter.invoke(bean, new java.sql.Time((long)json.getDouble(property)));
            }else if(paramType == java.sql.Timestamp.class){
                setter.invoke(bean, new java.sql.Timestamp((long)json.getDouble(property)));
            }else if(paramType == java.util.Calendar.class){
                java.util.Calendar c = java.util.Calendar.getInstance();
                c.setTimeInMillis((long)json.getDouble(property));
                setter.invoke(bean, c);
            }else if(paramType == java.util.Set.class){
                Class componentType = null;
                if(collectionComponentTypes != null)
                    componentType = collectionComponentTypes.get(property);
                java.util.Set s = new java.util.HashSet();
                initCollection(s, json.getJSONArray(property), componentType, collectionComponentTypes);
                setter.invoke(bean, s);
            }else if(paramType == java.util.List.class ||
                    paramType == java.util.Collection.class){
                Class componentType = null;
                if(collectionComponentTypes != null)
                    componentType = collectionComponentTypes.get(property);
                java.util.List l = new java.util.ArrayList();
                initCollection(l, json.getJSONArray(property), componentType, collectionComponentTypes);
                setter.invoke(bean, l);
            }else if(paramType.getName().startsWith("[")){
                Object[] arr = toObjectArray(paramType, json.getJSONArray(property), collectionComponentTypes);
                setter.invoke(bean, arr);
            }else{
                setter.invoke(bean, json2Bean(json.getJSONObject(property), paramType, collectionComponentTypes));
            }
        }
        
        return bean;
    }
    
    private static Class<?> getType(Class paramType) {
        Class type = null;
        for(int i = 0; i < arrayClasses.length; i++){
            if(paramType == arrayClasses[i]){
                type = classes[i];
            }
        }
        return type;
    }

    public static Object[] toObjectArray(Class paramType, JSONArray jarr, Map<String, Class> collectionComponentTypes) throws Exception{
        Object[] objs = new Object[jarr.length()];
        Class beanClass = Class.forName(paramType.getName().substring(2));
        for(int i = 0; i < objs.length; i++){
            objs[i] = json2Bean(jarr.getJSONObject(i), beanClass, collectionComponentTypes);
        }
        return objs;
    }

    public static void initCollection(Collection c, JSONArray jarr, Class componentType, Map<String, Class> collectionComponentTypes) throws Exception{
        //component of collection is primitive
        if(componentType == null || isPrimitive(componentType)){
            for(int i = 0; i < jarr.length(); i++ )
                c.add(jarr.get(i));
        }else if(componentType == java.util.Date.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.util.Date((long)jarr.getDouble(i)));
        }else if(componentType == java.sql.Date.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.sql.Date((long)jarr.getDouble(i)));
        }else if(componentType == java.sql.Time.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.sql.Time((long)jarr.getDouble(i)));
        }else if(componentType == java.sql.Timestamp.class){
            for(int i = 0; i < jarr.length(); i++)
                c.add(new java.sql.Timestamp((long)jarr.getDouble(i)));
        }else if(componentType == java.util.Calendar.class){
            for(int i = 0; i < jarr.length(); i++){
                java.util.Calendar cal = java.util.Calendar.getInstance();
                cal.setTimeInMillis((long)jarr.getDouble(i));
                c.add(cal);
            }
        }else if(componentType != null){
            for(int i = 0; i < jarr.length(); i++){
                c.add(json2Bean(jarr.getJSONObject(i), componentType, collectionComponentTypes));
            }
        }
    }
    
    public static JSONObject bean2Json(Object bean){
        Class clazz = bean.getClass();
        JSONObject json = new JSONObject();
        Method[] getters = getGetters(clazz);
        int len =  getters.length;
        for(int i = 0; i < len; i++){
            Method m = getters[i];
            String property = getProperty(m.getName());
            Class retType = m.getReturnType();
            if(property == null || property.equals("class") )continue;
            try {
                Object value = m.invoke(bean, null);
                if(isPrimitive(retType))
                    json.put(property, value);
                else if(isPrimitiveArray(retType)){
                    JSONArray jarr = priArray(value);
                    json.put(property, jarr);
                }else if(isDate(value)){
                    json.put(property, getTime(value));
                }else if(value instanceof Collection){
                    json.put(property, collectionToJson(value));
                }else if(retType.getName().startsWith("[")){
                    json.put(property, arrayToJarr(value));
                }else if(value instanceof Map){
                    json.put(property, new JSONObject((Map)value));
                }
                else{
                    json.put(property, bean2Json(value));
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Error to get value");
            }
        }
        
        return json;
    }
    
    public static JSONArray arrayToJarr(Object value) {
        // TODO Auto-generated method stub
        Object[] arr = (Object[])value;
        Collection c = new ArrayList();
        for(Object o : arr)
            c.add(o);
        return collectionToJson(c);
    }

    public static JSONArray collectionToJson(Object value) {
        // TODO Auto-generated method stub
        JSONArray jarr = new JSONArray();
        Collection c = (Collection)value;
        for(Object o : c){
            Class type = o.getClass();
            if(isPrimitive(type)) jarr.put(o);
            else if(isPrimitiveArray(type)) jarr.put(priArray(o));
            else if(isDate(o)) jarr.put(getTime(o));
            else if(o instanceof Collection) jarr.put(collectionToJson(o));
            else if(type.getName().startsWith("[")) jarr.put(arrayToJarr(o));
            else if(o instanceof Map) jarr.put(new JSONObject((Map)o));
            else jarr.put(bean2Json(o));
        }
        
        return jarr;
    }

    private static Object getTime(Object value) {
        // TODO Auto-generated method stub
        if(value instanceof Date)
            return ((Date)value).getTime() ;
        return ((Calendar)value).getTimeInMillis();
    }

    private static boolean isDate(Object value){
        return value instanceof Date ||
         value instanceof Calendar;
    }
    
    public static JSONArray priArray(Object value){
        JSONArray jarr = new JSONArray();
        int arLen = Array.getLength(value);
        for(int j = 0; j < arLen; j++){
            jarr.put(Array.get(value, j));
        }
        
        return jarr;
    }
    
    private static String getProperty(String name) {
        if(name.startsWith("get") || name.startsWith("set")) {
            name = name.substring(3);
        } else if(name.startsWith("is")){
            name = name.substring(2);
        }
        if(name.length() > 0){
            StringBuilder sb = new StringBuilder(name.length());
            char first = name.charAt(0);
            if(name.length() > 1){
                char second = name.charAt(1);
                if(second >='A' && second <='Z');
                else first += 32;
            }
            sb.append(first).append(name.substring(1));
            return sb.toString();
        }
        
        return null;
    }

    private static Method[] getGetters(Class clazz){
        Method[] methods = clazz.getMethods();
        ArrayList<Method> getters = new ArrayList<Method>();
        for(int i = 0; i < methods.length; i++){
            if(methods[i].getName().startsWith("get") ||
                methods[i].getName().startsWith("is") ){
                Class[] paramTypes = methods[i].getParameterTypes();
                if(paramTypes == null || paramTypes.length == 0 )
                    getters.add(methods[i]);
            }
        }
        
        Method[] retMethods = new Method[getters.size()];
        int i = 0;
        for(Method m : getters){
            retMethods[i++] = m;
        }
        
        return retMethods;
    }
    
    private static Method[] getSetters(Class clazz){
        Method[] methods = clazz.getMethods();
        ArrayList<Method> setters = new ArrayList<Method>();
        for(int i = 0; i < methods.length; i++){
            if(methods[i].getName().startsWith("set")){
                if(methods[i].getParameterTypes() != null &&
                        methods[i].getParameterTypes().length == 1) setters.add(methods[i]);
            }
        }
        
        Method[] retMethods = new Method[setters.size()];
        int i = 0;
        for(Method m : setters){
            retMethods[i++] = m;
        }
        
        return retMethods;
    }
    
    private static boolean isPrimitive(Class clazz){
        for(int i = 0; i < classes.length; i++){
            if(classes[i] == clazz) return true;
        }
        
        return false;
    }
    
    private static boolean isPrimitiveArray(Class clazz){
        for(int i = 0; i < arrayClasses.length; i++){
            if(arrayClasses[i] == clazz) return true;
        }
        
        return false;
    }
    
    private static boolean isSpecificClass(Class clazz){
        return isPrimitive(clazz) || isPrimitiveArray(clazz);
    }

}
