/**
@author : kamal64
 */

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import org.apache.log4j.Logger;

public class log4jExample_v2 {
	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(log4jExample_v2.class.getName());
	static File file = new File("G:\\usr\\home\\log4j\\log1.out");

	public static void main(String[] args) throws IOException, SQLException {
		int i = 0, j = 0;
		int r1 = 0;
		System.out.println("begin");

/*		log.debug("Hello this is an debug message no. >> " + (i)
						+ " <<< Hello this is an debug message no"
						+ "Hello this is an debug message no. >> " + (i)
						+ " <<< Hello this is an debug message no");

				log.fatal("Hello this is an fatal message no. >> " + (i)
						+ " <<< Hello this is an fatal message no"
						+ "Hello this is an fatal message no. >> " + (i)
						+ " <<< Hello this is an fatal message no");

				log.warn("Hello this is an warn message no. >> " + (i)
						+ " <<< Hello this is an warn message no"
						+ "Hello this is an warn message no. >> " + (i)
						+ " <<< Hello this is an warn message no");*/

				try {
					throw new Throwable("custom exception ");
					
				} catch (Throwable e) {
					log.trace("hello",e);
//					e.printStackTrace();
				}

				/*log.error("Hello this is an error message no. >> " + (i)
						+ " <<< Hello this is an error message no"
						+ "Hello this is an error message no. >> " + (i)
						+ " <<< Hello this is an error message no");

				log.info("Hello this is an info message no. >> " + (i)
						+ " <<< Hello this is an info message no"
						+ "Hello this is an info message no. >> " + (i)
						+ " <<< Hello this is an info message no");*/

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