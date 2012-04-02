/**
 * 
 */
package json.ext;

/**
 * 接收者
 * 
 * @author yangwm Jul 7, 2010 11:13:01 AM 
 */
public class Recipient {
    
    private long recipientId;
    private String recipientType;
    private String recipientAddress;
    
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Recipient)) {
            return false;   
        }
        Recipient bean = (Recipient)obj;
        return this.recipientId == bean.recipientId 
            && this.recipientType.equals(bean.recipientType)
            && this.recipientAddress.equals(bean.recipientAddress);     
    }
    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * recipientId;
        if(recipientType != null) {
            result += 31 * recipientType.hashCode();
        }
        if(recipientAddress != null) {
            result += 31 * recipientAddress.hashCode();
        }
        return result;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Recipient{recipientId=");
        sb.append(recipientId);
        sb.append(", recipientType=");
        sb.append(recipientType);
        sb.append(", recipientAddress=");
        sb.append(recipientAddress);
        sb.append("}");
        return sb.toString();
    }

    public long getRecipientId() {
        return recipientId;
    }
    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }
    public String getRecipientType() {
        return recipientType;
    }
    public void setRecipientType(String recipientType) {
        this.recipientType = recipientType;
    }
    public String getRecipientAddress() {
        return recipientAddress;
    }
    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }
    
}
