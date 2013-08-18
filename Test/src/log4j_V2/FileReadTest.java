package log4j_V2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileReadTest {
private static String level;
private static boolean debugFlag;
private static boolean infoFlag;
private static boolean fatalFlag;
private static boolean errorFlag;
private static boolean warnFlag;
private static boolean traceFlag;

public static void main(String[] args) {
	try {
		Long startmili = System.currentTimeMillis();
		BufferedReader	bi = new BufferedReader(new InputStreamReader(new FileInputStream(new File("c:\\sqlite\\log.out"))));
//		Scanner s = new Scanner(new File("c:\\sqlite\\log.out"));
		String line=null;
		while ((line = bi.readLine()) != null){
			line = line.trim();
			System.out.println(line);
			if (line.contains("DEBUG")) {
				level = "DEBUG";
				debugFlag = true;
			} else if (line.contains("INFO")) {
				level = "INFO";
				infoFlag = true;
			} else if (line.contains("FATAL")) {
				level = "FATAL";
				fatalFlag = true;
			} else if (line.contains("ERROR")) {
				level = "ERROR";
				errorFlag = true;
			} else if (line.contains("WARN")) {
				level = "WARN";
				warnFlag = true;
			} else if (line.contains("TRACE")) {
				level = "TRACE";
				traceFlag = true;
			}
		
		/*while (s.hasNextLine()) {
			String object = s.nextLine().trim();
//			System.out.println("object is :: " + object);
			if (object.contains("DEBUG")) {
				level = "DEBUG";
				debugFlag = true;
			} else if (object.contains("INFO")) {
				level = "INFO";
				infoFlag = true;
			} else if (object.contains("FATAL")) {
				level = "FATAL";
				fatalFlag = true;
			} else if (object.contains("ERROR")) {
				level = "ERROR";
				errorFlag = true;
			} else if (object.contains("WARN")) {
				level = "WARN";
				warnFlag = true;
			} else if (object.contains("TRACE")) {
				level = "TRACE";
				traceFlag = true;
			}*/
			
			Pattern p = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d");
			Matcher m = p.matcher(line);

			String date = "";
			// System.out.println("datePattern is " + m.pattern());
			while (m.find()) {
//				System.out.println(m.start() + ">>" + m.group());
				date = m.group();
				break;
			}
			if (date == "")
				continue;
		}
		Long endMili = System.currentTimeMillis();
		Long millis = endMili - startmili;
		System.out.println("End at :" + Calendar.getInstance().getTime());

		System.out.println(String.format(
				"%d min, %d sec",
				TimeUnit.MILLISECONDS.toMinutes(millis),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
								.toMinutes(millis))));
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
