/**
 * 
 */
package reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * 基本类型使用与判断测试 
 * 
 * @author yangwm May 13, 2010 4:18:51 PM
 */
public class PrimitiveTest {

    /**
     * @param args
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     */
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        Info obj = new Info();
        
        Field[] fields = Info.class.getDeclaredFields();
        for (Field field : fields) {
            //System.out.println(field.getGenericType());
            System.out.println(field.getType() + ", " + isBasicType(field.getType()));
            if (field.getType().isArray()) {
                //Object[] oArray = (Object[]) field.get(obj);
                Object oArray = field.get(obj);
                for (int i = 0; i < Array.getLength(oArray); i++) {
                    Object o = Array.get(oArray, i);
                    System.out.println(o);
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
        return cls.isPrimitive() || "java.lang".equals(cls.getPackage().getName());
    }
    
}

class Info {
    long l = 1;
    String s = "s";
    Integer i = 2;
    Object o = new Object();
    PrimitiveTest p = new PrimitiveTest();
    int[] intArray = {6, 7};
    String[] strArray = {"a", "b", "c"};
}

/*
long, true
class java.lang.String, false
class java.lang.Integer, false
class java.lang.Object, false
class reflect.PrimitiveTest, false
class [I, false
Exception in thread "main" java.lang.ClassCastException: [I cannot be cast to [Ljava.lang.Object;
    at reflect.PrimitiveTest.main(PrimitiveTest.java:28)

*/

/*
long, true
class java.lang.String, true
class java.lang.Integer, true
class java.lang.Object, true
class reflect.PrimitiveTest, false
class [I, false
6
7
class [Ljava.lang.String;, false
a
b
c

*/

