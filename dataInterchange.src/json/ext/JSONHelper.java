/**
 * 
 */
package json.ext;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON帮助工具类
 * 
 * @author yangwm May 10, 2010 3:27:20 PM
 */
class JSONHelper {
    
    /**
     * 获取bean对相应的JSONObject -- not trace parent's Ancestor
     * 
     * @param bean
     * @return
     */
    static JSONObject toJSONObject(Object bean) {
        return toJSONObject(bean, false);
    }
    
    /**
     * 获取bean对相应的JSONObject
     * 
     * @param bean
     * @return
     */
    static JSONObject toJSONObject(Object bean, boolean traceAncestor) {
        if (bean == null) {
            return null;
        }
        
        JSONObject jsonObject = new JSONObject();

        Class<?> self = bean.getClass();
        
        // parent fileds 
        Class<?> parent = self.getSuperclass();
        boolean needFetch = true;
        while (needFetch && parent != null && !(parent == Object.class)) {
            putFieldsWithClass(jsonObject, parent, bean);
            parent = parent.getSuperclass();
            
            if (traceAncestor == false) {
                needFetch = false;
            }
        }
        
        // self fileds 
        putFieldsWithClass(jsonObject, self, bean);

        return jsonObject;
    }
    
    /**
     * bean对应的class的属性数组放入jsonObject中 
     * 
     * @param jsonObject
     * @param clazz
     * @param bean
     */
    private static void putFieldsWithClass(JSONObject jsonObject, Class<?> clazz, Object bean) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            putFields(jsonObject, fields, bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * bean的属性数组放入jsonObject中 
     * 
     * @param jsonObject
     * @param fields
     * @param bean
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * @throws JSONException 
     * @throws Exception
     */
    private static void putFields(JSONObject jsonObject, Field[] fields, Object bean) throws IllegalArgumentException, IllegalAccessException, JSONException {
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                String name = field.getName();
                Class<?> cls = field.getType();
                Object obj = field.get(bean);
                //System.out.println(name + ":" + obj + ", " + cls);
                
                if (isBasicType(cls)) {
                    jsonObject.put(name, obj);
                } else if (cls.isArray()) {
                    Object oArray = obj;
                    
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < Array.getLength(oArray); i++) {
                        Object o = Array.get(oArray, i);
                        jsonArray.put(toJSONObject(o));
                    }
                    
                    jsonObject.put(name, jsonArray);
                } else if (isDate(obj)){
                    jsonObject.put(name, getTime(obj));
                } else if (obj instanceof Collection){
                    Collection<?> oCollection = (Collection<?>) obj;
                    
                    JSONArray jsonArray = new JSONArray();
                    for (Object o : oCollection) {
                        jsonArray.put(toJSONObject(o));
                    }
                    
                    jsonObject.put(name, jsonArray);
                } else if (obj instanceof Map){
                    jsonObject.put(name, new JSONObject((Map<?, ?>)obj));
                } else {
                    jsonObject.put(name, toJSONObject(obj));
                }
            }
        }
    }


    /**
     * 判断是不是基本类型以及包装类 
     * 
     * @param cls
     * @return
     */
    private static boolean isBasicType(Class<?> cls) {
        if (cls.isArray()) {
            return false;
        }
        return cls.isPrimitive() 
            || "java.lang".equals(cls.getPackage().getName()); //8种基本类型、8种基本类型的包装类、java.lang.String
    }

    private static Object getTime(Object obj) {
        if (obj instanceof Date) {
            return ((Date)obj).getTime();
        }
        return ((Calendar)obj).getTimeInMillis();
    }

    private static boolean isDate(Object obj){
        return obj instanceof Date 
            || obj instanceof Calendar;
    }

}
