/**
 * 
 */
package betwixt.mortgage;

/**
 * @author yangwm in Apr 27, 2009 10:21:14 PM
 */
public class Mortgage {
    private float rate;
    private int years;
    
    public Mortgage(){
    }
    
    public Mortgage(float rate, int years) {
        this.rate = rate;
        this.years = years;
    }

    public float getRate() {
        return rate;
    }
    public void setRate(float rate) {
        this.rate = rate;
    }
    public int getYears() {
        return years;
    }
    public void setYears(int years) {
        this.years = years;
    }
}
