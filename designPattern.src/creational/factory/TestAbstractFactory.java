//: creational.factory.TestAbstractFactory.java

package creational.factory;
import java.awt.*;

import javax.swing.*;

public class TestAbstractFactory {
	public static void main(String[] args) {
		// new a Factory
		GUIFactory fact = new AWTFactory();
		//GUIFactory fact = new SwingFacotry();
		
		Frame f = fact.getFrame();
		Component c1 = fact.getButton();
		Component c2 = fact.getTextField();
		
		f.setSize(500, 300);
		f.setLayout(new FlowLayout());
		f.add(c1);
		f.add(c2);
		
		f.setVisible(true);
	}
}

abstract class GUIFactory {
	public abstract Component getButton();
	public abstract Component getTextField();
	public abstract Frame getFrame();
	
}
class AWTFactory extends GUIFactory {

	@Override
	public Component getButton() {
		return new Button("AWT Button"); 
	}

	@Override
	public Frame getFrame() {
		return new Frame("AWT Frame");
	}

	@Override
	public Component getTextField() {
		return new TextField("AWT TextField");
	}
	
}
class SwingFacotry extends GUIFactory {

	@Override
	public Component getButton() {
		return new JButton("Swing Button"); 
	}

	@Override
	public Frame getFrame() {
		return new JFrame("Swing Frame");
	}

	@Override
	public Component getTextField() {
		return new JTextField("Swing TextField");
	}
}

