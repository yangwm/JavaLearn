/**
 * 
 */
package jvm.base.bytecode;

/**
 * JDK自带的javap.exe文件可以反汇编Bytecode
 * 
 * 这些指令又称为opcode，Java一直以来只有约202個Opcode，具体请参考Java Bytecode规范。
 * @author yangwm Apr 17, 2010 2:31:34 PM
 */
public class BytecodeTest {
    public static void main(String[] args) {
        int i = 10000;
        System.out.println("Hello Bytecode! Number = " + i);
    }
}

/*
D:\study\tempProject\JavaLearn\classes>javap -c jvm.base.bytecode.BytecodeTest
Compiled from "BytecodeTest.java"
public class jvm.base.bytecode.BytecodeTest extends java.lang.Object{
public jvm.bytecode.BytecodeTest();
  Code:
   0:   aload_0
   1:   invokespecial   #8; //Method java/lang/Object."<init>":()V
   4:   return

public static void main(java.lang.String[]);
  Code:
   0:   sipush  10000
   3:   istore_1
   4:   getstatic       #16; //Field java/lang/System.out:Ljava/io/PrintStream;
   7:   new     #22; //class java/lang/StringBuilder
   10:  dup
   11:  ldc     #24; //String Hello Bytecode! Number =
   13:  invokespecial   #26; //Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V
   16:  iload_1
   17:  invokevirtual   #29; //Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   20:  invokevirtual   #33; //Method java/lang/StringBuilder.toString:()Ljava/lang/String;
   23:  invokevirtual   #37; //Method java/io/PrintStream.println:(Ljava/lang/String;)V
   26:  return

}

*/
