/**
 * 
 */
package nio;

import java.nio.ByteBuffer;

/**
 * @author yangwm in Sep 25, 2009 1:29:38 PM
 */
public class ByteBufferTest {

    /**
     * create by yangwm in Sep 25, 2009 1:29:38 PM
     * @param args
     */
    public static void main(String[] args) {
        ByteBuffer bb=ByteBuffer.allocate(10);
        for(int i=1; i<=8; i++){
            bb.put((byte)i);
        }
        
        System.out.println("pos:"+bb.position());
        System.out.println("limit:"+bb.limit());
        System.out.println("cap:"+bb.capacity());
        
        bb.flip();
        System.out.println("\nafter flip"); 
        System.out.println("pos:"+bb.position());
        System.out.println("limit:"+bb.limit());
        System.out.println("cap:"+bb.capacity());
        
          
        bb.mark(); 
        bb.position(5);
        System.out.println("\nafter mark"); 
        System.out.println("pos:"+bb.position());
        System.out.println("limit:"+bb.limit());
        System.out.println("cap:"+bb.capacity());
        
        
        bb.reset();
        System.out.println("\nafter reset"); 
        System.out.println("pos:"+bb.position());
        System.out.println("limit:"+bb.limit());
        System.out.println("cap:"+bb.capacity());
        
        bb.position(5);
        bb.clear();
        System.out.println("\nafter clear"); 
        System.out.println("pos:"+bb.position());
        System.out.println("limit:"+bb.limit());
        System.out.println("cap:"+bb.capacity()); 
        
        bb.limit(1); 
        bb.put((byte)9);
        bb.put((byte)10);//超出limit范围，抛出java.nio.BufferOverflowException异常
        bb.put((byte)11);
    }

}

/*
pos:8
limit:10
cap:10

after flip
pos:0
limit:8
cap:10

after mark
pos:5
limit:8
cap:10

after reset
pos:0
limit:8
cap:10

after clear
pos:0
limit:10
cap:10
Exception in thread "main" java.nio.BufferOverflowException
    at java.nio.Buffer.nextPutIndex(Buffer.java:495)
    at java.nio.HeapByteBuffer.put(HeapByteBuffer.java:145)
    at nio.ByteBufferTest.main(ByteBufferTest.java:57)
*/

