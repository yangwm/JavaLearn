/**
 * 
 */
package json.ext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import json.ext.JSONHelper;

/**
 * josn使用工具类 
 * 
 * @author yangwm May 21, 2010 10:48:52 AM 
 */
public class JsonUtil {

    /**
     * 使用JSONObject填充bean -- 使用名称替换功能（如：message_id替换为messageId）
     * 
     * @param bean
     * @param jsonObject
     */
    public static void fillBeanFromJSONObject(Object bean, JSONObject jsonObject) {
        fillBeanFromJSONObject(bean, jsonObject, true);
    }

    /**
     * 使用JSONObject填充bean 
     * 
     * @param bean
     * @param jsonObject
     * @param replaceName 是否使用名称替换功能（如：message_id替换为messageId）
     */
    public static void fillBeanFromJSONObject(Object bean, JSONObject jsonObject, boolean replaceName) {
        BeanHelper.fillBean(bean, jsonObject, replaceName);
    }
    
    /**
     * 获取bean对相应的JSONObject后toString -- 不追溯父类的祖先属性
     * 
     * @param bean
     * @return
     */
    public static String toJSONStrFromBean(Object bean) {
        return toJSONObjectFromBean(bean).toString();
    }

    /**
     * 获取bean对相应的JSONObject -- 不追溯父类的祖先属性
     * 
     * @param bean
     * @return
     */
    public static JSONObject toJSONObjectFromBean(Object bean) {
        return JSONHelper.toJSONObject(bean);
    }
    
    /*
    public static Object toBeanFromJSONObject(JSONObject json, Class<?> beanClass) {
        Object bean = null;
        
        return bean;
    }
    */
    
    
    /**
     * 获取字符串的JSONObject
     * 
     * @param str
     * @return
     */
    public static JSONObject toJSONObject(String str) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    
    
    /**
     * 获取字符串的JSONArray
     * 
     * @param str
     * @return
     */
    public static JSONArray toJSONArray(String str) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
    
    /**
     * 对象value以name为key放入jsonObject中
     * 
     * @param jsonObject
     * @param name
     * @param value
     */
    public static void put(JSONObject jsonObject, String name, Object value) {
        try {
            jsonObject.put(name, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
}
