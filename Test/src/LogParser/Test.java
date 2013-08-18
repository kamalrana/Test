/**
@author : kamal64
*/


package LogParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

public static void main(String[] args) {
String s="-20.30d{ISO8601}{fdf}   ";
String temp="fISO8601";
int i=0;
int i1=0;
if(temp.equals("ISO8601")){
	i="yyyy-MM-dd HH:mm:ss,SSS".length();
}
else if(!(s.indexOf("}")==s.lastIndexOf("}"))){
	System.out.println("extr>>"+s.substring(s.indexOf("{")+1,s.indexOf("}")));
	i=s.substring(s.indexOf("{")+1,s.indexOf("}")).length();
}
else{
	System.out.println(s.substring(s.indexOf("{")+1, s.indexOf("}")));
	i=s.substring(s.indexOf("{")+1, s.indexOf("}")).length();
}
System.out.println("is is :"+i);
i1=s.substring(s.lastIndexOf("}")+1, s.length()).length();
System.out.println("is is :"+i1);
System.out.println("total length is >>"+(i+i1));

other(s.substring(0, s.indexOf("d")));
}

private static void other(String substring) {
	System.out.println("sbstring is "+substring);
	String ary[]=substring.split("\\.");
	System.out.println("ary length : "+ary.length);
	for (int i = 0; i < ary.length; i++) {
		String string = ary[i];
		if(string.contains("-"))
			System.out.println(string.substring(string.indexOf("-")+1));
		System.out.println("<<"+string);
	}
	Pattern p = Pattern.compile("[\\-\\.]");
	Matcher m = p.matcher(substring);
	System.out.println("Pattern is " + m.pattern());
	while (m.find()) {
		if(m.group().length()>0 && (m.group().equals(".") || m.group().equals("-"))){
			System.out.println("group is :"+m.group());
		}
		if (m.group().equals("d")) {
			if (substring.charAt(m.start() + 1) == '{') {
				String temp = substring.substring(m.start() + 2,
						substring.lastIndexOf("}"));
				System.out.println("found pattern of date >>" + temp
						+ ">>its length is >>" + temp.length());
				continue;
			}
		}
		System.out.println(m.start() + ">>" + m.group());

	}
	
}
}
