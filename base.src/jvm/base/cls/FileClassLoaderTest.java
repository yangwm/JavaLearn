/**
 * 
 */
package jvm.base.cls;

/**
 * 
 * 
 * @author yangwm Apr 17, 2010 2:01:07 PM
 */
public class FileClassLoaderTest {
    public static void main(String[] args) throws Exception {
        FileClassLoader loader = new FileClassLoader();
        Class<?> objClass = loader.findClass("ArraysTest");
        
        Object obj = objClass.newInstance();
        System.out.println(objClass.getName());
        System.out.println(objClass.getClassLoader());
        System.out.println(obj);
    }
}

/*
ArraysTest
jvm.base.cls.FileClassLoader@a0dcd9
ArraysTest@15f5897

*/

