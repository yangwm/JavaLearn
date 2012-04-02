package jvm.base.cls;

import java.net.*;

/**
 * URL 类加载器测试 
 */
public class ClasspathTest {
    public static void main(String[] args) throws Exception {
        String oldClassPath = System.getProperty("java.class.path");
        System.out.println(oldClassPath);
        System.setProperty("java.class.path", oldClassPath + ";D:\\study\\tempProject\\JavaLearn\\src");
        System.out.println(System.getProperty("java.class.path"));
        URL[] urls = {new URL("file:/d:/study/tempProject/JavaLearn/ojdbc14.jar")};
        URLClassLoader loader = URLClassLoader.newInstance(urls);
        Class.forName("jvm.base.cls.ClasspathTest").newInstance();
        //loader.loadClass("oracle.jdbc.Const").newInstance();
    }
}
