package basics.essential.concurrencys.simple;

public class ProducerConsumerExample {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Drop drop = new Drop();
        (new Thread(new Producer(drop))).start();
        (new Thread(new Consumer(drop))).start();
    }

}
