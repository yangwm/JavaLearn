package lang;

/**
 * 
 */

import lombok.Data;

/**
 * 
 * 
 * @author yangwm May 18, 2010 1:09:32 PM
 */
public @Data class LombokDemo {
    long id;
    String name;
}

/*
D:\study\tempProject\JavaLearn>set path="D:\Program Files\Java\jdk1.6.0_13\bin"

D:\study\tempProject\JavaLearn>javac -version
javac 1.6.0_13

D:\study\tempProject\JavaLearn>javac -d classes -sourcepath src -classpath ".;lib/lombok.jar" src/Lo
mbokDemo.java

D:\study\tempProject\JavaLearn>javap classes/LombokDemo
Compiled from "LombokDemo.java"
public class LombokDemo extends java.lang.Object{
    long id;
    java.lang.String name;
    public LombokDemo();
    public long getId();
    public void setId(long);
    public java.lang.String getName();
    public void setName(java.lang.String);
    public boolean equals(java.lang.Object);
    public int hashCode();
    public java.lang.String toString();
}


D:\study\tempProject\JavaLearn>
*/
