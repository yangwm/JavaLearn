/**
 * 
 */
package jvm.base.cls;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义文件类加载器 
 * 
 * @author yangwm Apr 17, 2010 1:51:46 PM
 */
public class FileClassLoader extends ClassLoader {
    
    public Class<?> findClass(String name) {
        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        FileInputStream fis = null;
        byte[] data = null;
        try {
            fis = new FileInputStream(new File("classes/" + name + ".class"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int ch = 0;
            while ((ch = fis.read()) != -1) {
                baos.write(ch);
            }
            data = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
