/**
 * 
 */
package jdyn.javassist;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author yangwm in Dec 7, 2009 10:26:07 PM
 */
public class VerboseLoader extends URLClassLoader {
    
    protected VerboseLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }
    
    @Override
    public Class<?> loadClass(String name)
        throws ClassNotFoundException {
        System.out.println("loadClass: " + name);
        return super.loadClass(name);
    }
    
    @Override
    protected Class<?> findClass(String name)
        throws ClassNotFoundException {
        System.out.println("findclass: loaded " + name + " from this loader");
        return super.findClass(name);
    }
    
    public static void main(String[] args) 
            throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if (args.length >= 1) {
            try {
                
                // get paths to be used for loading
                ClassLoader base = ClassLoader.getSystemClassLoader();
                URL[] urls;
                System.out.println("(base instanceof URLClassLoader)=" 
                        + (base instanceof URLClassLoader));
                if (base instanceof URLClassLoader) {
                    urls = ((URLClassLoader)base).getURLs();
                } else {
                    urls = new URL[] { new File(".").toURI().toURL() };
                }
                
                // list the paths actually being used
                System.out.println("Loading from paths:");
                for (int i = 0; i < urls.length; i++) {
                    System.out.println(" " + urls[i]);
                }
                
                // load target class using custom class loader
                VerboseLoader loader = new VerboseLoader(urls, base.getParent());
                Class<?> clas = loader.loadClass(args[0]);
                
                // ??? 
                Thread.currentThread().setContextClassLoader(loader);
                System.out.println("clas.getClassLoader().getClass().getName()=" 
                        + clas.getClassLoader().getClass().getName());
                    
                // invoke "main" method of target class
                Class<?>[] ptypes = new Class[] { args.getClass() };
                Method main = clas.getDeclaredMethod("main", ptypes);
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                main.invoke(null, new Object[] { pargs });
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            
        } else {
            System.out.println("Usage: jdyn.javassist.VerboseLoader main-class args...");
        }
    }
}

/*
java jdyn.javassist.VerboseLoader jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
(base instanceof URLClassLoader)=true
Loading from paths:
 file:/D:/study/tempProject/JavaLearn/classes/
 file:/D:/study/tempProject/JavaLearn/lib/ldapbp.jar
 file:/D:/study/tempProject/JavaLearn/lib/log4j-1.2.15.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-lang-2.4.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-beanutils-1.8.0.jar
 file:/D:/study/tempProject/JavaLearn/lib/J7Zip.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-dbutils-1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-digester-2.0.jar
 file:/D:/study/tempProject/JavaLearn/lib/quartz-all-1.6.5.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-collections-3.2.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-betwixt-0.8.jar
 file:/D:/study/tempProject/JavaLearn/lib/dom4j-1.6.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/jaxen-1.1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/ojdbc5_g.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-logging-1.1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/xml-apis-2.0.2.jar
 file:/D:/study/tempProject/JavaLearn/lib/Log4j_ultrapower_1.0_bate.jar
 file:/D:/study/tempProject/JavaLearn/lib/javassist-1.1.jar
loadClass: jdyn.javassist.StringBuilder
findclass: loaded jdyn.javassist.StringBuilder from this loader
loadClass: java.lang.Object
clas.getClassLoader().getClass().getName()=jdyn.javassist.VerboseLoader
loadClass: java.lang.String
loadClass: java.lang.Integer
loadClass: java.lang.StringBuilder
loadClass: java.lang.System
loadClass: java.io.PrintStream
Constructed string of length 1000
Constructed string of length 2000
Constructed string of length 4000
Constructed string of length 8000
Constructed string of length 16000

*/

/*
java jdyn.javassist.VerboseLoader jdyn.javassist.Run
(base instanceof URLClassLoader)=true
Loading from paths:
 file:/D:/study/tempProject/JavaLearn/classes/
 file:/D:/study/tempProject/JavaLearn/lib/ldapbp.jar
 file:/D:/study/tempProject/JavaLearn/lib/log4j-1.2.15.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-lang-2.4.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-beanutils-1.8.0.jar
 file:/D:/study/tempProject/JavaLearn/lib/J7Zip.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-dbutils-1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-digester-2.0.jar
 file:/D:/study/tempProject/JavaLearn/lib/quartz-all-1.6.5.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-collections-3.2.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-betwixt-0.8.jar
 file:/D:/study/tempProject/JavaLearn/lib/dom4j-1.6.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/jaxen-1.1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/ojdbc5_g.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-logging-1.1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/xml-apis-2.0.2.jar
 file:/D:/study/tempProject/JavaLearn/lib/Log4j_ultrapower_1.0_bate.jar
 file:/D:/study/tempProject/JavaLearn/lib/javassist-1.1.jar
loadClass: jdyn.javassist.Run
findclass: loaded jdyn.javassist.Run from this loader
loadClass: java.lang.Object
clas.getClassLoader().getClass().getName()=jdyn.javassist.VerboseLoader
loadClass: java.lang.Throwable
loadClass: java.lang.ClassNotFoundException
loadClass: java.lang.NoSuchMethodException
loadClass: java.lang.IllegalArgumentException
loadClass: java.lang.IllegalAccessException
loadClass: java.lang.reflect.InvocationTargetException
loadClass: java.lang.String
loadClass: java.lang.System
loadClass: java.io.PrintStream
Usage: jdyn.javassist.Run main-class args...

*/

/*
java jdyn.javassist.VerboseLoader jdyn.javassist.Run jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
(base instanceof URLClassLoader)=true
Loading from paths:
 file:/D:/study/tempProject/JavaLearn/classes/
 file:/D:/study/tempProject/JavaLearn/lib/ldapbp.jar
 file:/D:/study/tempProject/JavaLearn/lib/log4j-1.2.15.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-lang-2.4.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-beanutils-1.8.0.jar
 file:/D:/study/tempProject/JavaLearn/lib/J7Zip.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-dbutils-1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-digester-2.0.jar
 file:/D:/study/tempProject/JavaLearn/lib/quartz-all-1.6.5.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-collections-3.2.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-betwixt-0.8.jar
 file:/D:/study/tempProject/JavaLearn/lib/dom4j-1.6.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/jaxen-1.1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/ojdbc5_g.jar
 file:/D:/study/tempProject/JavaLearn/lib/commons-logging-1.1.1.jar
 file:/D:/study/tempProject/JavaLearn/lib/xml-apis-2.0.2.jar
 file:/D:/study/tempProject/JavaLearn/lib/Log4j_ultrapower_1.0_bate.jar
 file:/D:/study/tempProject/JavaLearn/lib/javassist-1.1.jar
loadClass: jdyn.javassist.Run
findclass: loaded jdyn.javassist.Run from this loader
loadClass: java.lang.Object
clas.getClassLoader().getClass().getName()=jdyn.javassist.VerboseLoader
loadClass: java.lang.Throwable
loadClass: java.lang.ClassNotFoundException
loadClass: java.lang.NoSuchMethodException
loadClass: java.lang.IllegalArgumentException
loadClass: java.lang.IllegalAccessException
loadClass: java.lang.reflect.InvocationTargetException
loadClass: java.lang.String
loadClass: java.lang.Class
loadClass: java.lang.ClassLoader
loadClass: jdyn.javassist.StringBuilder
findclass: loaded jdyn.javassist.StringBuilder from this loader
loadClass: java.lang.System
loadClass: java.lang.StringBuilder
loadClass: java.io.PrintStream
clas.getClassLoader().getClass().getName()=jdyn.javassist.VerboseLoader
loadClass: java.lang.reflect.Method
loadClass: java.lang.Integer
Constructed string of length 1000
Constructed string of length 2000
Constructed string of length 4000
Constructed string of length 8000
Constructed string of length 16000

*/
