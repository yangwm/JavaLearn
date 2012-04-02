/**
 * 
 */
package guice.print;

/**
 * @author yangwm in Jan 3, 2010 8:55:39 PM
 */
public class PrintServiceImpl implements PrintService {

    /* (non-Javadoc)
     * @see print.PrintService#print(java.lang.String)
     */
    @Override
    public void print(String str) {
        System.out.print(str);
    }

}
