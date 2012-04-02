//: 
// 

public class TestReUse {
    public static void main(String[] args) {
    	Huxz h = new Huxz(new Liucy());
    	h.teachCpp();
    }
}


// well, has a relationship
class Huxz {
	private Liucy liucy;
	public void teachCpp() {
		liucy.teachCpp();
	}
	public Huxz() {
		this.liucy = new Liucy();
	}
	public Huxz(Liucy liucy) {
		this.liucy = liucy;
	}
}
class Liucy {
	public void teachCpp() {
		System.out.println("teach cpp...");
	}
}

//// not good , extends inhertance (is a relationship)
//class Liucy {
//	public void teachCpp() {
//	}
//}
//class Huxz extends Liucy {
//	
//}
