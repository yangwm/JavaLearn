/**
 * 
 */
package pb.util;

import java.util.ArrayList;
import java.util.List;

import pb.model.Person;
import pb.model.PhoneNumber;
import pb.protobuf.PersonProtos.PersonWrap;
import pb.protobuf.PhoneNumberProtos.PhoneNumberWrap;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * 
 * 
 * @author yangwm Oct 30, 2011 12:54:57 AM
 */
public class PersonPbUtil {
    
    public static byte[] toPB(Person person) {
        return toPBObject(person).toByteArray();
    }
    public static PersonWrap toPBObject(Person person) {
        PersonWrap.Builder b = PersonWrap.newBuilder();
        b.setId(person.getId());
        b.setName(person.getName());
        b.setEmail(person.getEmail());
        List<PhoneNumber> phoneList = person.getPhone();
        if (phoneList != null && phoneList.size() > 0) {
            for (PhoneNumber phoneNumber : phoneList) {
                b.addPhone(PhoneNumberPbUtil.toPBObject(phoneNumber));
            }
        }
        return b.build();
    }
    
    
    public static Person parseFromPB(byte[] bytes) {
        if (bytes != null) {
            try {
                PersonWrap pb = PersonWrap.parseFrom(bytes);
                return toObject(pb);
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static Person toObject(PersonWrap personWrap) {
        if (personWrap != null) {
            Person person = new Person();
            person.setId(personWrap.getId());
            person.setName(personWrap.getName());
            person.setEmail(personWrap.getEmail());
            if (personWrap.getPhoneList().size() > 0) {
                List<PhoneNumber> phoneList = new ArrayList<PhoneNumber>();
                for (PhoneNumberWrap phoneNumberWrap : personWrap.getPhoneList()) {
                    phoneList.add(PhoneNumberPbUtil.toObject(phoneNumberWrap));
                }
                person.setPhone(phoneList);
            }
            return person;
        }
        return null;
    }
    
}
