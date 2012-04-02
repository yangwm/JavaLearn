 /**
 * 
 */
package jvm.base.classload;

import java.lang.reflect.Method;

/**
 * 清单 1. 演示类加载器的树状组织结构 
 * 
 */
public class ClassLoaderTree { 

    public static void main(String[] args) { 
        ClassLoader loader = ClassLoaderTree.class.getClassLoader(); 
        while (loader != null) { 
            System.out.println(loader.toString()); 
            loader = loader.getParent(); 
        } 
    } 
 }
/*
yangwm@yangwuming:/media/main/study/tempProjects/yangwmProject/JavaLearn/base.src$ javac jvm/base/classload/ClassLoaderTree.java 
yangwm@yangwuming:/media/main/study/tempProjects/yangwmProject/JavaLearn/base.src$ java jvm.base.classload.ClassLoaderTree
sun.misc.Launcher$AppClassLoader@cac268
sun.misc.Launcher$ExtClassLoader@1a16869

*/


