package basics.essential.concurrencys;

public class HelloRunnable implements Runnable {
    
    @Override
    public void run() {
        System.out.println("Hello from a thread!");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new Thread(new HelloRunnable()).start();
    }

}
