/**
@author : kamal64
 */

package log4j_V2;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;

public class LogParserAdv1 {
	static Properties props = new Properties();
	static String reqKey = "";
	// static String pattern = "";
	static String tableCreationSql = "create table Test1(";
	static List<Character> charList = new ArrayList();
	static String reqDateFormat = "";
	static TreeMap<Integer, TreeMap<String,String>> patternMap = new TreeMap<>();
	static TreeMap<String, String> tempMap = new TreeMap<>();
	static String conversionPattern="";
	
	public static void main(String[] args) {
		
		String propertieFile = "C:\\sqlite\\log4j.properties";
		String fileName = "";
		String logFileLocation = "C:/sqlite\\system2.log";

		Long startmili = System.currentTimeMillis();

		// starts Getting file name from complete path of log file
		if (logFileLocation.contains("/")) {
			String temp[] = logFileLocation.split("/");
			if (!(temp[temp.length - 1].contains("/") || temp[temp.length - 1]
					.contains("\\")))
				fileName = temp[temp.length - 1];
			System.out.println("first if condition >> " + fileName);
		}
		if (logFileLocation.contains("\\")) {
			String temp[] = logFileLocation.split("\\\\");
			if (!(temp[temp.length - 1].contains("/") || temp[temp.length - 1]
					.contains("\\")))
				fileName = temp[temp.length - 1];
			System.out.println("second if condition >> " + fileName);
		}
		// end's Getting file name from complete path of log file

		// getting conversion pattern from log4j property file
		conversionPattern = getConversionPattern(propertieFile, fileName);

                beginOperation(startmili , conversionPattern,logFileLocation);

	}

	private static String createTableQuery_Format(String conversionPattern) {

		String[] temp = conversionPattern.split("%");
		// System.out.println("aray length : " + temp.length);
		String dateType = "";
		
		for (int i = 0; i < temp.length - 1; i++) {
			
			String s = temp[i];
			
			// replacing anything other then alphabets with empty string
			s = s.replaceAll("[^a-zA-Z]", "");
			
			 System.out.println("repace :: "+s);
			 
			if (s.length() > 0){
				if (!charList.contains(s.charAt(0)))
					charList.add(s.charAt(0));
				}
			
			if (!(s.length() > 0 && s.charAt(0) == 'd'))
				continue;
			
			if (s.length() > 1 && !(s.charAt(1) == '{'))
				dateType = "ISO8601";
			
			else if(s.length()>=2)
				dateType = s.substring(2, s.indexOf("}"));

			System.out.println("datetype is :: "+dateType);
			
			LogParserAdv1.refineDateFormat(dateType);
		}

		// sorted set of int to get hightest length pattern
		SortedSet<Integer> s = (SortedSet<Integer>) patternMap.keySet();
		
		String pattern="";
		String orignalName="";
		int patternLength=0;
		int limitLengthTo=0;
		
		if (patternMap.size() > 0) {
			System.out.println("mpa is " + patternMap.toString());

			// map >> [ 20, [pattern , PatternName ] ] 
			patternLength = patternMap.firstKey();

			pattern = patternMap.get(s.last()).firstKey();
			
			LogParserAdv1.reqDateFormat = pattern;
			
			orignalName = patternMap.get(s.last()).get(pattern);
			
			String diffString = conversionPattern.substring(0, conversionPattern.indexOf(orignalName));
			
			if(diffString.contains(".")){
				diffString=diffString.replaceAll("[^.\\d]", "");
				System.out.println("that is "+diffString);
				limitLengthTo = Integer.valueOf(diffString.substring(diffString.indexOf(".")+1));
				
				System.out.println(limitLengthTo);
				
				if(patternLength>limitLengthTo){
					
					int diff=patternLength-limitLengthTo;
					
					LogParserAdv1.reqDateFormat = pattern.substring(diff, patternLength);
					
					}
				}
		else{
			System.out.println("map is empty no suitable date format is found");
			}
		}
		System.out.println("final "+reqDateFormat);
//		conversionPattern.subSequence(conversionPattern.indexOf(reqDateFormat),)
//		System.exit(1);

		for (Iterator iterator = charList.iterator(); iterator.hasNext();) {
			Character c = (Character) iterator.next();
			System.out.println("char is " + c);
			switch (c) {
			case 'p':
				tableCreationSql += "level text,";
				break;

			case 'd':
				tableCreationSql += "date DATE,";
				break;

			case 'm':
				tableCreationSql += "message bolb,";
				break;

			/*
			 * case 'c': tableCreationSql += "logger text,"; break;
			 * 
			 * case 'C': tableCreationSql += "class text,"; break;
			 * 
			 * case 'F': tableCreationSql += "File text,"; break;
			 * 
			 * case 'i': tableCreationSql += "location text,"; break;
			 * 
			 * case 'L': tableCreationSql += "Line text,"; break;
			 * 
			 * case 'M': tableCreationSql += "method text,"; break;
			 * 
			 * case 'r': tableCreationSql += "relative text,"; break;
			 * 
			 * case 't': tableCreationSql += "thread text,"; break;
			 * 
			 * case 'x': tableCreationSql += "NDC text,"; break;
			 * 
			 * case 'X': tableCreationSql += "key_MDC text,"; break;
			 */

			default:
				break;
			}

		}
		if (tableCreationSql.charAt(tableCreationSql.length() - 1) == ',')
			tableCreationSql = tableCreationSql.substring(0,
					tableCreationSql.length() - 1);

		tableCreationSql += ")";

		System.out.println(tableCreationSql);

		return tableCreationSql;
	}

	private static String getConversionPattern(String propertieFile, String fileName) {
		String conversionPattern = "";
		try {
			FileInputStream fis = new FileInputStream(propertieFile);
			
			// read the property file
			props.load(fis);

			// getting key set from file 
			Set propKeySet = props.keySet();

			// first iteration through property file to get req. key
            for (Iterator iterator1 = propKeySet.iterator(); iterator1 .hasNext();) {
                        String key = (String) iterator1.next();

                        System.out.println("all>>" + key);

                        if (props.getProperty(key).toLowerCase().endsWith("fileappender")) {

                            System.out.println("in fileappedner>> " + key);

                            System.out.println("prop chcek>> " + props.getProperty(key + ".File"));

                            if (props.getProperty(key + ".File").toLowerCase()
                                    .endsWith(fileName)) {
                                System.out.println("Got required key, breaking >>" + key);
                                reqKey = key;
                                break;
                            }

                        }
                    }
			
			// second iteration to get conversion pattern
			for (Iterator iterator2 = propKeySet.iterator(); iterator2 .hasNext();) {
				
				String key = (String) iterator2.next();
				
				System.out.println(key + "<< >>" + props.getProperty(key));
				
				if (key.startsWith(reqKey) && key.toLowerCase().contains( (".layout.conversionpattern"))) 
				{
					// System.out.println(object1);
					conversionPattern = props.getProperty(key);
					
					System.out.println("req. conversion pattern is " + conversionPattern);
					break;
				}
			}
			
			// very essential close of input stream.
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return conversionPattern;
	}

	public static void refineDateFormat(String orignal) {
		System.out.println("orignal is :: "+orignal);

		// clearing tempMap on each iteration, avoiding creation of new map object
		if(tempMap.size()>0)
			tempMap.clear();
			
		int length=0;
		String t1="";
		
		if (orignal.equals("ISO8601")) {
			t1= "yyyy-MM-dd HH:mm:ss,SSS";
			length=t1.length();
		} else if (orignal.equals("ISO8601_BASIC")) {
			t1 = "yyyyMMdd HHmmss,SSS";
			length=t1.length();
		} else if (orignal.equals("ABSOLUTE")) {
			t1 = "HH:mm:ss,SSS";
			length=t1.length();
		} else if (orignal.equals("DATE")) {
			t1 = "dd MMM yyyy HH:mm:ss,SSS";
			length=t1.length();
		} else if (orignal.equals("COMPACT")) {
			t1 = "yyyyMMddHHmmssSSS";
			length=t1.length();
		}

		// if pattern given is customized then find length of pattern according to alphabets. ex. yyyy-MM-dd HH:mm:ss,SSS --> [y , M , d , H , m , s , S]
		else{
		char c[] = orignal.replaceAll("[^a-zA-Z]", "").toCharArray();
		Set s=new TreeSet();
				for (int i = 0; i < c.length; i++) {
					char d = c[i];
					s.add(d);
				}
				System.out.println("proper format is :: "+s.toString());
				length=s.size();
		}
		
		// yyyy-MM-dd HH:mm:ss,SSS , ISO8601
		tempMap.put(t1, orignal);
		
		patternMap.put(length, tempMap);
	}

    public static void beginOperation(long startmili, String conversionPattern, String logFileLocation) {
    
		
		String tableCreationSql = createTableQuery_Format(conversionPattern);

		System.out.println("table Creation Sql is >>" + tableCreationSql);

		System.out.println("Pattern is " + conversionPattern);

		DateFormat df = new SimpleDateFormat(reqDateFormat);
		// System.out.println("1>>"+df.format(d));
		String datePattern = df.format(new Date()).replaceAll("[0-9]", "\\\\d") .replaceAll("[a-ce-zA-CE-Z]", "[a-zA-Z]");

		System.out.println("date pattern :: "+datePattern);
		
		DBOperation.operation(datePattern, logFileLocation, tableCreationSql);

		Long endMili = System.currentTimeMillis();
		Long millis = endMili - startmili;
		System.out.println("End at :" + Calendar.getInstance().getTime());

		System.out.println(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis))));
                
    }

}
