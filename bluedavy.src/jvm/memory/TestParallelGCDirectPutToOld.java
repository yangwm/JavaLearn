/**
 * 
 */
package jvm.memory;

public class TestParallelGCDirectPutToOld{
     public static void main(String[] args) throws Exception{
          MemoryObject m1=new MemoryObject(1024*1024);
          MemoryObject m2=new MemoryObject(1024*1024);
          MemoryObject m3=new MemoryObject(1024*1024);
          MemoryObject m4=new MemoryObject(1024*1024);
          MemoryObject m5=new MemoryObject(1024*1024);
          MemoryObject m6=new MemoryObject(1024*1024);
          MemoryObject m7=new MemoryObject(1024*1024);
          MemoryObject m8=new MemoryObject(1024*1024);
          MemoryObject m9=new MemoryObject(1024*1024);
          MemoryObject m12=new MemoryObject(1024*256);
          MemoryObject mend=new MemoryObject(1024*1024*4);
          Thread.sleep(10000);
     }
}

