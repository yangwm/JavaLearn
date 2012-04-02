/**
 * 
 */
package jref.hello1;

/**
 * @author yangwm in Nov 29, 2009 8:49:37 PM
 */
public class HelloAction {
    
    public String getMessage() {
        return "welcome yangwm's homepage.";
    }
    
    public void execute(String str) {
        System.out.println(str + ", " + getMessage());
    }
}
