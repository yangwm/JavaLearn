package beanutils.sample;

/**
 * Used to copy properties from SampleOjbect. Used to nested property.
 * 
 * @author yangwm in Feb 22, 2009 2:08:39 PM
 */
public class SampleObjectA {
    String name = null;
    String display = null;
    Double num = null;

    /**
     * @return Returns the num.
     */
    public Double getNum() {
        return num;
    }

    /**
     * @param num The num to set.
     */
    public void setNum(Double num) {
        this.num = num;
    }

    /**
     * @return Returns the display.
     */
    public String getDisplay() {
        return display;
    }

    /**
     * @param display The display to set.
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
}
