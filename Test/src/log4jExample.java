import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import org.apache.log4j.Logger;

public class log4jExample {
	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(log4jExample.class.getName());
	static File file = new File("c:/sqlite/log2.out");

	public static void main(String[] args) throws IOException, SQLException {
		int i = 0, j = 0;
		int r1 = 0;
		System.out.println("begin");
		log.debug("Hello this is an debug message no. >> " + (i)
				+ " <<< Hello this is an debug message no"
				+ "Hello this is an debug message no. >> " + (i)
				+ " <<< Hello this is an debug message no");
		Random r = new Random();
		System.out.println("file complete path :: "+file.getAbsolutePath());
		if(!file.exists())
			System.exit(1);
		while ((file.length() / 1024) / 1024 <= 2) {
			r1 = r.nextInt(6);
			getThread(r1);
			i++;
			switch (r1) {
			case 0:
				log.debug("Hello this is an debug message no. >> " + (i)
						+ " <<< Hello this is an debug message no"
						+ "Hello this is an debug message no. >> " + (i)
						+ " <<< Hello this is an debug message no");
				break;
			case 1:
				log.fatal("Hello this is an fatal message no. >> " + (i)
						+ " <<< Hello this is an fatal message no"
						+ "Hello this is an fatal message no. >> " + (i)
						+ " <<< Hello this is an fatal message no");
				break;
			case 2:
				log.warn("Hello this is an warn message no. >> " + (i)
						+ " <<< Hello this is an warn message no"
						+ "Hello this is an warn message no. >> " + (i)
						+ " <<< Hello this is an warn message no");
				break;
			case 3:
				log.trace("Hello this is an trace message no. >> " + (i)
						+ " <<< Hello this is an trace message no"
						+ "Hello this is an trace message no. >> " + (i)
						+ " <<< Hello this is an trace message no");
				break;
			case 4:
				if (j++ > 1000)
					continue;
				log.error("Hello this is an error message no. >> " + (i)
						+ " <<< Hello this is an error message no"
						+ "Hello this is an error message no. >> " + (i)
						+ " <<< Hello this is an error message no");
			break;
			case 5:
				log.info("Hello this is an info message no. >> " + (i)
						+ " <<< Hello this is an info message no"
						+ "Hello this is an info message no. >> " + (i)
						+ " <<< Hello this is an info message no");
			break;
			default:
				break;
			}
			System.out.println(i + " - " + j);
		}

	}

	private static void getThread(int nextInt) {
		switch (nextInt) {
		case 0:
			Thread.currentThread().setName("kamal");
			break;
		case 1:
			Thread.currentThread().setName("kishore");
			break;
		case 2:
			Thread.currentThread().setName("rana");
			break;
		default:
			break;
		}

	}
}