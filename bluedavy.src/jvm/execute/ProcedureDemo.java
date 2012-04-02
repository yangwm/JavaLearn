/**
 * 
 */
package jvm.execute;

/**
 * 
 * 
 * @author yangwm Aug 7, 2010 6:11:50 PM
 */
public class ProcedureDemo {
    public static void foo() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 5;
    }
}

/*
D:\study\tempProject\JavaLearn\bluedavy.src>javac jvm/execute/ProcedureDemo.java

D:\study\tempProject\JavaLearn\bluedavy.src>javap -c jvm.execute.ProcedureDemo
Compiled from "ProcedureDemo.java"
public class jvm.execute.ProcedureDemo extends java.lang.Object {
  public jvm.execute.ProcedureDemo();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void foo();
    Code:
       0: iconst_1
       1: istore_0
       2: iconst_2
       3: istore_1
       4: iload_0
       5: iload_1
       6: iadd
       7: iconst_5
       8: imul
       9: istore_2
      10: return
}

D:\study\tempProject\JavaLearn\bluedavy.src>

*/
