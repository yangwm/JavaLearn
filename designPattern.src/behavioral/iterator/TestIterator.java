//: behavioral/iterator/TestIterator.java

package behavioral.iterator;

public class TestIterator {
	public static void main(String[] args) {
		Stack s = new Stack();
		s.push("Stack a");
		s.push("Stack b");
		s.push("Stack c");
		s.push("Stack d");
		s.push("Stack e");
		print(s.iterator());
		
		LinkedList l = new LinkedList();
		l.add("List a");
		l.add("List b");
		l.add("List c");
		l.add("List d");
		l.add("List e");
		print(l.iterator());
	}
	
	public static void print(Iter iter) {
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}

interface Iter {
	boolean hasNext();
	Object next();
}
class Stack {
	Object[] os = new Object[10];
	int index = 0;
	private void expand() {
		Object[] os2 = new Object[os.length * 2];
		System.arraycopy(os,0,os2,0,os.length);
		os = os2;
	}
	public void push(Object o) {
		if (index == os.length) expand();
		os[index] = o;
		index++;
	}
	public Object pop() {
		index--;
		Object o = os[index];
		os[index] = null;
		return o;
	}
	private class StackIter implements Iter {
		int cursor = index;
		public boolean hasNext() {
			return cursor > 0;
		}
		public Object next() {
			return os[--cursor];
		}
	}
	public Iter iterator() {
		return new StackIter();
	}
}

class LinkedList {
	static class Node {
		Object o;
		Node next;
		public Node(Object o, Node next) {
			this.o = o;
			this.next = next;
		}
	}
	Node head;// = new Node(null, null)
	Node cur;

    public LinkedList() {
    }
	public void add(Object o) {
	    Node n = new Node(o, null);
	    if (head == null) {
	        head = n;
	        cur = n;
	    }
	    cur.next = n;
	    cur = n;
	}
	public Object remove() {
		Node n = head;
		head = head.next;
		return n.o;
	}
	
	private class LinkedListIter implements Iter {
		Node currentNode = head;
		public boolean hasNext() {
			return this.currentNode != null;
		}
		public Object next() {
			Node n = currentNode;
			currentNode = currentNode.next;
			return n.o;
		}
	}
	public Iter iterator() {
		return new LinkedListIter();
	}
}
