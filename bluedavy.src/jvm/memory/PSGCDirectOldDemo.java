
package jvm.memory;

public class PSGCDirectOldDemo{

    public static void main(String[] args) throws Exception{
        byte[] bytes=new byte[1024*1024*2];
        byte[] bytes2=new byte[1024*1024*2];
        byte[] bytes3=new byte[1024*1024*2];
        System.out.println("ready to direct allocate to old");
        Thread.sleep(3000);
        byte[] bytes4=new byte[1024*1024*2];
        Thread.sleep(30000);
    }

}

/*
-Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+UseParallelGC

ready to direct allocate to old
Turning off use of shared archive because of choice of garbage collector or large pages 

*/
