/**
 * 
 */
package jvm.base.compile;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;

/**
 * 
 * 
 * @author yangwm Jan 2, 2011 9:46:19 PM
 */
public class MethodEntryTransformerTest {

    public static void premain(String args, Instrumentation inst) {
        inst.addTransformer(new MethodEntryTransformer());
    }

    static class MethodEntryTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            try {
                ClassReader cr = new ClassReader(classfileBuffer);
                ClassNode cn = new ClassNode();
                // 省略使用ASM进行字节代码转换的代码
                ClassWriter cw = null;//new ClassWriter(0);
                cn.accept(cw);
                return cw.toByteArray();
            } catch (Exception e) {
                return null;
            }
        }
    }

}
