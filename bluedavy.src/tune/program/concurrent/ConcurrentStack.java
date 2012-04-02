/**
 * 
 */
package tune.program.concurrent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用Treiber算法实现Stack：基于CAS以及AtomicReference。
 * 
 * @author yangwm Aug 25, 2010 10:50:17 AM
 */
public class ConcurrentStack<E> {
    
    AtomicReference<Node<E>> head = new AtomicReference<Node<E>>();

    public void push(E item) {
        Node<E> newHead = new Node<E>(item);
        Node<E> oldHead;
        do {
            oldHead = head.get();
            newHead.next = oldHead;
        } while (!head.compareAndSet(oldHead, newHead));
    }
    
    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;
        do {
            oldHead = head.get();
            if (oldHead == null) {
                return null;
            }
            newHead = oldHead.next;
        } while (!head.compareAndSet(oldHead, newHead));
        return oldHead.item;
    }
    
    static class Node<E> {
        final E item;
        Node<E> next;
        public Node(E item) {
            this.item = item;
        }
    }

}
