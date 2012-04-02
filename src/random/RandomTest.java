/**
 * 
 */
package random;

/**
 * 
 * 
 * @author yangwm Jan 10, 2011 5:07:35 PM
 */
public class RandomTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        for (int t = 0; t < 1000; t++) {
            int n = (int)(Math.random() * 5);
            int i = 0;
            
            StringBuilder nStr = new StringBuilder("n:").append(n);
            for(i = n; i >= 0; i--){
                nStr.append(", i:").append(i);
                break;
            }
            nStr.append(", i:").append(i);
            System.out.println("nStr=" + nStr.toString());
        }
    }

}
