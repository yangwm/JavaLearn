public class TestLod {
}
class SomeOne{
	public void operation1(Friend f){
		Stranger s=f.introduce();
		s.operation2();
	}
}
class Friend{
	private Stranger str=new Stranger();
	public Stranger introduce(){
		return str;
	}
}
class Stranger{
	public void operation2(){}
}
