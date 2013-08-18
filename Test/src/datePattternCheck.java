import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class datePattternCheck {
public static void main(String[] args) {
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	 int limit = 20;
//	System.out.println(df.toString());
	System.out.println(df.format(new Date()));
	
	String dateString = df.format(new Date());
	int ldatePattern = dateString.length();	
	
	int diff = ldatePattern-limit;
	
	System.out.println("dateString is :: "+dateString);
	String dateCut = dateString.substring(diff);
	String datePattern = "" ;
	
	if(diff>0){
		
		datePattern = dateCut.replaceAll("[0-9]", "\\\\d");
	}
	

	System.out.println("date pattern 1st conversion :: "+datePattern);
	datePattern = datePattern.replaceAll("[a-ce-zA-CE-Z]", "[a-zA-Z]");
	System.out.println("date pattern 2st conversion :: "+datePattern);
	
	 
	 
	 System.out.println(ldatePattern);
}
}
