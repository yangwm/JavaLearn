package reflect;


import java.io.*;

public class TestReflection {
	public static void main(String[] args) throws Exception{
		String path = TestReflection.class.getResource("").getPath();
		FileReader fr=new FileReader(path + "1.txt");
		BufferedReader in=new BufferedReader(fr);
		String className=in.readLine();
		in.close();
		
		Object s=null;
		
		Class c=Class.forName(className);
		s=c.newInstance();
		
		System.out.println(s);
	}
}
