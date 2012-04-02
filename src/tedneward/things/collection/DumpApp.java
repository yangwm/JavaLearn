/**
 * 
 */
package tedneward.things.collection;

/**
 * 
 * 
 * @author yangwm Jul 4, 2010 4:00:08 PM
 */
public class DumpApp {
    
    public static void main(String[] args) throws Exception {
        for (String line : FileUtils.readlines(args[0])) {
            System.out.println(line);
        }
    }

}

/*
java tedneward.things.collection.DumpApp D:\study\tempProject\JavaLearn\appProperties
baba=yangYuePing
mama=zhouZhengLin
jiejie=yangMeiQing

*/

