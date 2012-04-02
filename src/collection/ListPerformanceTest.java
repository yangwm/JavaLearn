/**
 * 
 */
package collection;

import java.util.ArrayList;
import java.util.List;

/**
 * list performance test (single operation vs batch operation)
 * 
 * @author yangwm Oct 11, 2010 11:06:40 AM
 */
public class ListPerformanceTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        /*
         * init data
         */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            sb.append("abcdefg");
        }
        List<Message> list = new ArrayList<Message>();
        for (int i = 0; i < 10000; i++) {
            Message message = new Message();
            message.setId(i);
            message.setGlobalId(i);
            message.setContent(sb.toString());
            list.add(message);
        }

        /*
         * single operation
         */
        long begin = System.nanoTime();
        ArrayList<Message> testList2 = new ArrayList<Message>();
        for (int i = 5; i < list.size(); i++) {
            testList2.add(list.get(i));
        }
        long end = System.nanoTime();
        System.out.println("single operation consume " + (end - begin) + " ns ");
        //System.out.println(testList2);

        /*
         * batch operation
         */
        begin = System.nanoTime();
        List<Message> testList3 = new ArrayList<Message>();
        testList3.addAll(list.subList(5, list.size()));
        end = System.nanoTime();
        System.out.println(" batch operation consume " + (end - begin) + " ns ");
        //System.out.println(testList3);

    }

}
/*
single operation consume 2161168 ns 
 batch operation consume 3636775 ns 
 
*/

class Message {
    private long id;
    private long globalId;
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getGlobalId() {
        return globalId;
    }

    public void setGlobalId(long globalId) {
        this.globalId = globalId;
    }

}
