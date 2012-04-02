package json.ext;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectTest {
    public static void main(String[] args) {
        try {
            String jsonStr = "{\"messageSubject\":\"some msg\",\"messageSubjectLength\":8,\"subscriberMap\":{\"google\":{\"subscriberTool\":\"google\",\"subscriberMoney\":15.5,\"subscriberAddress\":\"yangwuming@google.com\"},\"youdao\":{\"subscriberTool\":\"youdao\",\"subscriberMoney\":10.5,\"subscriberAddress\":\"jxfzywm@163.com\"}},\"messageContent\":{\"contentType\":1,\"contentDetails\":\"哈哈噢噢，who am i?\"},\"messageId\":55555555555555,\"success\":true,\"recipientList\":[{\"recipientId\":1,\"recipientAddress\":\"yangwuming@ultrapower.com.cn\",\"recipientType\":\"email\"},{\"recipientId\":2,\"recipientAddress\":\"13811229996\",\"recipientType\":\"sms\"}]}";
            JSONObject jsonObject = new JSONObject(jsonStr);
            
            System.out.println(jsonObject.toString());
            for (Iterator<?> keyIter = jsonObject.keys(); keyIter.hasNext(); ) {
                String name =  (String)keyIter.next();
                Object obj = jsonObject.opt(name);
                Class<?> cls = obj.getClass();
                System.out.println(name + ":" + obj + ", " + cls);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

