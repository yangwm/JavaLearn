/**
 * 
 */
package jthread;

/**
 * 使用一个线程用于计时，并使用另一个线程完成工作
 * 
 * volatile 对于确保每个线程看到最新的变量值非常有用，volatile 比同步更简单，只适合于控制对基本变量（整数、布尔变量等）的单个实例的访问。
 * 
 * 同步（如 volatile）强制某些可见性约束。当对象获取锁时，它首先使自己的高速缓存无效，这样就可以保证直接从主内存中装入变量。 
 * 当一个变量被声明成 volatile，任何对该变量的写操作都会绕过高速缓存，直接写入主内存，而任何对该变量的读取也都绕过高速缓存，直接取自主内存。
 * 这表示所有线程在任何时候看到的 volatile 变量值都相同。 
 * 
 * CalculatePrimes -- calculate as many primes as we can in ten seconds 
 * 
 * @author yangwm in Nov 20, 2009 9:39:04 AM
 */
public class CalculatePrimes extends Thread {
    
    public static final int MAX_PRIMES = 1000000;
    public static final int TEN_SECONDS = 10000;

    public volatile boolean finished = false;

    public void run() {
        int[] primes = new int[MAX_PRIMES];
        int count = 0;

        for (int i=2; count<MAX_PRIMES; i++) {
            // Check to see if the timer has expired
            if (finished) {
                break;
            }

            boolean prime = true;
            for (int j=0; j<count; j++) {
                if (i % primes[j] == 0) {
                    prime = false;
                    break;
                }
            }

            if (prime) {
                primes[count++] = i;
                System.out.println("Found prime: " + i);
            }
        }
    }

    public static void main(String[] args) {
        CalculatePrimes calculator = new CalculatePrimes();
        calculator.start();
        try {
            Thread.sleep(TEN_SECONDS);
        } catch (InterruptedException e) {
            // fall through
        }

        calculator.finished = true;
    }

}

/*
Found prime: 2
Found prime: 3
Found prime: 5
Found prime: 7
Found prime: 11
Found prime: 13
Found prime: 17
Found prime: 19
Found prime: 23
Found prime: 29
...
...
Found prime: 577009
Found prime: 577033

*/

