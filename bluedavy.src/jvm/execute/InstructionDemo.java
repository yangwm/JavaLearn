/**
 * 
 */
package jvm.execute;

/**
 * 
 * 
 * @author yangwm Aug 1, 2010 10:38:26 PM
 */
public class InstructionDemo {
    public void execute() {
        A.execute();
        A a = new A();
        a.bar();
        IFoo b = new B();
        b.bar();
    }
}

class A {
    public static int execute() {
        return 1 + 2;
    }

    public int bar() {
        return 1 + 2;
    }
}

class B implements IFoo {
    public int bar() {
        return 1 + 2;
    }
}

/*
D:\study\tempProject\JavaLearn\bluedavy.src>javac jvm/execute/InstructionDemo.java

D:\study\tempProject\JavaLearn\bluedavy.src>javap -c jvm.execute.InstructionDemo
Compiled from "InstructionDemo.java"
public class jvm.execute.InstructionDemo extends java.lang.Object {
  public jvm.execute.InstructionDemo();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public void execute();
    Code:
       0: invokestatic  #2                  // Method jvm/execute/A.execute:()I
       3: pop
       4: new           #3                  // class jvm/execute/A
       7: dup
       8: invokespecial #4                  // Method jvm/execute/A."<init>":()V
      11: astore_1
      12: aload_1
      13: invokevirtual #5                  // Method jvm/execute/A.bar:()I
      16: pop
      17: new           #6                  // class jvm/execute/B
      20: dup
      21: invokespecial #7                  // Method jvm/execute/B."<init>":()V
      24: astore_2
      25: aload_2
      26: invokeinterface #8,  1            // InterfaceMethod jvm/execute/IFoo.bar:()I
      31: pop
      32: return
}

D:\study\tempProject\JavaLearn\bluedavy.src>

*/

