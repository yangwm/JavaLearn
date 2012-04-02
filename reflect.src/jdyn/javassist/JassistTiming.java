/**
 * 
 */
package jdyn.javassist;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

/**
 * 
 * 
    
    清单 3. 在源代码中添加一个拦截器方法

    
    private String buildString$impl(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += (char)(i%26 + 'a');
        }
        return result;
    }
    private String buildString(int length) {
        long start = System.currentTimeMillis();
        String result = buildString$impl(length);
        System.out.println("Call to buildString took " +
            (System.currentTimeMillis()-start) + " ms.");
        return result;
    }
    
 * 
 * 
 * @author yangwm in Dec 7, 2009 5:24:18 PM
 */
public class JassistTiming {
    
    public static void main(String[] argv) {
        if (argv.length == 2) {
            try {
                
                // start by getting the class file and method
                CtClass clas = ClassPool.getDefault().get(argv[0]);
                if (clas == null) {
                    System.err.println("Class " + argv[0] + " not found");
                } else {
                    
                    // add timing interceptor to the class
                    addTiming(clas, argv[1]);
                    clas.writeFile();
                    System.out.println("Added timing to method " +
                        argv[0] + "." + argv[1]);
                    
                }
                
            } catch (CannotCompileException ex) {
                ex.printStackTrace();
            } catch (NotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        } else {
            System.out.println("Usage: jdyn.javassist.JassistTiming class method-name");
        }
    }
    
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
    
}

/*
D:\study\tempProject\JavaLearn\classes>java jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
Constructed string of length 1000
Constructed string of length 2000
Constructed string of length 4000
Constructed string of length 8000
Constructed string of length 16000

D:\study\tempProject\JavaLearn\classes>java -classpath ".;..\lib\javassist-1.1.jar" jdyn.javassist.JassistTiming jdyn.javassist.StringBuilder buildString
Interceptor method body:
{
long start = System.currentTimeMillis();
java.lang.String result = buildString$impl($$);
System.out.println("Call to method buildString took " +
 (System.currentTimeMillis()-start) + " ms.");
return result;
}
Added timing to method jdyn.javassist.StringBuilder.buildString

D:\study\tempProject\JavaLearn\classes>java jdyn.javassist.StringBuilder 1000 2000 4000 8000 16000
Call to method buildString took 0 ms.
Constructed string of length 1000
Call to method buildString took 15 ms.
Constructed string of length 2000
Call to method buildString took 32 ms.
Constructed string of length 4000
Call to method buildString took 125 ms.
Constructed string of length 8000
Call to method buildString took 500 ms.
Constructed string of length 16000

D:\study\tempProject\JavaLearn\classes>

*/

