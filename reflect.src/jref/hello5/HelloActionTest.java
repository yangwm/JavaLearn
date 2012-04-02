/**
 * 
 */
package jref.hello5;

/**
 * @author yangwm in Nov 29, 2009 8:52:13 PM
 */
public class HelloActionTest {

    /**
     * create by yangwm in Nov 29, 2009 8:52:13 PM
     * @param args
     */
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory("objects.xml");
        HelloAction helloAction = (HelloAction)beanFactory.getBean("HelloAction");
        helloAction.execute("test ioc");
    }

}

/*
test ioc, welcome to jiangxi fuzhou
*/

