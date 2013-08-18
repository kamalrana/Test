package LogParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;

public class TableCreation {
	static Properties props = new Properties();
	static String reqKey = "";
	static String pattern = "";
	static String tableCreationSql = "create table Test1(";
	static List<Character> charList = new ArrayList();
	static String format="";
	public static void main(String[] args) {}
	public static String operation(String propertieFile,String fileName){

		pattern=getPattern(propertieFile,fileName);
		
         String[] temp = pattern.split("%");
		System.out.println("aray length : " + temp.length);
		for (int i = 0; i < temp.length - 1; i++) {
			String string = temp[i];
			string = string.replaceAll("[^a-zA-Z]", "");
			System.out.println("repace :: "+string);
			if (string.length() > 0)
				// System.out.println(string +">>:"+string.charAt(0));
				if(!charList.contains(string.charAt(0)))
					charList.add(string.charAt(0));
			}
		System.out.println(charList);
		
		String t = "";
		TreeMap<Integer, String> map = new TreeMap<>();
		for (int i = 0; i < temp.length; i++)
			{
			String string = temp[i];
			if (!(string.length() > 0 && string.charAt(0) == 'd'))
				continue;
			System.out.println(string);
			if (string.length() >= 1 && !(string.charAt(1) == '{'))
				t = "ISO8601";
			else
				t = string.substring(2, string.indexOf("}"));
			map.put(findLength(t), t);
			}
		System.out.println(map.toString());
		SortedSet<Integer> s = (SortedSet<Integer>) map.keySet();
		System.out.println(map.get(s.last()));

		format=map.get(s.last());
		
			for (Iterator iterator = charList.iterator(); iterator.hasNext();) {
			Character c = (Character) iterator.next();
				System.out.println("char is "+c);
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
					
				/*case 'c':
					tableCreationSql += "logger text,";
					break;

				case 'C':
					tableCreationSql += "class text,";
					break;

				case 'F':
					tableCreationSql += "File text,";
					break;

				case 'i':
					tableCreationSql += "location text,";
					break;

				case 'L':
					tableCreationSql += "Line text,";
					break;

				case 'M':
					tableCreationSql += "method text,";
					break;

				case 'r':
					tableCreationSql += "relative text,";
					break;

				case 't':
					tableCreationSql += "thread text,";
					break;

				case 'x':
					tableCreationSql += "NDC text,";
					break;

				case 'X':
					tableCreationSql += "key_MDC text,";
					break;*/

				default:
					break;
				}

			}
			if(tableCreationSql.charAt(tableCreationSql.length()-1)==',')
			tableCreationSql = tableCreationSql.substring(0, tableCreationSql.length() - 1);
			
			tableCreationSql += ")";
			
			System.out.println(tableCreationSql);

	return tableCreationSql;
	}
	private static String getPattern(String propertieFile,String fileName)
	    {
	    try
            {
            props.load(new FileInputStream(propertieFile));
            }
        catch (IOException e)
            {
            e.printStackTrace();
            }
		
		Set propKeySet = props.keySet();

		for (Iterator iterator1 = propKeySet.iterator(); iterator1.hasNext();) {
			String key = (String) iterator1.next();
			 System.out.println("all>>"+key);

			if (props.getProperty(key).toLowerCase()
					.endsWith("fileappender")) {
				 System.out.println("in fileappedner>> "+key);
				 System.out.println("prop chcek>> "+props.getProperty(key+".File"));
				if (props.getProperty(key + ".File").toLowerCase().endsWith(fileName)) {
					 System.out.println("key>>"+key);
					 reqKey = key;
					break;
					}
				}
			}
		for (Iterator iterator2 = propKeySet.iterator(); iterator2.hasNext();) {
			String object1 = (String) iterator2.next();
			System.out.println(object1+"<< >>"+props.getProperty(object1));
			if (object1.startsWith(reqKey) && object1.toLowerCase().contains((".layout.conversionpattern"))) {
				// System.out.println(object1);
				pattern = props.getProperty(object1);
				System.out.println("pattern is "+pattern);
				break;
			}
		}
	    return pattern;
	    }
	
	private static int findLength(String t)
		{
		System.out.println("t is : " + t);
		if (t.equals("ISO8601"))
			{
			t = "yyyy-MM-dd HH:mm:ss,SSS";
			}
		else if (t.equals("ISO8601_BASIC"))
			{
			t = "yyyyMMdd HHmmss,SSS";
			}
		else if (t.equals("ABSOLUTE"))
			{
			t = "HH:mm:ss,SSS";
			}
		else if (t.equals("DATE"))
			{
			t = "dd MMM yyyy HH:mm:ss,SSS";
			}
		else if (t.equals("COMPACT"))
			{
			t = "yyyyMMddHHmmssSSS";
			}
		return t.length();
		}
}
