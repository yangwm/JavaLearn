//: structural/proxy/TestProxy.java

package structural.proxy;

public class TestProxy {
	public static void main(String[] args) {
		MoneyMakerImpl mmi = new MoneyMakerImpl();
		MoneyMaker m2 = new MoneyIssuer(mmi);
		m2.printMoney();
	}
}

interface MoneyMaker {
    void printMoney();
}
class MoneyMakerImpl implements MoneyMaker  {
    @Override
    public void printMoney() {
        System.out.println("free print money");
    }
}
class MoneyIssuer implements MoneyMaker {
    private MoneyMaker h;
    public MoneyIssuer(MoneyMaker h) {
        this.h = h;
    }

    @Override
    public void printMoney() {
        System.out.println("print money need salary ");
        h.printMoney();
    }
}

