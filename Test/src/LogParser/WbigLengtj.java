/**
@author : kamal64
 */

package LogParser;

import java.util.SortedSet;
import java.util.TreeMap;

public class WbigLengtj
{
	public static void main(String[] args)
		{
		}

	public static String operation(String pattrn)
		{
		// String
		// pattrn="%5r %10C %d{yyyy-MM-dd HH:mm:ss  sss} %c{1} %d{ABSOLUTE}  %d{ISO8601} %d [%p] %m%n";
		String[] pp = pattrn.split("%");
		String t = "";
		TreeMap<Integer, String> map = new TreeMap<>();
		for (int i = 0; i < pp.length; i++)
			{
			String string = pp[i];
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
		return map.get(s.last());
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
