package basics.learning.objects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static Date stringToDate(String dateStr){
		Date date = null;	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    if(dateStr==null) {
	      return new Date();
	    }	    
	    try {
	      date = sdf.parse(dateStr);
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    return  date;
	}
}
