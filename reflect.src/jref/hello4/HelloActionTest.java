/**
 * 
 */
package jref.hello4;

/**
 * @author yangwm in Nov 29, 2009 8:52:13 PM
 */
public class HelloActionTest {

    /**
     * create by yangwm in Nov 29, 2009 8:52:13 PM
     * @param args
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String className = System.getProperty("MessageProvider");
        
        Class<?> cls = Class.forName(className);
        
        //System.out.println(MessageProvider.class.isAssignableFrom(MessageProviderProp.class));
        //System.out.println(MessageProvider.class.isAssignableFrom(Object.class));
        if (MessageProvider.class.isAssignableFrom(cls)) {
            MessageProvider messageProvider = (MessageProvider)cls.newInstance();
            
            HelloAction helloAction = new HelloAction();
            helloAction.setMessageProvider(messageProvider);
            
            helloAction.execute("baby");
        } else {
            System.out.println("MessageProvider not implements jref.hello4.MessageProvider, " 
                    + "your MessageProvider is " + cls.getName());
        }
    }

}

/*
VM arguments:-DMessageProvider=jref.hello4.MessageProviderProp :
java -DMessageProvider=jref.hello4.MessageProviderProp HelloActionTest
true
false
baby, welcome to jiangxi fuzhou
*/

