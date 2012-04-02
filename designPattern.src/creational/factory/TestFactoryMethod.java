//: creational.factory.TestFactoryMethod.java

package creational.factory;

public class TestFactoryMethod {
	public static void main(String[] args) {
	    GraphFactory graphFactory = new CircleFactory();
	    Circle graph = (Circle) graphFactory.getGraph();
	    graph.draw();
	}
}



abstract class Graph {
    public void draw() {
    }
}
class Circle extends Graph {
    @Override
    public void draw() {
        System.out.println("Circle");
    }
}
class Triangle extends Graph {
    @Override
    public void draw() {
        System.out.println("Triangle");
    }
}
class Square extends Graph {
    @Override
    public void draw() {
        System.out.println("Square");
    }
}


abstract class GraphFactory {
	public abstract Graph getGraph();
}
class CircleFactory extends GraphFactory {
	public Graph getGraph() {
		return new Circle();
	}
}
class TriangleFactory extends GraphFactory {
	public Graph getGraph() {
		return new Triangle();
	}
}
class SquareFactory extends GraphFactory {
    public Graph getGraph() {
        return new Square();
    }
}




