/**
 * 
 */
package json.ext;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;


/**
 * json first test 
 * @author: yangwm
 */
public class JsonFirstTest {
    

    /**
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        JSONObject textObj = new JSONObject("{\"1750715731\":{\"30\":1750715731}}");
        //textObj.get("1750715731");
        System.out.println(textObj.toString());
    }
    
    /**
     * 返回JSON信息--简单对象
     * 
     * @param msg
     * @return
     */
    public JSONObject getJSONObjectFromSample(MessageInfo msg) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("messageId", msg.getMessageId());
            jsonObject.put("messageSubject", msg.getMessageSubject());
            jsonObject.put("messageSubjectLength", msg.getMessageSubjectLength());
            jsonObject.put("messageContent", new JSONObject(msg.getMessageContent())); // must JSONObject 
            jsonObject.put("success", msg.getSuccess());
            jsonObject.put("messageSize", msg.getMessageSize());
            jsonObject.put("messageSize2", msg.getMessageSize2());
            jsonObject.put("recipientList", msg.getRecipientList());
            jsonObject.put("subscriberMap", msg.getSubscriberMap());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        return jsonObject;
    }
    
    
    /**
     * test getJSONObject from sample
     * 
     * @throws JSONException
     */
    @Test public void testFromSample() throws JSONException {
        MessageInfo msg = BaseTest.getMessageInfo();
        JSONObject jsonObject = new JsonFirstTest().getJSONObjectFromSample(msg);
        Assert.assertEquals(55555555555555L, jsonObject.get("messageId"));
        Assert.assertEquals("some msg", jsonObject.get("messageSubject"));
        Assert.assertEquals((short)8, jsonObject.get("messageSubjectLength"));
        //Assert.assertEquals(new JSONObject(new Content(1, "哈哈噢噢，who am i?")), jsonObject.get("messageContent"));
        Assert.assertEquals(true, jsonObject.get("success"));
        Assert.assertEquals((Long)10L, jsonObject.get("messageSize"));
        Assert.assertEquals((Short)(short)20, jsonObject.get("messageSize2"));
        
        try {
            jsonObject = new JSONObject(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(55555555555555L, jsonObject.get("messageId"));
        Assert.assertEquals("some msg", jsonObject.get("messageSubject"));
        Assert.assertEquals((byte)8, jsonObject.get("messageSubjectLength"));
        //Assert.assertEquals(new JSONObject(new Content(1, "哈哈噢噢，who am i?")), jsonObject.get("messageContent"));
        Assert.assertEquals(true, jsonObject.get("success"));
        Assert.assertEquals((Byte)(byte)10, jsonObject.get("messageSize"));
        Assert.assertEquals((Byte)(byte)20, jsonObject.get("messageSize2"));
    }

}
