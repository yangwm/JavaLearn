/**
 * 
 */
package json.ext;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Bean帮助工具类
 * 
 * @author yangwm Jul 6, 2010 6:27:26 PM 
 */
public class BeanHelper {
    
    /*
     * collection object's type
     */
    private static final Map<String, Class<?>> collectionObjectTypeMap;
    
    // collection object's type config file name  
    private static final String COLLECTION_OBJECT_TYPE = "collectionObjectType.properties";
    
    static {
        Map<String, Class<?>> map = new HashMap<String, Class<?>>();
        Properties env = ConfigUtil.getConfigProperties(COLLECTION_OBJECT_TYPE);
        for (Entry<Object, Object> entry : env.entrySet()) {
            // like this map.put("recipientList", json.ext.Recipient.class);
            try {
                map.put((String)entry.getKey(), Class.forName((String)entry.getValue()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        
        collectionObjectTypeMap = map;
    }
    
    static void fillBean(Object bean, JSONObject jsonObject, boolean replaceName) {
        setFieldsWithKeys(bean, jsonObject, replaceName);
    }
    
    private static void setFieldsWithKeys(Object bean, JSONObject jsonObject, boolean replaceName) {
        try {
            Iterator<?> keyIter = jsonObject.keys();
            setFields(bean, keyIter, jsonObject, replaceName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * jsonObject的属性数组放入bean中
     * 
     * @param bean
     * @param keyIter
     * @param jsonObject
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static void setFields(Object bean, Iterator<?> keyIter, JSONObject jsonObject, boolean replaceName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InstantiationException {
        for (; keyIter.hasNext(); ) {
            Class<?> beanCls = bean.getClass();
            String name =  (String)keyIter.next();
            Object obj = jsonObject.opt(name);
            Class<?> cls = obj.getClass();
            
            String fieldName = getReplaceName(name, "_", replaceName);
            Field field = getField(beanCls, fieldName);
            Type fieldGenericType = field.getGenericType();
            
            //System.out.println(beanCls + "; " + name + ":" + obj + ", " + cls + "; " + fieldName + ":" + fieldGenericType.toString());
            if (field != null) {
                field.setAccessible(true);

                if (isBasicType(cls)) {
                    if ("class java.lang.Integer".equals(fieldGenericType.toString())) {
                        field.set(bean, new Integer(obj.toString()));
                    } else if ("class java.lang.Long".equals(fieldGenericType.toString())) {
                        field.set(bean, new Long(obj.toString()));
                    } else if ("class java.lang.Short".equals(fieldGenericType.toString())) {
                        field.set(bean, new Short(obj.toString()));
                    } else if ("class java.lang.Byte".equals(fieldGenericType.toString())) {
                        field.set(bean, new Byte(obj.toString()));
                    } else {
                        field.set(bean, obj);
                    }
                } else if (obj instanceof JSONArray) {
                    JSONArray subJsonArray = (JSONArray) obj;
                    Class<?> fieldType = field.getType();
                    
                    if(fieldType == List.class) {
                        List<Object> listObj = new ArrayList<Object>();
                        for(int i = 0; i < subJsonArray.length(); i++ ) {
                            Object subJsonArrayObj = subJsonArray.opt(i);
                            //System.out.println(subJsonArrayObj + ", " + subJsonArrayObj.getClass());
                            if (isBasicType(subJsonArrayObj.getClass())) {
                                listObj.add(subJsonArrayObj);
                            } else if (subJsonArrayObj instanceof JSONObject) {
                                JSONObject subJsonArrayObject = (JSONObject) subJsonArrayObj;
                                Class<?> collectionObjectType = collectionObjectTypeMap.get(fieldName);
                                if (collectionObjectType != null) {
                                    Object otherObj = collectionObjectType.newInstance();
                                    fillBean(otherObj, subJsonArrayObject, replaceName);
                                    listObj.add(otherObj);
                                }
                            }
                        }
                        field.set(bean, listObj);
                    }
                } else if (obj instanceof JSONObject) {
                    Class<?> fieldType = field.getType();
                    
                    if(fieldType == Map.class){
                        Object mapObj = new HashMap<Object, Object>();
                        field.set(bean, mapObj);
                    } else {
                        Object otherObj = field.getType().newInstance();
                        JSONObject subJsonObject = (JSONObject) obj;
                        fillBean(otherObj, subJsonObject, replaceName);
                        field.set(bean, otherObj);
                    }
                } else {
                    field.set(bean, obj);
                }
            }
            
        }
        
    }

    private static Field getField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (SecurityException  e) {
            //e.printStackTrace();
        } catch (NoSuchFieldException  e) {
            //e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return field;
    }
    
    /**
     * 返回根据标记改变后的字段名 
     * 
     * @param name
     * @param token
     * @return
     */
    private static String getReplaceName(String name, String token, boolean replaceName) {
        String fieldName = name;
        if (replaceName == true) {
            fieldName = getReplaceName(name, token);
        }
        return fieldName;
    }
    
    /**
     * 返回根据标记改变后的字段名 
     * 
     * @param name
     * @param token
     * @return
     */
    private static String getReplaceName(String name, String token) {
        StringBuilder builder = new StringBuilder();
        if (name != null) {
            String after = name;
            int start = after.indexOf(token);
            int end = (start + token.length() + 1) <= after.length() ? (start + token.length() + 1) : after.length();
            while (start > -1) {
                String before = after.substring(0, start);
                String content = after.substring(start + token.length(), end).toUpperCase();
                after = after.substring(end);
                
                builder.append(before);
                builder.append(content);
                
                start = after.indexOf(token);
                end = (start + token.length() + 1) <= after.length() ? (start + token.length() + 1) : after.length();
            }
            builder.append(after);
        }
        //System.out.println("<<<getFieldName " + name + ", " + builder.toString());
        return builder.toString();
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

}
