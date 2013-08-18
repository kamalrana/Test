package LogParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class FetchDB {
	static Connection c = null;
	static Statement stmt = null;
public static void main(String[] args) {
	try {
		Long startmili = System.currentTimeMillis();
		Class.forName("org.sqlite.JDBC");
		c = DriverManager.getConnection("jdbc:sqlite:"+"D:\\sqlite\\testDB.db");
		stmt = c.createStatement();
		
		ResultSet rs = stmt.executeQuery("select message from Test1 where level='DEBUG'");
		while(rs.next()){
			System.out.println(rs.getString(1));
//			System.out.print(rs.getString(1));
//			System.out.println(rs.getString(2));
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
		rs.close();
		stmt.close();

	}catch (Exception e) {
		e.printStackTrace();
	}finally{
		
	}
	
}
}
