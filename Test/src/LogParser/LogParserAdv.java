package LogParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;


public class LogParserAdv
{
public static void main(String[] args)
	{
		File logFile = new File("G:\\eclipse_workspaces\\core1\\Log4jProject\\src\\log4j.properties");
		try
	        {
	        String s="";
	        String reqString="";
	        String fileName="";
	        String pattern="";
	        boolean b=false;
	        boolean b1=false;
	        BufferedReader br = new BufferedReader(new FileReader(logFile));
	        Date d1= new Date();
	        while((s=br.readLine())!=null){
	        	s=s.toLowerCase();
	        	if(s.startsWith("log4j.appender") && s.endsWith("fileappender")){
	        		reqString=s.substring(0, s.indexOf("=")).trim();
	        		System.out.println("reqString is "+reqString);
	        		b=true;
	        		break;
	        		}
	        	/*
	        	if(b && s.startsWith(reqString) && (s.substring(0, s.indexOf("=")).trim()).equals(reqString+".file")){
	        	String temp=s.substring(s.indexOf("="));
	        		if(temp.contains("/")){
	        		String ss[] = temp.split("/");
	        		if(!(ss[ss.length-1].contains("/") || ss[ss.length-1].contains("\\")))
	        		System.out.println("First :"+ss[ss.length-1]);
	        		fileName=ss[ss.length-1].trim();
	        		}
	        		if(temp.contains("\\")){
	        		String ss[] = temp.split("\\\\");
	        		if(!(ss[ss.length-1].contains("/") || ss[ss.length-1].contains("\\")))
	        			System.out.println("second :"+ss[ss.length-1]);
	        		fileName=ss[ss.length-1].trim();
	        			}
	        		
	        	 	}*/
	        	}
	        s="";
	        if(b){
	        while ((s = br.readLine()) != null)
	            {
	            s=s.toLowerCase();
	        if(b && s.startsWith(reqString) && (s.substring(0, s.indexOf("=")).trim()).equals(reqString+".file")){
        	String temp=s.substring(s.indexOf("="));
        		if(temp.contains("/")){
        		String ss[] = temp.split("/");
        		if(!(ss[ss.length-1].contains("/") || ss[ss.length-1].contains("\\"))){
        		System.out.println("First :"+ss[ss.length-1]);
        		fileName=ss[ss.length-1].trim();
        			}
        		}
        		if(temp.contains("\\")){
        		String ss[] = temp.split("\\\\");
        		if(!(ss[ss.length-1].contains("/") || ss[ss.length-1].contains("\\"))){
        			System.out.println("second :"+ss[ss.length-1]);
        		fileName=ss[ss.length-1].trim();
	        	}
        	}
//        		System.out.println("fileName : "+fileName);
        		break;
        	 	}
	            }
	        }
	        s="";
	         if (b)
	            {
	            System.out.println("inside");
	            while ((s = br.readLine()) != null)
		            {
//		            System.out.println(s);
//		            s=s.toLowerCase();
		            if (s.toLowerCase().startsWith(reqString))
			            {
			            String temp = s.substring(0, s.indexOf("=")).trim();
//			            System.out.println(temp);
			            if(temp.toLowerCase().contains("layout.conversionpattern")){
			            	pattern=s.substring(s.indexOf("=")+1).trim();
			            	System.out.println("pattern is " + pattern);
				            break;
			            }
//			            reqString = s.substring(0, s.indexOf("=")).trim();
			            
			            }
		            }
	            }
	         Date d2= new Date();
	         System.out.println(d2.getTime()-d1.getTime());
	        /* String tempAry [] = pattern.split(" ");
	         for (int i = 0; i < tempAry.length; i++)
	            {
	            String string = tempAry[i];
	            	if(string.contains("%d")){
	            		if(string.charAt(string.indexOf("%d")+1) == '{'){
	            		System.out.println(string);
	            		}
	            	}
	            		
	            }*/
	         
	         
	        }
        catch (Exception e)
	        {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        }
	}
}
