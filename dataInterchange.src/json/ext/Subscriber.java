/**
 * 
 */
package json.ext;

/**
 * 订阅者
 * 
 * @author yangwm Jul 7, 2010 3:54:05 PM 
 */
public class Subscriber {
    
    private String subscriberTool;
    private String subscriberAddress;
    private Double subscriberMoney;
    
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Subscriber)) {
            return false;   
        }
        Subscriber bean = (Subscriber)obj;
        return this.subscriberTool.equals(bean.subscriberTool)
            && this.subscriberAddress.equals(bean.subscriberAddress)
            && this.subscriberMoney == subscriberMoney ;     
    }
    @Override
    public int hashCode() {
        int result = 17;
        if(subscriberTool != null) {
            result += 31 * subscriberTool.hashCode();
        }
        if(subscriberAddress != null) {
            result += 31 * subscriberAddress.hashCode();
        }
        if(subscriberMoney != null) {
            result += 31 * subscriberMoney.hashCode();
        }
        return result;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Subscriber{subscriberTool=");
        sb.append(subscriberTool);
        sb.append(", subscriberAddress=");
        sb.append(subscriberAddress);
        sb.append(", subscriberMoney=");
        sb.append(subscriberMoney);
        sb.append("}");
        return sb.toString();
    }
    
    public String getSubscriberTool() {
        return subscriberTool;
    }
    public void setSubscriberTool(String subscriberTool) {
        this.subscriberTool = subscriberTool;
    }
    public String getSubscriberAddress() {
        return subscriberAddress;
    }
    public void setSubscriberAddress(String subscriberAddress) {
        this.subscriberAddress = subscriberAddress;
    }
    public Double getSubscriberMoney() {
        return subscriberMoney;
    }
    public void setSubscriberMoney(Double subscriberMoney) {
        this.subscriberMoney = subscriberMoney;
    }
    
}
