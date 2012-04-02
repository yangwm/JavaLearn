/**
 * 
 */
package jthread;

/**
 * 创建并启动两个线程，每个线程都打印两行到 System.out
 * 
 * @author yangwm in Nov 20, 2009 9:57:11 AM
 */
public class TwoThreads {
    
    public static class Thread1 extends Thread {
        public void run() {
            System.out.print("A ");
            System.out.print("B ");
        }
    }

    public static class Thread2 extends Thread {
        public void run() {
            System.out.print("1 ");
            System.out.print("2 ");
        }
    }

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

}

/*
我们并不知道这些行按什么顺序执行，只知道“1”在“2”之前打印，以及“A”在“B”之前打印。输出可能是以下结果中的任何一种：

    * 1 2 A B
    * 1 A 2 B
    * 1 A B 2
    * A 1 2 B
    * A 1 B 2
    * A B 1 2

*/
