package util;


import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;

import org.apache.commons.lang.ArrayUtils;

public class EntityUtil {
        
    /**
     * 返回代表当前对象的字符串对象，忽略ignores所指定的属性
     * 
     * modify by yangwm in Jul 29, 2009 11:39:56 AM 增加entity本身是数组的支持
     * create by yangwm in Dec 27, 2008 12:01:06 AM
     * @param entity
     * @param ignores
     * @return 包含所有的属性名与属性值，以及从父类中继承的属性。 不包含Object中的属性。
     */
    public static String toString(Object entity, String[] ignores) {
        if (entity == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Class<?> c = entity.getClass();
        sb.append(c.getName());
        sb.append("[");
        
        if (c.isArray()) {
            sb.append(ArrayUtils.toString(entity));
        } else {
            // 父类中继承的属性 
            Class<?> parent = c.getSuperclass();
            while (parent != null && !(parent == Object.class)) {
                Field[] fields = parent.getDeclaredFields();
                try {
                    appendFields(sb, fields, entity, ignores);
                } catch (Exception e) {
                }
                parent = parent.getSuperclass();
            }
            
            // 本身的属性 
            try {
                Field[] fields = c.getDeclaredFields();
                appendFields(sb, fields, entity, ignores);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        sb.append("]");
        return sb.toString();
    }

    /**
     * 返回代表当前对象的字符串对象。
     * 
     * create by yangwm in Dec 27, 2008 12:01:42 AM
     * @param entity
     * @return 包含所有的属性名与属性值，以及从父类中继承的属性。 不包含Object中的属性。
     */
    public static String toString(Object entity) {
        return toString(entity, new String[0]);
    }

    /**
     * 追加属性名与属性值 
     * 
     * modify by yangwm in Jul 29, 2009 11:39:56 AM 改变判断某一属性的类型是否是数组的实现逻辑   
     * modify by yangwm in Jun 1, 2009 11:12:15 PM 追加数组对象地址改为数组里的值 
     * create by yangwm in Dec 27, 2008 12:02:14 AM
     * @param sb         字符对象用来追加属性名与属性值   
     * @param fields     属性列表 
     * @param entity     实体 
     * @param ignores    需要忽略的属性列表 
     * @throws Exception
     */
    private static void appendFields(StringBuilder sb, Field[] fields, Object entity,
            String[] ignores) throws Exception {
        AccessibleObject.setAccessible(fields, true);
        sb.append("<");
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            if (!shouldIgnore(name, ignores)) {
                if (i != 0) {
                    sb.append(",");
                }
                
                //System.out.println(fields[i].getType());
                if (fields[i].getType().isArray()) {
                    sb.append(name + "=" + ArrayUtils.toString(fields[i].get(entity)));
                } else {
                    sb.append(name + "=" + fields[i].get(entity));
                }
            }
        }
        sb.append(">");
    }

    /**
     * 判断属性名是否在需要忽略的属性列表中 
     * 
     * create by yangwm in Dec 27, 2008 12:03:54 AM
     * @param field     属性名   
     * @param ignores   需要忽略的属性列表 
     * @return
     */
    private static boolean shouldIgnore(String field, String[] ignores) {
        boolean ignore = false;
        for (int i = 0; i < ignores.length; i++) {
            if (field.equalsIgnoreCase(ignores[i])) {
                ignore = true;
                break;
            }
        }
        return ignore;
    }

}
