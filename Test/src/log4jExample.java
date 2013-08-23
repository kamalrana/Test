import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

import org.apache.log4j.Logger;

public class log4jExample {
	/* Get actual class name to be printed on */
	static Logger log = Logger.getLogger(log4jExample.class.getName());
	static File file = new File("D:/sqlite/TwoMbLog.out");

	public static void main(String[] args) throws IOException, SQLException {
		int i = 0, j = 0;
		int r1 = 0;
		System.out.println("begin");
//		log.debug("Hello this is an debug message no. >> " + (i)
//				+ " <<< Hello this is an debug message no"
//				+ "Hello this is an debug message no. >> " + (i)
//				+ " <<< Hello this is an debug message no");
		Random r = new Random();
		System.out.println("file complete path :: "+file.getAbsolutePath());
		String s="javax.servlet.ServletException: Something bad happened \n"+
    "                           at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:60) \n"+
    "                           at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157) \n"+
    "                           at com.example.myproject.ExceptionHandlerFilter.doFilter(ExceptionHandlerFilter.java:28) \n"+
    "                           at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157) \n"+
    "                           at com.example.myproject.OutputBufferFilter.doFilter(OutputBufferFilter.java:33) \n"+
    "                           at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1157) \n"+
    "                           at org.mortbay.jetty.servlet.ServletHandler.handle(ServletHandler.java:388) \n"+
    "                           at org.mortbay.jetty.security.SecurityHandler.handle(SecurityHandler.java:216) \n"+
    "                           at org.mortbay.jetty.servlet.SessionHandler.handle(SessionHandler.java:182) \n"+
    "                           at org.mortbay.jetty.handler.ContextHandler.handle(ContextHandler.java:765) \n"+
    "                           at org.mortbay.jetty.webapp.WebAppContext.handle(WebAppContext.java:418) \n"+
    "                           at org.mortbay.jetty.handler.HandlerWrapper.handle(HandlerWrapper.java:152) \n"+
    "                           at org.mortbay.jetty.Server.handle(Server.java:326) \n"+
    "                           at org.mortbay.jetty.HttpConnection.handleRequest(HttpConnection.java:542) \n"+
    "                           at org.mortbay.jetty.HttpConnection$RequestHandler.content(HttpConnection.java:943) \n"+
    "                           at org.mortbay.jetty.HttpParser.parseNext(HttpParser.java:756) \n"+
    "                           at org.mortbay.jetty.HttpParser.parseAvailable(HttpParser.java:218) \n"+
    "                           at org.mortbay.jetty.HttpConnection.handle(HttpConnection.java:404) \n"+
    "                           at org.mortbay.jetty.bio.SocketConnector$Connection.run(SocketConnector.java:228) \n"+
    "                           at org.mortbay.thread.QueuedThreadPool$PoolThread.run(QueuedThreadPool.java:582) \n"+
"Caused by: com.example.myproject.MyProjectServletException \n"+
    "                           at com.example.myproject.MyServlet.doPost(MyServlet.java:169) \n"+
    "                           at javax.servlet.http.HttpServlet.service(HttpServlet.java:727) \n"+
    "                           at javax.servlet.http.HttpServlet.service(HttpServlet.java:820) \n"+
    "                           at org.mortbay.jetty.servlet.ServletHolder.handle(ServletHolder.java:511) \n"+
    "                           at org.mortbay.jetty.servlet.ServletHandler$CachedChain.doFilter(ServletHandler.java:1166) \n"+
    "                           at com.example.myproject.OpenSessionInViewFilter.doFilter(OpenSessionInViewFilter.java:30)";
		if(!file.exists())
			System.exit(1);
		while ((file.length() / 1024) / 1024 <= 2) {
			r1 = r.nextInt(6);
			getThread(r1);
			i++;
			int k = r.nextInt(2);
			switch (r1) {
			case 0:
				System.out.println(k);
				if(k==0)
				log.debug(i+"<< >> "+s);
				else
					log.debug("Hello this is an debug message no. >> " + (i)
							+ " <<< Hello this is an debug message no"
							+ "Hello this is an debug message no. >> " + (i)
							+ " <<< Hello this is an debug message no");
				break;
			case 1:
				System.out.println(k);
				if(k==0)
					log.fatal("Hello this is an fatal message no. >> " + (i)
							+ " <<< Hello this is an fatal message no"
							+ "Hello this is an fatal message no. >> " + (i)
							+ " <<< Hello this is an fatal message no");
					else
						log.fatal(i+"<< >> "+s);
				break;
			case 2:
				System.out.println(k);
				if(k==0)
					log.warn("Hello this is an warn message no. >> " + (i)
							+ " <<< Hello this is an warn message no"
							+ "Hello this is an warn message no. >> " + (i)
							+ " <<< Hello this is an warn message no");
					else
				log.warn(i+"<< >> "+s);
				break;
			case 3:
				System.out.println(k);
				if(k==0)
					log.trace("Hello this is an trace message no. >> " + (i)
							+ " <<< Hello this is an trace message no"
							+ "Hello this is an trace message no. >> " + (i)
							+ " <<< Hello this is an trace message no");
					else
				log.trace(i+"<< >> "+s);
				break;
			case 4:
				System.out.println(k);
				if (j++ > 1000){
					continue;
				}
				if(k==0)
					log.error("Hello this is an error message no. >> " + (i)
							+ " <<< Hello this is an error message no"
							+ "Hello this is an error message no. >> " + (i)
							+ " <<< Hello this is an error message no");
					else
				log.error(i+"<< >> "+s);
			break;
			case 5:
				System.out.println(k);
				if(k==0)
					log.info("Hello this is an info message no. >> " + (i)
							+ " <<< Hello this is an info message no"
							+ "Hello this is an info message no. >> " + (i)
							+ " <<< Hello this is an info message no");
					else
				log.info(i+"<< >> "+s);
			break;
			default:
				break;
			}
//			System.out.println(i + " - " + j);
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