//:

public class TestPoly {
	public static void main(String[] args) {
		Animal[] as = new Animal[4];
		as[0] = new Animal(0);
		as[1] = new Animal(1);
		as[2] = new Animal(2);
		as[3] = new Animal(3);
		
		for (int i = 0; i < as.length; i++) {
			as[i].eat();
		}
	}
}

class Animal {
	private int type;
	public Animal(int type) {
		this.type = type;
	}
	public void eat() {
		switch (type) {
		case 0 :
			System.out.println(0);
			break;
		case 1 :
			System.out.println(1);
			break;
		case 2 :
			System.out.println(2);
			break;
		default :
			System.out.println(3);	
		}
	}
}
