/**
 * 
 */
package jdyn.javassist;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

/**
 * @author yangwm in Dec 7, 2009 11:24:39 PM
 */
public class JavassistRun {

    public static void main(String[] args) {
        if (args.length >= 1) {
            try {
                
                // set up class loader with translator
                Translator xlat = new VerboseTranslator();
                ClassPool pool = ClassPool.getDefault(xlat);
                Loader loader = new Loader(pool);
                    
                // invoke "main" method of target class
                String[] pargs = new String[args.length-1];
                System.arraycopy(args, 1, pargs, 0, pargs.length);
                loader.run(args[0], pargs);
                
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            
        } else {
            System.out.println("Usage: jdyn.javassist.JavassistRun main-class args...");
        }
    }
    
    public static class VerboseTranslator implements Translator {
        
        @Override
        public void start(ClassPool pool) {}
        
        @Override
        public void onWrite(ClassPool pool, String cname) {
            System.out.println("onWrite called for " + pool.getClass().getName() 
                    + " " + cname);
        }

        /*
        @Override
        public void onLoad(ClassPool pool, String cname)
                throws NotFoundException, CannotCompileException {
            System.out.println("onLoad called for " + pool.getClass().getName() 
                    + " " + cname);
        }
        */
    }

}

/*
java -classpath ".;..\lib\javassist-1.1.jar" jdyn.javassist.JavassistRun jdyn.javassist.Run jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
onWrite called for javassist.ClassPool jdyn.javassist.Run
onWrite called for javassist.ClassPool jdyn.javassist.StringBuilder
clas.getClassLoader().getClass().getName()=javassist.Loader
Constructed string of length 1000
Constructed string of length 2000
Constructed string of length 4000
Constructed string of length 8000
Constructed string of length 16000
*/
