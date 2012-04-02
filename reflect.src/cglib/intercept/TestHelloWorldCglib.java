/**
 * 
 */
package cglib.intercept;

import net.sf.cglib.proxy.Enhancer;

/**
 * CgLib就可以不用接口，它底层调用asm动态生成一个代理类去覆盖父类中非final的方法，
 * 然后实现MethodInterceptor接口的intercept方法，这样以后直接调用重写的方法，比JDK要快。
 * 但是加载cglib消耗时间比直接jdk反射时间长，开发的过程中，如果是反复动态生成新的代理类推荐用jdk自身的反射，反之用cglib.
 * 
 * @author yangwm Apr 15, 2010 6:19:49 PM
 */
public class TestHelloWorldCglib {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloWorld.class);
        enhancer.setCallback(new HelloWorldCglibProxy());
        
        HelloWorld helloWorld = (HelloWorld) enhancer.create();
        helloWorld.say();
        
    }

}
/*
net.sf.cglib.proxy.Enhancer$EnhancerKey$$KeyFactoryByCGLIB$$7fb24d72
net.sf.cglib.core.MethodWrapper$MethodWrapperKey$$KeyFactoryByCGLIB$$d45e49f7
cglib.intercept.HelloWorld$$EnhancerByCGLIB$$dd17dfd5
>>>intercept say
cglib.intercept.HelloWorld$$FastClassByCGLIB$$ae46c2d9
cglib.intercept.HelloWorld$$EnhancerByCGLIB$$dd17dfd5$$FastClassByCGLIB$$e092c346
Hello World
<<<intercept say

*/
