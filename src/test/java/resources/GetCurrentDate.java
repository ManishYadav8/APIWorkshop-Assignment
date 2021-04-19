package resources;

import java.util.Date;
import java.util.TimeZone;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GetCurrentDate {
	
	public static String getDate() {
	       //getting current date and time using Date class
	       DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	       df.setTimeZone(TimeZone.getTimeZone("UTC"));
	       Date dateobj = new Date();
	       return df.format(dateobj);
	    }

}
