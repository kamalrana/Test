package LogParser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LogFileCreation
{
public static void main(String[] args)
	{
	try
	    {
	    File file=new File("c:\\sqlite\\logs\\log2.out");
	    if(!file.exists())
	    	file.createNewFile();
	    
	    BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
	    String s="[rgulati] [DEBUG] [rule] FQAppvEngine.generateAuditRuleDescriptionParameter - now description : \"Cost Center was updated from by+ gulati";
	    /*long fileSizeInBytes = null;
	 // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
	 long fileSizeInKB = fileSizeInBytes / 1024;
	 // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
	 long fileSizeInMB = fileSizeInKB / 1024;*/
	 int i=0;
	 DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
	 Date d=new Date();
	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 Calendar c = Calendar.getInstance();
	 c.setTime(d);
	    while((file.length()/1024)/1024<=1000){
	    System.out.println((i++));
	    c.add(Calendar.DATE, 1);  // number of days to add
	    bw.write(df.format(c.getTime())+" "+s+">> <<"+(i)+"  "+"\n");
	    }
	    }
    catch (Exception e)
	    {
e.printStackTrace();
	    }
	}
}
