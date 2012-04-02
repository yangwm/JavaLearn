public class TestISP {
	public static void main(String[] args) {
		IAISP a=getIA();
		a.m1();
		
		IBISP b=getIB();
		b.m3();
	}
	
	private static  C c=new C();
	public static IAISP getIA(){
		return c;
	}
	public static IBISP getIB(){
		return c;
	}
}
interface IAISP{
	void m1();
	void m2();
}
interface IBISP{
	void m3();
	void m4();
}
class C implements IAISP, IBISP {
	public void m1(){}
	public void m2(){}
	public void m3(){}
	public void m4(){}
}
