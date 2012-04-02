/**
 * 
 */
package jdk.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 使用Java自带的序列化，将对象转化为流后进行传输（网络），将流还原为对象（对象需实现Java序列化接口）。
 * 
 * 对象序列化/反序列化：将Java对象转化为字节流，读取字节流还原为Java对象。
 * 
 * @author yangwm Aug 13, 2010 4:16:31 PM
 */
public class AObject implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -2349845809623513122L;
    
    private String a = "java";
    private String b = "yangwm";

    private int i = 100;
    private int j = 10;
    
    private long m = 100L;
    
    private boolean isA = true;
    
    private BObject bojbect = new BObject();
    
    public static void main(String[] args) {
        AObject object = new AObject();
        
        for (int i = 0; i < 10; i++) {
            long beginTime = System.nanoTime();
            
            ObjectOutputStream out = null;
            ObjectInputStream in = null;
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                out = new ObjectOutputStream(bo);
                out.writeObject(object);
                out.flush();
                byte[] bs = bo.toByteArray();
                
                ByteArrayInputStream bi = new ByteArrayInputStream(bs);
                in = new ObjectInputStream(bi);
                in.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            System.out.println("Java serialize comuse time:" + (System.nanoTime() - beginTime) + " ns");
        }
    }

}

/*
Java serialize comuse time:16818618 ns
Java serialize comuse time:608457 ns
Java serialize comuse time:674667 ns
Java serialize comuse time:488330 ns
Java serialize comuse time:458158 ns
Java serialize comuse time:483581 ns
Java serialize comuse time:474362 ns
Java serialize comuse time:477156 ns
Java serialize comuse time:472965 ns
Java serialize comuse time:502298 ns

*/
