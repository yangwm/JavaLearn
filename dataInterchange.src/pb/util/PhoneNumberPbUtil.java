/**
 * 
 */
package pb.util;

import pb.model.PhoneNumber;
import pb.protobuf.PhoneNumberProtos.PhoneNumberWrap;

/**
 * 
 * 
 * @author yangwm Oct 30, 2011 1:00:04 AM
 */
public class PhoneNumberPbUtil {
    
    public static PhoneNumberWrap toPBObject(PhoneNumber phoneNumber) {
        PhoneNumberWrap.Builder b = PhoneNumberWrap.newBuilder();
        b.setNumber(phoneNumber.getNumber());
        b.setType(PhoneTypePbUtil.toPBObject(phoneNumber.getType()));
        return b.build();
    }
    
    public static PhoneNumber toObject(PhoneNumberWrap phoneNumberWrap) {
        if (phoneNumberWrap != null) {
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setNumber(phoneNumberWrap.getNumber());
            if (phoneNumberWrap.hasType()) {
                phoneNumber.setType(PhoneTypePbUtil.toObject(phoneNumberWrap.getType()));
            }
            
            return phoneNumber;
        }
        
        return null;
    }
    
}
