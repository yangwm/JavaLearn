// behavioral/template/TestTemplateMethod.java

package behavioral.template;

public class TestTemplateMethod {
	public static void main(String[] args) {
	    Testcase testcase = new ATestcase();
	    testcase.test();
	}
}

abstract class Testcase {
	public abstract void beforeTest();
	public abstract void doTest();
	public abstract void afterTest();

	public final void test() {
	    beforeTest();
	    doTest();
	    afterTest();
	}
}
class ATestcase extends Testcase {
	@Override
	public void beforeTest() {
		System.out.println("A: <before>");
	}
	@Override
	public void doTest() {
		System.out.println("A: <test>");
	}
	@Override
	public void afterTest() {
		System.out.println("A: <after>");
	}
}
class BTestcase extends Testcase {
    @Override
    public void beforeTest() {
        System.out.println("B: <before>");
    }
    @Override
    public void doTest() {
        System.out.println("B: <test>");
    }
    @Override
    public void afterTest() {
        System.out.println("B: <after>");
    }
}
