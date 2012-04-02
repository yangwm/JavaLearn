/**
 * 
 */
package jdyn.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Loader;
import javassist.NotFoundException;
import javassist.Translator;

/**
 * @author yangwm in Dec 8, 2009 11:18:35 AM
 */
public class TranslateTiming {

    private static void addTiming(CtClass clas, String mname)
        throws NotFoundException, CannotCompileException {
        
        //  get the method information (throws exception if method with
        //  given name is not declared directly by this class, returns
        //  arbitrary choice if more than one with the given name)
        CtMethod mold = clas.getDeclaredMethod(mname);
        
        //  rename old method to synthetic name, then duplicate the
        //  method with original name for use as interceptor
        String nname = mname+"$impl";
        mold.setName(nname);
        CtMethod mnew = CtNewMethod.copy(mold, mname, clas, null);
        
        //  start the body text generation by saving the start time
        //  to a local variable, then call the timed method; the
        //  actual code generated needs to depend on whether the
        //  timed method returns a value
        String type = mold.getReturnType().getName();
        StringBuffer body = new StringBuffer();
        body.append("{\nlong start = System.currentTimeMillis();\n");
        if (!"void".equals(type)) {
            body.append(type + " result = ");
        }
        body.append(nname + "($$);\n");
        
        //  finish body text generation with call to print the timing
        //  information, and return saved value (if not void)
        body.append("System.out.println(\"Call to method " + mname +
            " took \" +\n (System.currentTimeMillis()-start) + " +
            "\" ms.\");\n");
        if (!"void".equals(type)) {
            body.append("return result;\n");
        }
        body.append("}");
        
        //  replace the body of the interceptor method with generated
        //  code block and add it to class
        mnew.setBody(body.toString());
        clas.addMethod(mnew);
        
        //  print the generated code block just to show what was done
        System.out.println("Interceptor method body:");
        System.out.println(body.toString());
    }
    
    public static void main(String[] args) {
        if (args.length >= 3) {
            try {
                
                // set up class loader with translator
                Translator xlat =
                    new SimpleTranslator(args[0], args[1]);
                ClassPool pool = ClassPool.getDefault(xlat);
                Loader loader = new Loader(pool);
                    
                // invoke "main" method of target class
                String[] pargs = new String[args.length-3];
                System.arraycopy(args, 3, pargs, 0, pargs.length);
                loader.run(args[2], pargs);
                
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
            
        } else {
            System.out.println("Usage: jdyn.javassist.TranslateTiming" +
                " class-name method-mname main-class args...");
        }
    }
    
    public static class SimpleTranslator implements Translator {
        private String m_className;
        private String m_methodName;
        
        public SimpleTranslator(String cname, String mname) {
            m_className = cname;
            m_methodName = mname;
        }
        
        @Override
        public void start(ClassPool pool) {}
        
        @Override
        public void onWrite(ClassPool pool, String cname)
            throws NotFoundException, CannotCompileException {
            if (cname.equals(m_className)) {
                CtClass clas = pool.get(cname);
                addTiming(clas, m_methodName);
            }
        }

        /*
        @Override
        public void onLoad(ClassPool pool, String classname)
                throws NotFoundException, CannotCompileException {
            if (classname.equals(m_className)) {
                CtClass clas = pool.get(classname);
                addTiming(clas, m_methodName);
            }
        }
        */
        
    }

}

/*
java -classpath ".;..\lib\javassist-1.1.jar" jdyn.javassist.TranslateTiming jdyn.javassist.StringBuilder buildString jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
Interceptor method body:
{
long start = System.currentTimeMillis();
java.lang.String result = buildString$impl($$);
System.out.println("Call to method buildString took " +
 (System.currentTimeMillis()-start) + " ms.");
return result;
}
clas.getClassLoader().getClass().getName()=javassist.Loader
Call to method buildString took 15 ms.
Constructed string of length 1000
Call to method buildString took 0 ms.
Constructed string of length 2000
Call to method buildString took 47 ms.
Constructed string of length 4000
Call to method buildString took 125 ms.
Constructed string of length 8000
Call to method buildString took 547 ms.
Constructed string of length 16000

*/

/*
java -classpath ".;..\lib\javassist-1.1.jar" jdyn.javassist.TranslateTiming jdyn.javassist.StringBuilder buildString jdyn.javassist.Run jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
Interceptor method body:
{
long start = System.currentTimeMillis();
java.lang.String result = buildString$impl($$);
System.out.println("Call to method buildString took " +
 (System.currentTimeMillis()-start) + " ms.");
return result;
}
clas.getClassLoader().getClass().getName()=javassist.Loader
Call to method buildString took 15 ms.
Constructed string of length 1000
Call to method buildString took 0 ms.
Constructed string of length 2000
Call to method buildString took 47 ms.
Constructed string of length 4000
Call to method buildString took 125 ms.
Constructed string of length 8000
Call to method buildString took 547 ms.
Constructed string of length 16000

*/

