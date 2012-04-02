/**
 * 
 */
package json.ext;

/**
 * 内容
 * 
 * @author yangwm Jul 7, 2010 5:03:20 PM 
 */
public class Content {
    
    private int contentType;  // 1表示普通文本，2表示加密文本 
    private String contentDetails;
    
    public Content() {
    }
    public Content(int contentType, String contentDetails) {
        this.contentType = contentType;
        this.contentDetails = contentDetails;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Content)) {
            return false;   
        }
        Content bean = (Content)obj;
        return this.contentType == bean.contentType 
            && this.contentDetails.equals(bean.contentDetails);     
    }
    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * contentType;
        if(contentDetails != null) {
            result += 31 * contentDetails.hashCode();
        }
        return result;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Content{contentType=");
        sb.append(contentType);
        sb.append(", contentDetails=");
        sb.append(contentDetails);
        sb.append("}");
        return sb.toString();
    }
    
    
    public int getContentType() {
        return contentType;
    }
    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
    public String getContentDetails() {
        return contentDetails;
    }
    public void setContentDetails(String contentDetails) {
        this.contentDetails = contentDetails;
    }
    
}
