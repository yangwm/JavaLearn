/**
 * 
 */
package tune.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author yangwm Aug 22, 2010 12:22:57 AM
 */
public class GCPolicyDemo {
    
    public static void main(String[] args) throws Exception {
        System.out.println("ready to start");
        Thread.sleep(10000);
        List<GCPolicyDataObject> cacheObjects = new ArrayList<GCPolicyDataObject>();
        for (int i = 0; i < 2048; i++) {
            cacheObjects.add(new GCPolicyDataObject(100));
        }
        System.gc();
        Thread.sleep(1000);
        for (int i = 0; i < 10; i++) {
            System.out.println("Round: " + (i + 1));
            for (int j = 0; j < 5; j++) {
                System.out.println("put 64M objects");
                List<GCPolicyDataObject> tmpObjects = new ArrayList<GCPolicyDataObject>();
                for (int m = 0; m < 1024; m++) {
                    tmpObjects.add(new GCPolicyDataObject(64));
                }
                tmpObjects = null;
            }
        }
        cacheObjects.size();
        cacheObjects = null;
        Thread.sleep(1000);
    }
    
}

class GCPolicyDataObject {

    byte[] bytes = null;

    GCPolicyRefObject object = null;

    public GCPolicyDataObject(int factor) {
        // create object in kb
        bytes = new byte[factor * 1024];
        object = new GCPolicyRefObject();
    }

}

class GCPolicyRefObject {

    GCPolicyRefChildObject object;

    public GCPolicyRefObject() {
        object = new GCPolicyRefChildObject();
    }

}

class GCPolicyRefChildObject {

    public GCPolicyRefChildObject() {
        ;
    }

}

/*
-Xms680M -Xmx680M -Xmn80M -XX:+UseSerialGC -XX:+PrintGCApplicationStoppedTime

-Xms680M -Xmx680M -Xmn80M -XX:+UseParallelGC -XX:+PrintGCApplicationStoppedTime

-Xms680M -Xmx680M -Xmn80M -XX:+UseConcMarkSweepGC -XX:+UseCMSCompactAtFullCollection -XX:CMSMaxAbortablePrecleanTime=5 -XX:+PrintGCApplicationStoppedTime

*/
