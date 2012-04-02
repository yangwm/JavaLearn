/**
 * 
 */
package jthread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用一个线程用于计时，并使用定期执行任务(TimerTask 线程被标记成守护程序线程，这样它就不会阻止程序退出) 
 * 
 * @author yangwm in Nov 20, 2009 10:27:45 AM
 */
public class CalculatePrimes2 {

    public static void main(String[] args) {
        Timer timer = new Timer();
        
        final CalculatePrimes calculator = new CalculatePrimes();
        calculator.start();

        timer.schedule(
                new TimerTask() {
                    public void run()
                    {
                        calculator.finished = true;
                    }
                }, CalculatePrimes.TEN_SECONDS);
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
Found prime: 584951
Found prime: 584963
Found prime: 584971
Found prime: 584981

*/
