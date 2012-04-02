//: structural/bridge/TestBridge.java

package structural.bridge;

public class TestBridge {
	public static void main(String[] args) {
	    BankAccount account = new CreditAccount();
		BankUser user = new DollarUser(account);
		user.getMoney();
	}
}
interface BankAccount {
	void deposit();
	void withdraw();
}
class SavingAccount implements BankAccount {
    @Override
	public void deposit() {
		System.out.println("Save");
	}
    
    @Override
	public void withdraw() {
		System.out.println("withdraw can't be ceiling");
	}
}
class CreditAccount implements BankAccount {
    @Override
	public void deposit() {
		System.out.println("Save");
	}
    
    @Override
	public void withdraw() {
		System.out.println("withdraw can be ceiling");
	}
}

abstract class BankUser {
	protected  BankAccount account;

	public BankUser(BankAccount account) {
		super();
		this.account = account;
	}
	public abstract void saveMoney();
	public abstract void getMoney();
}
class RenminbiUser extends BankUser {
	public RenminbiUser(BankAccount account) {
		super(account);
	}

	@Override
	public void getMoney() {
		System.out.print("RMB ");
		super.account.withdraw();
	}

	@Override
	public void saveMoney() {
		System.out.println("RMB ");
		super.account.deposit();
	}
	
}
class DollarUser extends BankUser {
	public DollarUser(BankAccount account) {
		super(account);
	}
	
	@Override
	public void getMoney() {
		System.out.println("Dollar ");
		super.account.withdraw();
	}

	@Override
	public void saveMoney() {
		System.out.println("Dollar ");
		super.account.deposit();
	}
}
