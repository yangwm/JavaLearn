/**
 * 
 */
package junitstudy;

/**
 * @author yangwm in Apr 8, 2010 6:24:19 PM
 */
class Money {
    private int fAmount;
    private String fCurrency;

    public Money(int amount, String currency) {
        fAmount= amount;
        fCurrency= currency;
    }

    public int amount() {
        return fAmount;
    }

    public String currency() {
        return fCurrency;
    }
    
    public boolean equals(Object anObject) {
        if (anObject instanceof Money) {
            Money aMoney= (Money)anObject;
            return aMoney.currency().equals(currency())
                && amount() == aMoney.amount();
        }
        return false;
    }
    
    public Money add(Money m) {
        return new Money(amount()+m.amount(), currency());
    }
    
}
