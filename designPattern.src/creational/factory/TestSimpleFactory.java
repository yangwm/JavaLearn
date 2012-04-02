//: creational.factory.TestSimpleFactory.java

package creational.factory;

public class TestSimpleFactory {
	public static void main(String[] args) {
		Animal animal = AnimalFacotry.getAnimal("Dog");
		animal.cry();
	}
}

abstract class Animal {
    public void cry() {
    }
}
class Dog extends Animal {
    @Override
    public void cry() {
        System.out.println("woof woof ... ");
    }
}
class Cat extends Animal {
    @Override
    public void cry() {
        System.out.println("meow meow ... ");
    }
}
class Sheep extends Animal {
    @Override
    public void cry() {
        System.out.println("baa baa ... ");
    }
}

class AnimalFacotry {
	public static Animal getAnimal(String name) {	
		if (name.equals("Dog")) {
			return new Dog();
		} else if (name.equals("Cat")) {
			return new Cat();
		}
		return new Sheep();
	}
}
