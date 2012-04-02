//: behavioral/stragegy/TestStrategy.java

package behavioral.stragegy;

public class TestStrategy {
	public static void main(String[] args) {
		Strategy strategy = new June1Strategy();
		
		Book book = new Book(100);
		book.setStrategy(strategy);
		System.out.println(book.getPrice());
	}
}
class Book {
	Strategy strategy;
	private double price;
	
	public Book(double price) {
		this.price = price;
		this.strategy = new GeneralStrategy();
	}
	public double getPrice() {
		return price * strategy.getDiscount();
	}
	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
}

interface Strategy {
	double getDiscount();
}
class GeneralStrategy implements Strategy {
	public double getDiscount() {
		return 1.0;
	}
}
class May1Strategy implements Strategy {
	public double getDiscount() {
		return 0.8;
	}
}
class June1Strategy implements Strategy {
	public double getDiscount() {
		return 0.7;
	}
}


