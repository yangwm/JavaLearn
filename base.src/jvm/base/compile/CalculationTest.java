/**
 * 
 */
package jvm.base.compile;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject.Kind;

import jvm.base.compile.CompilerTest.StringSourceJavaObject;

/**
 * 
 * 
 * @author yangwm Jan 2, 2011 9:39:56 PM
 */
public class CalculationTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    private static double calculate(String expr) throws CalculationException, URISyntaxException  {
        String className = "CalculatorMain";
        String methodName = "calculate";
        String source = "public class " + className 
           + " { public static double " + methodName + "() { return " + expr + "; } }";
           //省略动态编译Java源代码的相关代码，参见上一节
        
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        System.out.println(compiler);
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
        StringSourceJavaObject sourceObject = new CompilerTest.StringSourceJavaObject("Main", source);
        Iterable<? extends JavaFileObject> fileObjects = Arrays.asList(sourceObject);
        CompilationTask task = compiler.getTask(null, fileManager, null, null, null, fileObjects);
        
        boolean result = task.call();
        if (result) {
           ClassLoader loader = Calculator.class.getClassLoader(); 
           try {            
              Class<?> clazz = loader.loadClass(className);
              Method method = clazz.getMethod(methodName, new Class<?>[] {});
              Object value = method.invoke(null, new Object[] {});
              return (Double) value;
           } catch (Exception e) {
              throw new CalculationException("内部错误。");        
           }    
        } else {
           throw new CalculationException("错误的表达式。");    
        }
     }

}
