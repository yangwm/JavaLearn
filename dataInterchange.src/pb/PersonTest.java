/**
 * 
 */
package pb;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pb.model.Person;
import pb.model.PhoneNumber;
import pb.model.PhoneType;
import pb.protobuf.PersonProtos.PersonWrap;
import pb.util.PersonPbUtil;
import util.EntityUtil;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * 
 * @author yangwm Oct 29, 2011 11:47:32 PM
 */
public class PersonTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Person person = new Person();
        person.setId(6659);
        person.setName("Wuming Yang");
        person.setEmail("jxfzywm@163.com");
        
        List<PhoneNumber> phoneNumberList = new ArrayList<PhoneNumber>();
        PhoneNumber workPhoneNumber = new PhoneNumber();
        workPhoneNumber.setNumber("6267-4041");
        workPhoneNumber.setType(PhoneType.WORK);
        phoneNumberList.add(workPhoneNumber);
        PhoneNumber mobilePhoneNumber = new PhoneNumber();
        mobilePhoneNumber.setNumber("13811229996");
        mobilePhoneNumber.setType(PhoneType.MOBILE);
        phoneNumberList.add(mobilePhoneNumber);
        person.setPhone(phoneNumberList);

        PersonWrap oneWrap = PersonPbUtil.toPBObject(person);
        PersonWrap twoWrap = null;
        try {
            twoWrap = PersonWrap.parseFrom(oneWrap.toByteArray());
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        
        // PersonPbUtil.toPB(pojo) 
        // PersonPbUtil.parseFromPB(bytes)
        
        System.out.println(twoWrap.toString());
        System.out.println(EntityUtil.toString(twoWrap));
        System.out.println("pbObject pb bytes length:" + twoWrap.toByteArray().length);
        System.out.println("pbObject serialization bytes length:" + encode(twoWrap).length);
        
        Person onePojo = PersonPbUtil.toObject(twoWrap);
        Person twoPojo = decode(encode(onePojo));
        System.out.println(EntityUtil.toString(twoPojo));
        System.out.println("pojo serialization bytes length:" + encode(twoPojo).length);
    }


    @SuppressWarnings("unchecked")
    public static final <T extends Serializable> T decode(byte[] bytes) {
        T t = null;
        Exception thrown = null;
        try {
            ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bytes));
            t = (T) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            thrown = e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            thrown = e;
        } catch (ClassCastException e) {
            e.printStackTrace();
            thrown = e;
        } finally {
            if (null != thrown)
                throw new RuntimeException("Error decoding byte[] data to instantiate java object - "
                        + "data at key may not have been of this type or even an object", thrown);
        }
        return t;
    }
    
    public static final <T extends Serializable> byte[] encode(T obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(obj);
            bytes = bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error serializing object" + obj + " => " + e);
        }
        return bytes;
    }

}

/*

id: 6659
name: "Wuming Yang"
email: "jxfzywm@163.com"
phone {
  number: "6267-4041"
  type: WORK
}
phone {
  number: "13811229996"
  type: MOBILE
}

pb.protobuf.PersonProtos$PersonWrap[<serialVersionUID=1,unknownFields=,alwaysUseFieldBuilders=false><memoizedSize=-1><><defaultInstance=,ID_FIELD_NUMBER=1,hasId=true,id_=6659,NAME_FIELD_NUMBER=2,hasName=true,name_=Wuming Yang,EMAIL_FIELD_NUMBER=3,hasEmail=true,email_=jxfzywm@163.com,PHONE_FIELD_NUMBER=4,phone_=[number: "6267-4041"
type: WORK
, number: "13811229996"
type: MOBILE
],memoizedSerializedSize=-1>]
pbObject pb bytes length:65
pbObject serialization bytes length:257
pb.model.Person[<serialVersionUID=1,id=6659,name=Wuming Yang,email=jxfzywm@163.com,phone=[{number=6267-4041,type=WORK}, {number=13811229996,type=MOBILE}]>]
pojo serialization bytes length:402



id: 6659
name: "Wuming Yang"
email: "jxfzywm@163.com"

pb.protobuf.PersonProtos$PersonWrap[<serialVersionUID=1,unknownFields=,alwaysUseFieldBuilders=false><memoizedSize=-1><><defaultInstance=,ID_FIELD_NUMBER=1,hasId=true,id_=6659,NAME_FIELD_NUMBER=2,hasName=true,name_=Wuming Yang,EMAIL_FIELD_NUMBER=3,hasEmail=true,email_=jxfzywm@163.com,PHONE_FIELD_NUMBER=4,phone_=[],memoizedSerializedSize=-1>]
pbObject pb bytes length:33
pbObject serialization bytes length:225
pb.model.Person[<serialVersionUID=1,id=6659,name=Wuming Yang,email=jxfzywm@163.com,phone=[]>]
pojo serialization bytes length:203

*/
