public class TestOCP {
	public static void main(String[] args) {
		//Animal a=new Animal(Animal.TIGER);
	//	a.eat();
	}
}
/*
class Animal{
	public static final int CAT=0;
	public static final int DOG=1;
	public static final int TIGER=2;
	int type;
	public Animal(int type){
		this.type=type;
	}
	public void eat(){
		if (type==CAT)	System.out.println("Cat eats Fish");
		else if (type==DOG) System.out.println("Dog eats Bone");
		else System.out.println("Tiger eats Human");
	}
}
*/
abstract class AnimalOCP{
	public abstract void eat();
}
class Dog extends AnimalOCP{
	public void eat(){
		System.out.println("Dog eats Bone");
	}
}
class Cat extends AnimalOCP{
	public void eat(){
		System.out.println("Cat eats Fish");
	}
}




