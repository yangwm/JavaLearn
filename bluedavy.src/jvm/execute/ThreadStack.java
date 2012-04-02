/**
 * 
 */
package jvm.execute;

/**
 * 
 * 
 * @author yangwm Aug 1, 2010 11:20:53 PM
 */
public class ThreadStack {
    
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                loop(0);
            }
            
            private void loop(int i) {
                if (i != 1000) {
                    i++;
                    loop(i);
                } else {
                    return;
                }
            }
            
        }).start();
    }

}

/*
-Xss1K

Exception in thread "Thread-0" java.lang.StackOverflowError
    at jvm.execute.ThreadStack$1.loop(ThreadStack.java:21)
    at jvm.execute.ThreadStack$1.loop(ThreadStack.java:23)
    ......
    at jvm.execute.ThreadStack$1.loop(ThreadStack.java:23)
    at jvm.execute.ThreadStack$1.run(ThreadStack.java:17)
    at java.lang.Thread.run(Thread.java:717)
        
*/

