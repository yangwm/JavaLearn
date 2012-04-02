/**
 * 
 */
package jthread;

/**
 * 通过使用同步来保护对共享变量的访问，及时看到对共享数据的更改。 （实现典型互斥锁）
 * 每个 Java 对象都可以充当锁，synchronized 块可以确保一次只有一个线程执行由给定锁保护的 synchronized 代码。 
 * 
 * 以下示例代码将打印“1 0”或“0 1”。如果没有同步，它还会打印“1 1”或“0 0”
 * 
 * @author yangwm in Nov 20, 2009 10:59:57 AM
 */
public class SyncExample {
    private static int x;
    private static int y;

    private static Object lockObject = new Object();
    private static class Thread1 extends Thread { 
      public void run() { 
        synchronized (lockObject) {
          x = y = 0;
          System.out.print(x);
        }
      }
    }

    private static class Thread2 extends Thread { 
      public void run() { 
        synchronized (lockObject) {
          x = y = 1;
          System.out.print(y);
        }
      }
    }

    public static void main(String[] args) {
        new Thread1().start();
        new Thread2().start();
    }

}

/*
01
*/

