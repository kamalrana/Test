package LogParser;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Date;

public class NumOfLineTest
{
public static void main(String[] args)
	{
	/*try
	    {
	    File file= new File("D:\\sqlite\\logs\\Log_CMCM_93x_CascadeTheChangeInCostCenter_UI.txt");
	    Date d11=new Date();
	    FileInputStream fstream =
		        new FileInputStream(file);
	    DataInputStream in = new DataInputStream(fstream);
	      BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine;
		
		int i=0;
		while((strLine=br.readLine())!=null){
		i++;
		}
	    LineNumberReader lnr=new LineNumberReader(new InputStreamReader(in));
		lnr.skip(Long.MAX_VALUE);
		System.out.println(lnr.getLineNumber());
	    IndexedFileReader reader = new IndexedFileReader(file);
		System.out.println("no of lines : "+reader.getLineCount());
		Date d2=new Date();
		System.out.println((d2.getTime()-d11.getTime()));
//		System.out.println("no of lines are : "+i);
	    }
    catch (Exception e)
	    {
	    // TODO: handle exception
	    }*/
	
	String s="2013-04-24 15:51:17     INFO  (LogParserPanel.java:446) - invoking method :validateAndCreateCSV";
	String[] s1=s.split(" ");
	for (int i = 0; i < s1.length; i++)
	    {
	    String string = s1[i];
	    System.out.println("-"+string+"-");
	    }
	}
}
