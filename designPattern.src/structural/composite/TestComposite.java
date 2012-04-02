//: structural/composite/TestComposite.java

package structural.composite;

import java.util.*;

public class TestComposite {
    public static void main(String[] args) {
        Node n1 = new LeafNode(3);
        Node n2 = new LeafNode(4);
        Node n3 = new LeafNode(6);
        Node n4 = new LeafNode(5);
        Node n5 = new LeafNode(2);
        Node n6 = new LeafNode(9);
        Node n7 = new LeafNode(12);
        Node n8 = new LeafNode(7);
        Node n9 = new LeafNode(8);

        Node c3 = new CompositeNode(n8, n9);
        Node c4 = new CompositeNode(n5, c3);

        Node c1 = new CompositeNode(n1, n2, n3);
        Node c2 = new CompositeNode(n4, c4);
        Node c5 = new CompositeNode(n6, n7);

        Node root = new CompositeNode(c1, c2, c5);
        System.out.println(root.getValue());
    }

}

abstract class Node {
    public abstract int getValue();
}

class LeafNode
        extends Node {
    private int value;

    public LeafNode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

class CompositeNode extends Node {
    private List<Node> child = new ArrayList<Node>();

    public CompositeNode(Node... nodes) {
        for (Node n : nodes) {
            child.add(n);
        }
    }

    public int getValue() {
        int result = 0;
        for (Node n : child) {
            result += n.getValue();
        }
        return result;
    }
}
