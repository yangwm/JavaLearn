//: creational.builder.TestBuilder.java

package creational.builder;

public class TestBuilder {
	public static void main(String[] args) {
		BuilderImpl1 builder = new BuilderImpl1();
		Director director = new Director(builder);
		Product product = director.createProduct();
		System.out.println(product);
	}
}

interface Builder {
	void buildPart1();
	void buildPart2();
	void buildPart3();
	
	Product getProduct();
}
class BuilderImpl1 implements Builder {
	private int part1;
	private double part2;
	private String part3;

	//
	public void buildPart1() {
		System.out.println("create part1");
		this.part1 = 1;
	}
	public void buildPart2() {
		System.out.println("create part2");
		this.part2 = 2.0;
	}
	public void buildPart3() {
		System.out.println("create part3");
		this.part3 = "three";
	}

	public Product getProduct() {
		return new Product(part1, part2, part3);
	}
}
class Product {
	private int part1;
	private double part2;
	private String part3;
	
	public Product(int part1, double part2, String part3) {
		this.part1 = part1;
		this.part2 = part2;
		this.part3 = part3;
	}
	public Product() {
		this.part1 = 0;
		this.part2 = 0.0;
		this.part3 = "";
	}
	
	@Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Product{part1=");
        sb.append(part1);
        sb.append(", part2=");
        sb.append(part2);
        sb.append(", part3=");
        sb.append(part3);
        sb.append("}");
        return sb.toString();
	}
	
}

class Director {
	Builder b;
	public Director(Builder b) {
		this.b = b;
	}
	public Product createProduct() {
		b.buildPart1();
		b.buildPart2();
		b.buildPart3();
		return b.getProduct();
	}
}
