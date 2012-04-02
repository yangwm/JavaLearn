/**
 * 
 */
package jref.hello5;

/**
 * @author yangwm in Nov 29, 2009 8:49:37 PM
 */
public class HelloAction {
    
    private MessageProvider messageProvider;
    
    public String getMessage() {
        return messageProvider.getMessage();
    }
    
    public void execute(String str) {
        System.out.println(str + ", " + getMessage());
    }

    public MessageProvider getMessageProvider() {
        return messageProvider;
    }

    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }
    
    
    
}
