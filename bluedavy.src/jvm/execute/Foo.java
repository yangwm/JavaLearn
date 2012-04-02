/**
 * 
 */
package jvm.execute;

/**
 * 
 * 
 * @author yangwm Aug 1, 2010 10:34:52 PM
 */
public class Foo {
    private static final int MAX_COUNT = 1000;
    private static int count = 0;
    
    public int bar() throws Exception{
        if (++count >= MAX_COUNT) {
            count = 0;
            throw new Exception("count overflow");
        }
        return count;
    }
}

/*
D:\study\tempProject\JavaLearn\bluedavy.src>javac -g jvm/execute/Foo.java

D:\study\tempProject\JavaLearn\bluedavy.src>javap -c -s -l -verbose jvm.execute.Foo
Classfile /D:/study/tempProject/JavaLearn/bluedavy.src/jvm/execute/Foo.class
  Last modified Aug 7, 2010; size 631 bytes
  MD5 checksum 389318592ea0af5d58123cbdbd547c6f
  Compiled from "Foo.java"
public class jvm.execute.Foo extends java.lang.Object
  SourceFile: "Foo.java"
  minor version: 0
  major version: 51
  flags: ACC_PUBLIC, ACC_SUPER
Constant pool:
   #1 = Methodref          #7.#27         //  java/lang/Object."<init>":()V
   #2 = Fieldref           #6.#28         //  jvm/execute/Foo.count:I
   #3 = Class              #29            //  java/lang/Exception
   #4 = String             #30            //  count overflow
   #5 = Methodref          #3.#31         //  java/lang/Exception."<init>":(Ljava/lang/String;)V
   #6 = Class              #32            //  jvm/execute/Foo
   #7 = Class              #33            //  java/lang/Object
   #8 = Utf8               MAX_COUNT
   #9 = Utf8               I
  #10 = Utf8               ConstantValue
  #11 = Integer            1000
  #12 = Utf8               count
  #13 = Utf8               <init>
  #14 = Utf8               ()V
  #15 = Utf8               Code
  #16 = Utf8               LineNumberTable
  #17 = Utf8               LocalVariableTable
  #18 = Utf8               this
  #19 = Utf8               Ljvm/execute/Foo;
  #20 = Utf8               bar
  #21 = Utf8               ()I
  #22 = Utf8               StackMapTable
  #23 = Utf8               Exceptions
  #24 = Utf8               <clinit>
  #25 = Utf8               SourceFile
  #26 = Utf8               Foo.java
  #27 = NameAndType        #13:#14        //  "<init>":()V
  #28 = NameAndType        #12:#9         //  count:I
  #29 = Utf8               java/lang/Exception
  #30 = Utf8               count overflow
  #31 = NameAndType        #13:#34        //  "<init>":(Ljava/lang/String;)V
  #32 = Utf8               jvm/execute/Foo
  #33 = Utf8               java/lang/Object
  #34 = Utf8               (Ljava/lang/String;)V
{
  public jvm.execute.Foo();
    Signature: ()V
    flags: ACC_PUBLIC
    LineNumberTable:
      line 11: 0
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
             0       5     0  this   Ljvm/execute/Foo;
    Code:
      stack=1, locals=1, args_size=1
         0: aload_0
         1: invokespecial #1                  // Method java/lang/Object."<init>":()V
         4: return
      LineNumberTable:
        line 11: 0
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
               0       5     0  this   Ljvm/execute/Foo;

  public int bar() throws java.lang.Exception;
    Signature: ()I
    flags: ACC_PUBLIC
    LineNumberTable:
      line 16: 0
      line 17: 15
      line 18: 19
      line 20: 29
    LocalVariableTable:
      Start  Length  Slot  Name   Signature
             0      33     0  this   Ljvm/execute/Foo;
    Code:
      stack=3, locals=1, args_size=1
         0: getstatic     #2                  // Field count:I
         3: iconst_1
         4: iadd
         5: dup
         6: putstatic     #2                  // Field count:I
         9: sipush        1000
        12: if_icmplt     29
        15: iconst_0
        16: putstatic     #2                  // Field count:I
        19: new           #3                  // class java/lang/Exception
        22: dup
        23: ldc           #4                  // String count overflow
        25: invokespecial #5                  // Method java/lang/Exception."<init>":(Ljava/lang/Str
ing;)V
        28: athrow
        29: getstatic     #2                  // Field count:I
        32: ireturn
      LineNumberTable:
        line 16: 0
        line 17: 15
        line 18: 19
        line 20: 29
      LocalVariableTable:
        Start  Length  Slot  Name   Signature
               0      33     0  this   Ljvm/execute/Foo;
      StackMapTable: number_of_entries = 1
           frame_type = 29 /* same * /

    Exceptions:
      throws java.lang.Exception

  static {};
    Signature: ()V
    flags: ACC_STATIC
    LineNumberTable:
      line 13: 0
    Code:
      stack=1, locals=0, args_size=0
         0: iconst_0
         1: putstatic     #2                  // Field count:I
         4: return
      LineNumberTable:
        line 13: 0
}

D:\study\tempProject\JavaLearn\bluedavy.src>

*/
