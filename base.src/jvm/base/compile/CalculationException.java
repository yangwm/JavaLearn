/**
 * 
 */
package jvm.base.compile;

/**
 * 
 * 
 * @author yangwm Jan 2, 2011 9:41:39 PM
 */
public class CalculationException extends Exception {

    public CalculationException() {
        super();
    }

    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CalculationException(Throwable cause) {
        super(cause);
    }
}
