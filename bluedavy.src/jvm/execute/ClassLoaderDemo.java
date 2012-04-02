/**
 * 
 */
package jvm.execute;

/**
 * 
 * 
 * @author yangwm Aug 1, 2010 10:35:46 PM
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {
        System.out.println(ClassLoaderDemo.class.getClassLoader());
        System.out.println(ClassLoaderDemo.class.getClassLoader().getParent());
        System.out.println(ClassLoaderDemo.class.getClassLoader().getParent().getParent());
    }

}

/*
sun.misc.Launcher$AppClassLoader@1be2d65
sun.misc.Launcher$ExtClassLoader@9664a1
null

*/
