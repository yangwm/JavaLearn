/**
 * 
 */
package tune.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author yangwm Aug 22, 2010 12:02:41 AM
 */
public class TenuringThresholdDemo {
    
    public static void main(String[] args) throws Exception {
        System.out.println("wait jstat");
        Thread.sleep(10000);
        List<DataObject> objects = new ArrayList<DataObject>();
        for (int i = 0; i < 51200; i++) {
            objects.add(new DataObject(1));
        }
        List<DataObject> tmpobjects = new ArrayList<DataObject>();
        for (int i = 0; i < 10240; i++) {
            tmpobjects.add(new DataObject(1));
        }
        System.gc();
        
        Thread.sleep(1000);
        tmpobjects.size();
        tmpobjects = null;
        long beginTime = System.currentTimeMillis();
        for (int i = 0; i < 30; i++) {
            DataObject toOldObject = new DataObject(1024);
            for (int j = 0; j < 16; j++) {
                for (int m = 0; m < 23; m++) {
                    new DataObject(1024);
                }
            }
            toOldObject.toString();
            toOldObject = null;
        }
        objects.size();
        long endTime = System.currentTimeMillis();
        System.out.println("Execute Summary: Execute Time( " + (endTime - beginTime) + "ms )");
        Thread.sleep(10000);
    }

}

class DataObject {

    byte[] bytes = null;

    public DataObject(int factor) {
        // create object in kb
        bytes = new byte[factor * 1024];
    }

}

/*
合理设置新生代存活周期
-Xms150M -Xmx150M -Xmn30M -XX:+UseSerialGC : 
wait jstat
Execute Summary: Execute Time( 9578ms )

-Xms150M -Xmx150M -Xmn30M -XX:+UseSerialGC -XX:MaxTenuringThreshold=16 :
wait jstat
Execute Summary: Execute Time( 9453ms )

*/
