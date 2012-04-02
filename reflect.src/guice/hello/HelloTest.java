/**
 * 
 */
package guice.hello;

import com.google.inject.Guice;
import com.google.inject.Injector;

/**
 * @author yangwm in Jan 3, 2010 11:07:32 PM
 */
public class HelloTest {

    /**
     * create by yangwm in Jan 3, 2010 11:07:32 PM
     * @param args
     */
    public static void main(String[] args) {
        Injector inject = Guice.createInjector();
        for (int i = 0; i < 3; i++) {
            Hello hello = inject.getInstance(Hello.class);
            System.out.println("hello(" + i + "):" + hello);
            hello.sayHello("yangwm");
        }
    }

}

/*
1. 实现类未使用注释@Singleton作用域 
hello(0):guice.hello.HelloImpl@1f934ad
Hello: yangwm
hello(1):guice.hello.HelloImpl@1f14ceb
Hello: yangwm
hello(2):guice.hello.HelloImpl@f0eed6
Hello: yangwm

2. 实现类使用注释@Singleton作用域 
hello(0):guice.hello.HelloImpl@18020cc
Hello: yangwm
hello(1):guice.hello.HelloImpl@18020cc
Hello: yangwm
hello(2):guice.hello.HelloImpl@18020cc
Hello: yangwm

*/
