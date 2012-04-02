package lang;
/**
 * 
 */


/**
 * 
 * @author yangwm Jul 13, 2011 11:44:02 AM
 */
public class Oo {

    /**
     * @param args
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("args error, must be java uid inserti");
            return ;
        }
        long uid = Long.valueOf(args[0]); // 1750715731L 
        int inserti = Integer.valueOf(args[1]);
        System.out.println("uid:" + uid + ", inserti:" + inserti);
    }

}
