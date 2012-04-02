/**
 * 
 */
package strings;

/**
 * String Bench
 * 
 * @author yangwm Apr 19, 2011 1:53:24 PM
 */
public class StringBench {
    
    private static final long loops = 10 * 1000 * 1000;

    public static void main(String[] args) throws Exception {
        testStringBuilder();
        testString();
        
        long beginTime = System.currentTimeMillis();
        testStringBuilder();
        long endTime = System.currentTimeMillis();
        System.out.println("testStringBuilder loops: " + loops + ", summary cosume: " + (endTime - beginTime) + " ms");
        
        beginTime = System.currentTimeMillis();
        testString();
        endTime = System.currentTimeMillis();
        System.out.println("testString loops: " + loops + ", summary cosume: " + (endTime - beginTime) + " ms");
    }

    public static void testStringBuilder() throws Exception {
        int v = 0;
        for (int i = 0; i < loops; i++) {
            StringBuilder sb = new StringBuilder(64);
            sb.append("get some value from mc key1=").append(i).append(",key2=").append(i + 1);
            v += sb.toString().length();
        }
        System.out.println("testStringBuilder v: " + v);
    }
    
    public static void testString() throws Exception {
        int v = 0;
        for (int i = 0; i < loops; i++) {
            String str = "get some value from mc key1=" + i + ",key2=" + (i + 1);
            v += str.length();
        }
        System.out.println("testString v: " + v);
    }
    
}

/*
D:\study\tempProjects\yangwmProject\JavaLearn\classes>java strings.StringBench 
testStringBuilder v: 477777787
testString v: 477777787
testStringBuilder v: 477777787
testStringBuilder loops: 10000000, summary cosume: 2938 ms
testStringBuilder v: 477777787
testString loops: 10000000, summary cosume: 2891 ms


D:\study\tempProjects\yangwmProject\JavaLearn\classes>javap -c strings.StringBench
Compiled from "StringBench.java"
public class strings.StringBench extends java.lang.Object{
public strings.StringBench();
  Code:
   0:   aload_0
   1:   invokespecial   #13; //Method java/lang/Object."<init>":()V
   4:   return

public static void main(java.lang.String[])   throws java.lang.Exception;
  Code:
   0:   invokestatic    #24; //Method testStringBuilder:()V
   3:   invokestatic    #27; //Method testString:()V
   6:   invokestatic    #30; //Method java/lang/System.currentTimeMillis:()J
   9:   lstore_1
   10:  invokestatic    #24; //Method testStringBuilder:()V
   13:  invokestatic    #30; //Method java/lang/System.currentTimeMillis:()J
   16:  lstore_3
   17:  getstatic       #36; //Field java/lang/System.out:Ljava/io/PrintStream;
   20:  new     #40; //class java/lang/StringBuilder
   23:  dup
   24:  ldc     #42; //String testStringBuilder loops: 10000000, summary cosume:
   26:  invokespecial   #44; //Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V
   29:  lload_3
   30:  lload_1
   31:  lsub
   32:  invokevirtual   #47; //Method java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
   35:  ldc     #51; //String  ms
   37:  invokevirtual   #53; //Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   40:  invokevirtual   #56; //Method java/lang/StringBuilder.toString:()Ljava/lang/String;
   43:  invokevirtual   #60; //Method java/io/PrintStream.println:(Ljava/lang/String;)V
   46:  invokestatic    #30; //Method java/lang/System.currentTimeMillis:()J
   49:  lstore_1
   50:  invokestatic    #24; //Method testStringBuilder:()V
   53:  invokestatic    #30; //Method java/lang/System.currentTimeMillis:()J
   56:  lstore_3
   57:  getstatic       #36; //Field java/lang/System.out:Ljava/io/PrintStream;
   60:  new     #40; //class java/lang/StringBuilder
   63:  dup
   64:  ldc     #65; //String testString loops: 10000000, summary cosume:
   66:  invokespecial   #44; //Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V
   69:  lload_3
   70:  lload_1
   71:  lsub
   72:  invokevirtual   #47; //Method java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
   75:  ldc     #51; //String  ms
   77:  invokevirtual   #53; //Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   80:  invokevirtual   #56; //Method java/lang/StringBuilder.toString:()Ljava/lang/String;
   83:  invokevirtual   #60; //Method java/io/PrintStream.println:(Ljava/lang/String;)V
   86:  return

public static void testStringBuilder()   throws java.lang.Exception;
  Code:
   0:   iconst_0
   1:   istore_0
   2:   iconst_0
   3:   istore_1
   4:   goto    52
   7:   new     #40; //class java/lang/StringBuilder
   10:  dup
   11:  bipush  64
   13:  invokespecial   #71; //Method java/lang/StringBuilder."<init>":(I)V
   16:  astore_2
   17:  aload_2
   18:  ldc     #74; //String get some value from mc key1=
   20:  invokevirtual   #53; //Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   23:  iload_1
   24:  invokevirtual   #76; //Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   27:  ldc     #79; //String ,key2=
   29:  invokevirtual   #53; //Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   32:  iload_1
   33:  iconst_1
   34:  iadd
   35:  invokevirtual   #76; //Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   38:  pop
   39:  iload_0
   40:  aload_2
   41:  invokevirtual   #56; //Method java/lang/StringBuilder.toString:()Ljava/lang/String;
   44:  invokevirtual   #81; //Method java/lang/String.length:()I
   47:  iadd
   48:  istore_0
   49:  iinc    1, 1
   52:  iload_1
   53:  i2l
   54:  ldc2_w  #8; //long 10000000l
   57:  lcmp
   58:  iflt    7
   61:  getstatic       #36; //Field java/lang/System.out:Ljava/io/PrintStream;
   64:  new     #40; //class java/lang/StringBuilder
   67:  dup
   68:  ldc     #87; //String testStringBuilder v:
   70:  invokespecial   #44; //Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V
   73:  iload_0
   74:  invokevirtual   #76; //Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   77:  invokevirtual   #56; //Method java/lang/StringBuilder.toString:()Ljava/lang/String;
   80:  invokevirtual   #60; //Method java/io/PrintStream.println:(Ljava/lang/String;)V
   83:  return

public static void testString()   throws java.lang.Exception;
  Code:
   0:   iconst_0
   1:   istore_0
   2:   iconst_0
   3:   istore_1
   4:   goto    45
   7:   new     #40; //class java/lang/StringBuilder
   10:  dup
   11:  ldc     #74; //String get some value from mc key1=
   13:  invokespecial   #44; //Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V
   16:  iload_1
   17:  invokevirtual   #76; //Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   20:  ldc     #79; //String ,key2=
   22:  invokevirtual   #53; //Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
   25:  iload_1
   26:  iconst_1
   27:  iadd
   28:  invokevirtual   #76; //Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   31:  invokevirtual   #56; //Method java/lang/StringBuilder.toString:()Ljava/lang/String;
   34:  astore_2
   35:  iload_0
   36:  aload_2
   37:  invokevirtual   #81; //Method java/lang/String.length:()I
   40:  iadd
   41:  istore_0
   42:  iinc    1, 1
   45:  iload_1
   46:  i2l
   47:  ldc2_w  #8; //long 10000000l
   50:  lcmp
   51:  iflt    7
   54:  getstatic       #36; //Field java/lang/System.out:Ljava/io/PrintStream;
   57:  new     #40; //class java/lang/StringBuilder
   60:  dup
   61:  ldc     #95; //String testString v:
   63:  invokespecial   #44; //Method java/lang/StringBuilder."<init>":(Ljava/lang/String;)V
   66:  iload_0
   67:  invokevirtual   #76; //Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
   70:  invokevirtual   #56; //Method java/lang/StringBuilder.toString:()Ljava/lang/String;
   73:  invokevirtual   #60; //Method java/io/PrintStream.println:(Ljava/lang/String;)V
   76:  return

*/

