/**
 * 
 */
package jdk.container;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * 
 * @author yangwm in Mar 26, 2010 5:37:14 PM
 */
public class CollectionPerformanceTest {
    Collection<String> collection=null;
    String findEle="";
    static CountDownLatch endLatch=null;
    static int collectionType;
    static boolean isThreadSafe;
    Random random=new Random();
    
    public static void main(String[] args) throws Exception{
        if(args.length!=1){
            throw new IllegalArgumentException("必须指定集合类型");
        }
        collectionType=Integer.parseInt(args[0]);
        System.out.println("测试的集合类型为："+collectionType);
        isThreadSafe=CollectionUtils.isThreadSafe(collectionType);
        
        // 为了避免由于JIT编译的优化造成影响
        Collection<String> initCollections=CollectionUtils.get(collectionType, 20000);
        for (int i = 0; i < 20000; i++) {
            initCollections.add("bluedavy");
            initCollections.contains("bluedavy");
            initCollections.remove("bluedavy");
        }
        
        CollectionPerformanceTest self=new CollectionPerformanceTest();
        int[] sizes=new int[]{10,100,1000,10000};
        for (int j = 0; j < sizes.length; j++) {
            System.out.println("===size: "+sizes[j]+" threadcount:10===");
            endLatch=new CountDownLatch(100);
            for (int i = 0; i < 10; i++) {
                self.runMultiThreadPerformanceTest(sizes[j], 10);
            }
            endLatch.await();
            System.out.println("===END===");
        }
        
        for (int j = 0; j < sizes.length; j++) {
            System.out.println("===size: "+sizes[j]+" threadcount:50===");
            endLatch=new CountDownLatch(500);
            for (int i = 0; i < 10; i++) {
                self.runMultiThreadPerformanceTest(sizes[j], 50);
            }
            endLatch.await();
            System.out.println("===END===");
        }
        
        for (int j = 0; j < sizes.length; j++) {
            System.out.println("===size: "+sizes[j]+" threadcount:100===");
            endLatch=new CountDownLatch(1000);
            for (int i = 0; i < 10; i++) {
                self.runMultiThreadPerformanceTest(sizes[j], 100);
            }
            endLatch.await();
            System.out.println("===END===");
        }
        
        for (int j = 0; j < sizes.length; j++) {
            for (int i = 0; i < 10; i++) {
                self.runPerformanceTest(sizes[j]);
            }
        }
    }
    
    private void runPerformanceTest(int size){
        collection=CollectionUtils.get(collectionType, size);
        for (int i = 0; i < size; i++) {
            collection.add(String.valueOf(i));
        }
        long beginTime=0L;
        findEle=String.valueOf(size/2);
        System.out.println("===PerformanceTest，Size: "+size+"===");
        
        for (int i = 0; i < 1000; i++) {
            beginTime=System.nanoTime();
            collection.add("TESTELE");
            System.out.println("AddTime:"+(System.nanoTime()-beginTime));
            beginTime=System.nanoTime();
            collection.contains(findEle);
            System.out.println("FindTime:"+(System.nanoTime()-beginTime));
            beginTime=System.nanoTime();
            collection.remove("TESTELE");
            System.out.println("RemoveTime:"+(System.nanoTime()-beginTime));
        }
        System.out.println("===END===");
    }
    
    private void runMultiThreadPerformanceTest(int size,int threadCount){
        findEle=String.valueOf(size/2);
        
        collection=CollectionUtils.get(collectionType, size);
        for (int i = 0; i < size; i++) {
            collection.add(String.valueOf(i));
        }
        
        for (int i = 0; i < threadCount; i++) {
            new Thread(new TestTask()).start();
        }
    }
    
    
    class TestTask implements Runnable{
        public void run() {
            long startTime=System.nanoTime();
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if(isThreadSafe){
                long beginTime=System.nanoTime();
                collection.add("TESTELE"+startTime);
                System.out.println("AddTime:"+(System.nanoTime()-beginTime));
            } else{
                long beginTime=System.nanoTime();
                synchronized (collection) {
                    collection.add("TESTELE"+startTime);
                }
                System.out.println("AddTime:"+(System.nanoTime()-beginTime));
            }
            
            if(isThreadSafe){
                long beginTime=System.nanoTime();
                collection.contains(findEle);
                System.out.println("FindTime:"+(System.nanoTime()-beginTime));
            } else{
                long beginTime=System.nanoTime();
                synchronized (collection) {
                    collection.contains(findEle);
                }
                System.out.println("FindTime:"+(System.nanoTime()-beginTime));
            }
            
            if(isThreadSafe){
                long beginTime=System.nanoTime();
                collection.remove("TESTELE"+startTime);
                System.out.println("RemoveTime:"+(System.nanoTime()-beginTime));
            } else{
                long beginTime=System.nanoTime();
                synchronized (collection) {
                    collection.remove("TESTELE"+startTime);
                }
                System.out.println("RemoveTime:"+(System.nanoTime()-beginTime));
            }
            
            endLatch.countDown();
        }
    }
}
