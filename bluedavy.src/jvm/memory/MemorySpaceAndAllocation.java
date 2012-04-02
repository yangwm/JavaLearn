/**
 * 
 */
package jvm.memory;

public class MemorySpaceAndAllocation{
    public static void main(String[] args) throws Exception{ 
        Thread.sleep(2000);
        // should allocate from TLAB
        MemoryObject object=new MemoryObject(new byte[1024]);
        Thread.sleep(2000);
        // should allocate from Eden space,not TLAB
        MemoryObject newObject=new MemoryObject(new byte[1024*1024*4]);
        Thread.sleep(2000);
        System.gc();
        Thread.sleep(2000);
        object=new MemoryObject(new byte[1024]);
        Thread.sleep(2000);
        // should allocate from Eden space,not TLAB
        newObject=new MemoryObject(new byte[1024*1024*4]);
        Thread.sleep(2000);
        System.gc();
        Thread.sleep(2000);
    }
}

