package basics.collections.interfaces;

// {args: java Countdown 4}
import java.util.*;

public class Countdown {
    public static void main(String[] args)
            throws InterruptedException {
        int time = Integer.parseInt(args[0]);
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = time; i >= 0; i--)
            queue.add(i);
        while (!queue.isEmpty()) {
            System.out.println(queue.remove());
            Thread.sleep(1000);
        }
    }

    static <E> List<E> heapSort(Collection<E> c) {
        Queue<E> queue = new PriorityQueue<E>(c);
        List<E> result = new ArrayList<E>();
        while (!queue.isEmpty())
            result.add(queue.remove());
        return result;
    }


}

