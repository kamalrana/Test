/**
@author : kamal64
*/


package LogParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternCal {
public static void main(String[] args) throws FileNotFoundException, IOException {
	String s="%5r %10C %d{yyyy-MM-dd HH:mm:ss} [%c{1}] %d{ABSOLUTE} [%p] %m%n";
	s=s.trim();
	System.out.println("===================");
	if(s.endsWith("%n")){
		s=s.substring(0, s.length()-2).replaceAll("[\\[\\]]", "");
		System.out.println(s);
	}
	System.out.println("===================");
	List propPatternString = new ArrayList();
	String ss[]=s.split("%");
	for (int i = 0; i < ss.length; i++) {
		String string = ss[i];
		if(string.length()>0)
			propPatternString.add(string);
		}
	System.out.println(propPatternString);
	HashMap<String, String> map=new HashMap<>();
	for (Iterator iterator = propPatternString.iterator(); iterator.hasNext();) {
		String object = (String) iterator.next();
		System.out.println("object is >>"+object);
if (object.contains("d")) {
	System.out.println("d object is >>"+object);
		if(object.indexOf("{")!=-1){
			String temp=object.substring(object.indexOf("{")+1, object.indexOf("}"));
//		System.out.println(temp);
			getLength(temp,object);
		}
}
/*else{
	//		System.out.println(object.charAt(0)+">>"+new Integer(object.charAt(0)+""));
	Pattern p = Pattern.compile("[\\-\\.\\d\\w]");
	Matcher m = p.matcher(object);
	System.out.println("Pattern is " + m.pattern());
	while (m.find()) {
		if(m.group().length()>0 && (m.group().equals(".") || m.group().equals("{") || m.group().equals("-"))){
			System.out.println("group is :"+m.group());
		}
		if (m.group().equals("d")) {
			if (object.charAt(m.start() + 1) == '{') {
				String temp = object.substring(m.start() + 2,
						object.lastIndexOf("}"));
				System.out.println("found pattern of date >>" + temp
						+ ">>its length is >>" + temp.length());
				continue;
			}
		}
		System.out.println(m.start() + ">>" + m.group());

	}
}*/
		try {
			char [] Cary=object.toCharArray();
			for (int i = 0; i < Cary.length; i++) {
				char c = Cary[i];
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}

private static void getLength(String temp, String object) {
System.out.println(temp);
System.out.println(object);
if(temp.equals("ISO8601")){
	System.out.println("yyyy-MM-dd HH:mm:ss,SSS".length());
}
System.out.println(object);
	
}
}
