package lang;


import java.util.ArrayList;
import java.util.List;

public class NumberTest {
	public static void main(String[] args) {
		List childrens = new ArrayList();
		for (int i = 0; i < 16 * 16 + 15; i++) {
			childrens.add("" + i);
		}
		
		String number = "0" + Integer.toString(childrens.size(), 16);
		System.out.println("number=" + number);
        number = number.toUpperCase().substring(number.length() - 2);
        System.out.println("number.length() - 2 = " + number);
	}
}
