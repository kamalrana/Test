/**
@author : kamal64
*/
package LogParser;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBOperation {
	static Connection c = null;
	static Statement stmt = null;
	static PreparedStatement ps=null;
public static void main(String[] args) throws Exception {}

	public static void operation(String datePattern, String fileLocation,
			String tableCreationSql) {
		Scanner s = null;
		File f = new File("D:\\sqlite\\testDB.db");
		boolean b = true;
		String object = "";
		String date = "";
		String level = "";
		// String dateFormat=reqDateFormat;

		try {
			String sql = "insert into Test1 values(?,?,?)";
			
			s = new Scanner(new File(fileLocation));
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:"+"D:\\sqlite\\testDB.db");
			stmt = c.createStatement();
			
			System.out.println("stmt");
			
//			 stmt.executeUpdate("drop table Test1");
			System.out.println(tableCreationSql);
			
//			stmt.executeUpdate(tableCreationSql);
			
			stmt.executeUpdate("PRAGMA synchronous=OFF");
			
			stmt.executeUpdate("PRAGMA locking_mode=OFF");
			
			System.out.println("transaction begin");
			
			System.out.println("datePattern " + datePattern);
			
			stmt.executeUpdate("BEGIN TRANSACTION");
			
			ps = c.prepareStatement(sql);
			
			while (s.hasNextLine()) {
				object = s.nextLine().trim();

				if (object.contains("DEBUG")) {
					level = "DEBUG";
				} else if (object.contains("INFO")) {
					level = "INFO";
				} else if (object.contains("FATAL")) {
					level = "FATAL";
				} else if (object.contains("ERROR")) {
					level = "ERROR";
				} else if (object.contains("WARN")) {
					level = "WARN";
				} else if (object.contains("TRACE")) {
					level = "TRACE";
				}
				
				Pattern p = Pattern.compile(datePattern);
				Matcher m = p.matcher(object);
				
				date = "";
				// System.out.println("datePattern is " + m.pattern());
				while (m.find()) {
					// System.out.println(m.start() + ">>" + m.group());
					date = m.group();
					break;
				}
				if (date == "")
					continue;

				/*
				 * if(b && (f.length()/1024)/1024==200){
				 * System.out.println("transaction eneded");
				 * stmt.executeUpdate("END TRANSACTION");
				 * System.out.println("transaction begin again");
				 * stmt.executeUpdate("BEGIN TRANSACTION"); b=false; }
				 */
				// insertOperation(object,date);
				// object=object.replaceAll("[\\']", "\\\\\\'");
				// System.out.println("date>> "+date+" >>level>>"+level);
				
				ps.setString(1, date);
				ps.setString(2, level);
				ps.setString(3, object);
				ps.addBatch();
				ps.executeBatch();
				
				
			}
		} catch (Exception e) {
			if (e.toString().contains("already exists"))
				try {
					stmt.execute("drop table Test1");
				} catch (SQLException e1) {
					// e1.printStackTrace();
				}
			// e.printStackTrace();
		}

		try {
			stmt.executeUpdate("END TRANSACTION");
			stmt.close();
			c.close();
			ps.close();
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
