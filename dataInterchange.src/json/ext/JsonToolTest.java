/**
 * 
 */
package json.ext;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * JSON帮助工具类测试  
 * 
 * @author yangwm May 10, 2010 3:42:41 PM
 */
public class JsonToolTest {
    
    static class ToJson {
        /**
         * @param args
         */
        public static void main(String[] args) {
            MessageInfo msg = BaseTest.getMessageInfo();
            System.out.println(msg.toString());
            
            JSONObject jsonObject = JsonTool.bean2Json(msg);
            System.out.println(jsonObject);
            for (Iterator<?> it = jsonObject.keys(); it.hasNext(); ) {
                String key =  (String) it.next();
                Object value = jsonObject.opt(key);
                System.out.println(key + ":" + value + ", " + value.getClass());
            }
            
            System.out.println("\n");
            try {
                jsonObject = new JSONObject(jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(jsonObject);
            for (Iterator<?> it = jsonObject.keys(); it.hasNext(); ) {
                String key =  (String) it.next();
                Object value = jsonObject.opt(key);
                System.out.println(key + ":" + value + ", " + value.getClass());
            }
        }

    }

}

/*
ToJson:
MessageInfo{messageId=55555555555555, messageSubject=some msg, messageContent=Content{type=1, details=哈哈噢噢，who am i?}, success=true, recipientList=[Recipient{recipientId=1, recipientType=email, recipientAddress=yangwuming@ultrapower.com.cn}, Recipient{recipientId=2, recipientType=sms, recipientAddress=13811229996}], subscriberMap={google=Subscriber{subscriberTool=google, subscriberAddress=yangwuming@google.com, subscriberMoney=15.5}, youdao=Subscriber{subscriberTool=youdao, subscriberAddress=jxfzywm@163.com, subscriberMoney=10.5}}}
{"messageSubject":"some msg","messageSubjectLength":8,"subscriberMap":{"google":{"subscriberTool":"google","subscriberMoney":15.5,"subscriberAddress":"yangwuming@google.com"},"youdao":{"subscriberTool":"youdao","subscriberMoney":10.5,"subscriberAddress":"jxfzywm@163.com"}},"messageContent":{"details":"哈哈噢噢，who am i?","type":1},"messageId":55555555555555,"success":true,"recipientList":[{"recipientId":1,"recipientAddress":"yangwuming@ultrapower.com.cn","recipientType":"email"},{"recipientId":2,"recipientAddress":"13811229996","recipientType":"sms"}]}
messageSubject:some msg, class java.lang.String
messageSubjectLength:8, class java.lang.Byte
subscriberMap:{"google":{"subscriberTool":"google","subscriberMoney":15.5,"subscriberAddress":"yangwuming@google.com"},"youdao":{"subscriberTool":"youdao","subscriberMoney":10.5,"subscriberAddress":"jxfzywm@163.com"}}, class org.json.JSONObject
messageContent:{"details":"哈哈噢噢，who am i?","type":1}, class org.json.JSONObject
messageId:55555555555555, class java.lang.Long
success:true, class java.lang.Boolean
recipientList:[{"recipientId":1,"recipientAddress":"yangwuming@ultrapower.com.cn","recipientType":"email"},{"recipientId":2,"recipientAddress":"13811229996","recipientType":"sms"}], class org.json.JSONArray


{"messageSubject":"some msg","messageSubjectLength":8,"messageContent":{"details":"哈哈噢噢，who am i?","type":1},"subscriberMap":{"google":{"subscriberTool":"google","subscriberAddress":"yangwuming@google.com","subscriberMoney":15.5},"youdao":{"subscriberTool":"youdao","subscriberAddress":"jxfzywm@163.com","subscriberMoney":10.5}},"messageId":55555555555555,"success":true,"recipientList":[{"recipientId":1,"recipientAddress":"yangwuming@ultrapower.com.cn","recipientType":"email"},{"recipientId":2,"recipientAddress":"13811229996","recipientType":"sms"}]}
messageSubject:some msg, class java.lang.String
messageSubjectLength:8, class java.lang.Integer
messageContent:{"details":"哈哈噢噢，who am i?","type":1}, class org.json.JSONObject
subscriberMap:{"google":{"subscriberTool":"google","subscriberAddress":"yangwuming@google.com","subscriberMoney":15.5},"youdao":{"subscriberTool":"youdao","subscriberAddress":"jxfzywm@163.com","subscriberMoney":10.5}}, class org.json.JSONObject
messageId:55555555555555, class java.lang.Long
success:true, class java.lang.Boolean
recipientList:[{"recipientId":1,"recipientAddress":"yangwuming@ultrapower.com.cn","recipientType":"email"},{"recipientId":2,"recipientAddress":"13811229996","recipientType":"sms"}], class org.json.JSONArray

*/

