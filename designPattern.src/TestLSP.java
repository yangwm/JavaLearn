
public class TestLSP {
	public static void main(String[] args) {

	}
	public void setRect(Rect r){
		while(r.getKuan()<=r.getChang()){
			r.setKuan(r.getKuan()+1);
		}
	}

}

class Rect{
	int chang;
	int kuan;
	public int getChang() {
		return chang;
	}
	public void setChang(int chang) {
		this.chang = chang;
	}
	public int getKuan() {
		return kuan;
	}
	public void setKuan(int kuan) {
		this.kuan = kuan;
	}
	
}

class Square extends Rect{
	int wide;

	@Override
	public int getChang() {
		return this.wide;
	}

	@Override
	public int getKuan() {
		return this.wide;
	}

	@Override
	public void setChang(int chang) {
		this.wide=chang;
	}

	@Override
	public void setKuan(int kuan) {
		this.wide=kuan;
	}
	
}
