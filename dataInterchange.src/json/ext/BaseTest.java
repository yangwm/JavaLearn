/**
 * 
 */
package json.ext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author yangwm Jul 7, 2010 5:53:42 PM 
 */
public class BaseTest {
    
    public static Recipient getRecipient() {
        Recipient recipient = new Recipient();
        recipient.setRecipientId(1);
        recipient.setRecipientType("email");
        recipient.setRecipientAddress("jxfzywm@163.com");
        return recipient;
    }
    
    public static Recipient getRecipient2() {
        Recipient recipient2 = new Recipient();
        recipient2.setRecipientId(2);
        recipient2.setRecipientType("sms");
        recipient2.setRecipientAddress("13811229996");
        return recipient2;
    }

    public static MessageInfo getMessageInfo() {
        /*
         * recipient
         */
        Recipient recipient = new Recipient();
        recipient.setRecipientId(1);
        recipient.setRecipientType("email");
        recipient.setRecipientAddress("jxfzywm@163.com");
        
        Recipient recipient2 = new Recipient();
        recipient2.setRecipientId(2);
        recipient2.setRecipientType("sms");
        recipient2.setRecipientAddress("13811229996");
        
        List<Recipient> recipientList = new ArrayList<Recipient>();
        recipientList.add(recipient);
        recipientList.add(recipient2);
        
        /*
         * subscriber
         */
        Subscriber subscriber = new Subscriber();
        subscriber.setSubscriberTool("youdao");
        subscriber.setSubscriberAddress("jxfzywm@163.com");
        subscriber.setSubscriberMoney(10.5);
        
        Subscriber subscriber2 = new Subscriber();
        subscriber2.setSubscriberTool("google");
        subscriber2.setSubscriberAddress("yangwuming@google.com");
        subscriber2.setSubscriberMoney(15.5);
        
        Map<String, Subscriber> subscriberMap = new HashMap<String, Subscriber>();
        subscriberMap.put(subscriber.getSubscriberTool(), subscriber);
        subscriberMap.put(subscriber2.getSubscriberTool(), subscriber2);
        
        /*
         * 
         */
        MessageInfo msg = new MessageInfo();
        msg.setMessageId(55555555555555L);
        msg.setMessageSubject("some msg");
        msg.setMessageSubjectLength((byte)8);
        msg.setMessageContent(new Content(1, "哈哈噢噢，who am i?"));
        msg.setSuccess(true);
        msg.setMessageSize(10L);
        msg.setMessageSize2((short)20);
        msg.setRecipientList(recipientList);
        msg.setSubscriberMap(subscriberMap);

        return msg;
    }
    
    public static String getJsonStr() {
        String jsonStr = "{\"message_subject_\":\"some msg\",\"message_size\":10,\"message_size2\":20,\"message_subject_length\":8,\"subscriber_map_\":{\"google\":{\"subscriberTool\":\"google\",\"subscriberMoney\":15.5,\"subscriberAddress\":\"yangwuming@google.com\"},\"youdao\":{\"subscriberTool\":\"youdao\",\"subscriberMoney\":10.5,\"subscriberAddress\":\"jxfzywm@163.com\"}},\"messageContent\":{\"contentType\":1,\"content_details\":\"哈哈噢噢，who am i?\"},\"message_id\":55555555555555,\"success\":true,\"recipient_list\":[{\"recipientId\":1,\"recipientAddress\":\"jxfzywm@163.com\",\"recipientType\":\"email\"},{\"recipientId\":2,\"recipientAddress\":\"13811229996\",\"recipientType\":\"sms\"}]}";
        //String jsonStr = "{\"message_size\":20}";
        return jsonStr;
    }
    
}
