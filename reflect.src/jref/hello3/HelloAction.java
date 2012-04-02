/**
 * 
 */
package jref.hello3;

/**
 * @author yangwm in Nov 29, 2009 8:49:37 PM
 */
public class HelloAction {
    
    public String getMessage() {
        //return new MessageProviderProp().getMessage();
        return new MessageProviderXml().getMessage();
    }
    
    public void execute(String str) {
        System.out.println(str + ", " + getMessage());
    }
}
