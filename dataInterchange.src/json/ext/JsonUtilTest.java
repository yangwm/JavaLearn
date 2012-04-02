/**
 * 
 */
package json.ext;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;


/**
 * JSON帮助工具类测试  
 * 
 * @author yangwm May 10, 2010 3:42:41 PM
 */
public class JsonUtilTest {
    
    /**
     * test to json
     * 
     * @throws JSONException
     */
    @Test public void testToJson() throws JSONException {
        MessageInfo msg = BaseTest.getMessageInfo();
        JSONObject jsonObject = JsonUtil.toJSONObjectFromBean(msg);
        Assert.assertEquals(55555555555555L, jsonObject.get("messageId"));
        Assert.assertEquals("some msg", jsonObject.get("messageSubject"));
        Assert.assertEquals((short)8, jsonObject.get("messageSubjectLength"));
        //Assert.assertEquals(new JSONObject(new Content(1, "哈哈噢噢，who am i?")), jsonObject.get("messageContent"));
        Assert.assertEquals(true, jsonObject.get("success"));
        Assert.assertEquals((Long)10L, jsonObject.get("messageSize"));
        Assert.assertEquals((Short)(short)20, jsonObject.get("messageSize2"));
        
        jsonObject = JsonUtil.toJSONObject(jsonObject.toString());
        Assert.assertEquals(55555555555555L, jsonObject.get("messageId"));
        Assert.assertEquals("some msg", jsonObject.get("messageSubject"));
        Assert.assertEquals((byte)8, jsonObject.get("messageSubjectLength"));
        //Assert.assertEquals(new JSONObject(new Content(1, "哈哈噢噢，who am i?")), jsonObject.get("messageContent"));
        Assert.assertEquals(true, jsonObject.get("success"));
        Assert.assertEquals((Byte)(byte)10, jsonObject.get("messageSize"));
        Assert.assertEquals((Byte)(byte)20, jsonObject.get("messageSize2"));
    }
    
    
    /**
     * test fill bean
     * 
     * @throws JSONException
     */
    @Test public void testFillBean() throws JSONException {
        String jsonStr = BaseTest.getJsonStr();
        JSONObject jsonObject = JsonUtil.toJSONObject(jsonStr);
        Assert.assertEquals(55555555555555L, jsonObject.get("message_id"));
        Assert.assertEquals("some msg", jsonObject.get("message_subject_"));
        Assert.assertEquals((byte)8, jsonObject.get("message_subject_length"));
        //Assert.assertEquals(new JSONObject(new Content(1, "哈哈噢噢，who am i?")), jsonObject.get("messageContent"));
        Assert.assertEquals(true, jsonObject.get("success"));
        Assert.assertEquals((Byte)(byte)10, jsonObject.get("message_size"));
        Assert.assertEquals((Byte)(byte)20, jsonObject.get("message_size2"));
        
        jsonObject = JsonUtil.toJSONObject(jsonObject.toString());
        MessageInfo msg = new MessageInfo();
        JsonUtil.fillBeanFromJSONObject(msg, jsonObject, true);
        Assert.assertEquals(55555555555555L, msg.getMessageId());
        Assert.assertEquals("some msg", msg.getMessageSubject());
        Assert.assertEquals((short)8, msg.getMessageSubjectLength());
        Assert.assertEquals(new Content(1, "哈哈噢噢，who am i?"), msg.getMessageContent());
        Assert.assertEquals(true, msg.getSuccess());
        Assert.assertEquals((Long)10L, msg.getMessageSize());
        Assert.assertEquals((Short)(short)20, msg.getMessageSize2());
        Assert.assertEquals(2, msg.getRecipientList().size());
        Assert.assertEquals(BaseTest.getRecipient(), msg.getRecipientList().get(0));
        Assert.assertEquals(BaseTest.getRecipient2(), msg.getRecipientList().get(1));
    }

    
    /**
     * test json fill bean
     * 
     * @throws JSONException
     */
    @Test public void testJsonFillBean() throws JSONException {
        MessageInfo fromMsg = BaseTest.getMessageInfo();
        JSONObject jsonObject = JsonUtil.toJSONObjectFromBean(fromMsg);
        Assert.assertEquals(55555555555555L, jsonObject.get("messageId"));
        Assert.assertEquals("some msg", jsonObject.get("messageSubject"));
        Assert.assertEquals((short)8, jsonObject.get("messageSubjectLength"));
        //Assert.assertEquals(new JSONObject(new Content(1, "哈哈噢噢，who am i?")), jsonObject.get("messageContent"));
        Assert.assertEquals(true, jsonObject.get("success"));
        Assert.assertEquals((Long)10L, jsonObject.get("messageSize"));
        Assert.assertEquals((Short)(short)20, jsonObject.get("messageSize2"));
        
        jsonObject = JsonUtil.toJSONObject(jsonObject.toString());
        MessageInfo msg = new MessageInfo();
        JsonUtil.fillBeanFromJSONObject(msg, jsonObject, true);
        Assert.assertEquals(55555555555555L, msg.getMessageId());
        Assert.assertEquals("some msg", msg.getMessageSubject());
        Assert.assertEquals((short)8, msg.getMessageSubjectLength());
        Assert.assertEquals(new Content(1, "哈哈噢噢，who am i?"), msg.getMessageContent());
        Assert.assertEquals(true, msg.getSuccess());
        Assert.assertEquals((Long)10L, msg.getMessageSize());
        Assert.assertEquals((Short)(short)20, msg.getMessageSize2());
        Assert.assertEquals(2, msg.getRecipientList().size());
        Assert.assertEquals(BaseTest.getRecipient(), msg.getRecipientList().get(0));
        Assert.assertEquals(BaseTest.getRecipient2(), msg.getRecipientList().get(1));
    }

}

/*
FillBean
问题：
当messageSubjectLength类型为byte/short时反射调用field.set(bean, obj)引发异常。
messageSubject:some msg, class java.lang.String
messageSubjectLength:8, class java.lang.Integer
java.lang.IllegalArgumentException: Can not set byte/short field json.ext.MessageInfo.messageSubjectLength to java.lang.Integer

原因分析：
JSONObject类
public JSONObject(String source)
public JSONObject(JSONTokener x) 
中的
putOnce(key, x.nextValue());

JSONTokener类
public Object nextValue()
中的
return JSONObject.stringToValue(s);

JSONObject类
static public Object stringToValue(String s)
中的
Long myLong = new Long(s);
if (myLong.longValue() == myLong.intValue()) {
    return new Integer(myLong.intValue());
} else {
    return myLong;
}


问题解决：
JSONObject类
static public Object stringToValue(String s)
中的
Long myLong = new Long(s);
if (myLong.longValue() == myLong.intValue()) {
    return new Integer(myLong.intValue());
} else {
    return myLong;
}
改为
Long myLong = new Long(s);
if (myLong.shortValue() == myLong.byteValue()) {
    return new Byte(myLong.byteValue());
} if (myLong.intValue() == myLong.shortValue()) {
    return new Short(myLong.shortValue());
} if (myLong.longValue() == myLong.intValue()) {
    return new Integer(myLong.intValue());
} else {
    return myLong;
}

*/

