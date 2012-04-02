/**
 * 
 */
package jdyn.javassist;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yangwm in Dec 7, 2009 8:44:41 PM
 */
public class Run {

    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                
                // load the target class to be run
                Class<?> clas = Run.class.getClassLoader().loadClass(args[0]);
                
                System.out.println("clas.getClassLoader().getClass().getName()=" 
                        + clas.getClassLoader().getClass().getName());
                    
                // invoke "main" method of target class
                Class<?>[] ptypes = new Class<?>[] { args.getClass() };
                Method main = clas.getDeclaredMethod("main", ptypes);
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                main.invoke(null, new Object[] { pargs });
                
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            
        } else {
            System.out.println("Usage: jdyn.javassist.Run main-class args...");
        }

    }

}
/*
java jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
clas.getClassLoader().getClass().getName()=sun.misc.Launcher$AppClassLoader
Constructed string of length 1000
Constructed string of length 2000
Constructed string of length 4000
Constructed string of length 8000
Constructed string of length 16000

*/
/*
java jdyn.javassist.Run jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
clas.getClassLoader().getClass().getName()=sun.misc.Launcher$AppClassLoader
Constructed string of length 1000
Constructed string of length 2000
Constructed string of length 4000
Constructed string of length 8000
Constructed string of length 16000

*/

/*
java jdyn.javassist.Run jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
clas.getClassLoader().getClass().getName()=sun.misc.Launcher$AppClassLoader
Call to method buildString took 16 ms.
Constructed string of length 1000
Call to method buildString took 0 ms.
Constructed string of length 2000
Call to method buildString took 47 ms.
Constructed string of length 4000
Call to method buildString took 156 ms.
Constructed string of length 8000
Call to method buildString took 625 ms.
Constructed string of length 16000

*/

