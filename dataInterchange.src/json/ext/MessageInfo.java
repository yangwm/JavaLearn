/**
 * 
 */
package json.ext;

import java.util.List;
import java.util.Map;

/**
 * 信息对象 
 * 
 * @author yangwm May 10, 2010 3:34:23 PM
 */
public class MessageInfo {
    
    private long messageId;
    private String messageSubject;
    private short messageSubjectLength;
    private Content messageContent;
    private boolean success;
    private Long messageSize;
    private Short messageSize2;
    private List<Recipient> recipientList;
    private Map<String, Subscriber> subscriberMap;
    
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MessageInfo{messageId=");
        sb.append(messageId);
        sb.append(", messageSubject=");
        sb.append(messageSubject);
        sb.append(", messageContent=");
        sb.append(messageContent);
        sb.append(", success=");
        sb.append(success);
        sb.append(", messageSize=");
        sb.append(messageSize);
        sb.append(", messageSize2=");
        sb.append(messageSize2);
        sb.append(", recipientList=");
        sb.append(recipientList);
        sb.append(", subscriberMap=");
        sb.append(subscriberMap);
        sb.append("}");
        return sb.toString();
    }


    public long getMessageId() {
        return messageId;
    }
    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
    public String getMessageSubject() {
        return messageSubject;
    }
    public void setMessageSubject(String messageSubject) {
        this.messageSubject = messageSubject;
    }
    public short getMessageSubjectLength() {
        return messageSubjectLength;
    }
    public void setMessageSubjectLength(short messageSubjectLength) {
        this.messageSubjectLength = messageSubjectLength;
    }
    public Content getMessageContent() {
        return messageContent;
    }
    public void setMessageContent(Content messageContent) {
        this.messageContent = messageContent;
    }
    public boolean getSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public Long getMessageSize() {
        return messageSize;
    }
    public void setMessageSize(Long messageSize) {
        this.messageSize = messageSize;
    }
    public Short getMessageSize2() {
        return messageSize2;
    }
    public void setMessageSize2(Short messageSize2) {
        this.messageSize2 = messageSize2;
    }
    public List<Recipient> getRecipientList() {
        return recipientList;
    }
    public void setRecipientList(List<Recipient> recipientList) {
        this.recipientList = recipientList;
    }
    public Map<String, Subscriber> getSubscriberMap() {
        return subscriberMap;
    }
    public void setSubscriberMap(Map<String, Subscriber> subscriberMap) {
        this.subscriberMap = subscriberMap;
    }
    
}

