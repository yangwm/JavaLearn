/**
 * 
 */
package pb.util;

import pb.model.PhoneType;
import pb.protobuf.PhoneTypeProtos.PhoneTypeWrap;

/**
 * 
 * 
 * @author yangwm Oct 30, 2011 1:05:53 AM
 */
public class PhoneTypePbUtil {
    
    public static PhoneTypeWrap toPBObject(PhoneType phoneType) {
        if (phoneType == PhoneType.MOBILE) {
            return PhoneTypeWrap.valueOf(0);
        } else if (phoneType == PhoneType.HOME) {
            return PhoneTypeWrap.valueOf(1);
        } else if (phoneType == PhoneType.WORK) {
            return PhoneTypeWrap.valueOf(2);
        }
        
        return null;
    }
    
    public static PhoneType toObject(PhoneTypeWrap phoneTypeWrap) {
        if (phoneTypeWrap != null) {
            if (phoneTypeWrap == PhoneTypeWrap.MOBILE) {
                return PhoneType.MOBILE;
            } else if (phoneTypeWrap == PhoneTypeWrap.HOME) {
                return PhoneType.HOME;
            } else if (phoneTypeWrap == PhoneTypeWrap.WORK) {
                return PhoneType.WORK;
            }
        }
        
        return null;
    }
    
}
