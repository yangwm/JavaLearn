/**
 * 
 */
package concurrent.volatiler;

/**
 * 一个使用volatile的好处，得自于其原理：内部禁止改变两个volatile变量的赋值或者初始化顺序，
 * 并且严格限制volatile变量和其周围非volatile变量的赋值或者初始化顺序。 
 * 
 * finished变量不是声明了volatile的话:JVM或许为了优化执行把两者的赋值顺序调换了:
 * 进入doSomething()得到finished的值为true，打印lucky的值为0
 * 
 * @author yangwm Jul 22, 2010 6:23:25 PM
 */
public class VolatileSample {
    public static void main(String[] args) {
        //for (int i = 0; i < 1000; i++) {
            final SubVolatileThread sample = new SubVolatileThread();

            new Thread(new Runnable() {
                public void run() {
                    sample.finish();
                }
            }).start();

            new Thread(new Runnable() {
                public void run() {
                    sample.doSomething();
                }
            }).start();
        //}
    }
}

class SubVolatileThread {
    private volatile boolean finished;
    private int lucky;

    public void doSomething() {
        if (finished/* && lucky == 0*/) {
            System.out.println("lucky: " + lucky);
        }
    }

    public void finish() {
        lucky = 7;
        finished = true;
    }
}