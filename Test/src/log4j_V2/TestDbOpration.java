package log4j_V2;


	/**
	 @author : kamal64
	 */

	import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	public class TestDbOpration {
		static Connection c = null;
		static Statement stmt = null;
		static PreparedStatement ps = null;
		static boolean debugFlag = false;
		static boolean warnFlag = false;
		static boolean fatalFlag = false;
		static boolean errorFlag = false;
		static boolean infoFlag = false;
		static boolean traceFlag = false;
		public static String dbName = "D:/sqlite/testDB.db";

		public static void main(String[] args) throws Exception {
			Long startmili = System.currentTimeMillis();
			operation("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d", "D:\\sqlite\\logs\\log2.out");
			Long endMili = System.currentTimeMillis();
			Long millis = endMili - startmili;
			System.out.println("End at :" + Calendar.getInstance().getTime());

			System.out.println(String.format(
					"%d min, %d sec",
					TimeUnit.MILLISECONDS.toMinutes(millis),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(millis))));
		}

		public static String operation(String datePattern, String fileLocation) {
			String begDate = "";
			String lastDate = "";
			File dbFile = new File(dbName);
			boolean b = true;
			String line = "";
			String date = "";
			String level = "";
			BufferedReader bi = null;
			// String dateFormat=reqDateFormat;

			try {
				if(dbFile.exists()){
					System.out.println("dbName :: " + dbName);
					dbFile.delete();
				}
				
				if (!dbFile.exists()) {
					// String str ="D:/sqlite/sqlite3.exe testDB1.db";
					System.out.println("dbName :: " + dbName);
					String str = dbName.substring(0, dbName.lastIndexOf("/") + 1);
					str += "sqlite3.exe "
							+ dbName.substring(dbName.lastIndexOf("/") + 1,
									dbName.length());
					System.out.println("str is :: " + str);
					Process process = Runtime.getRuntime().exec(
							new String[] { "cmd.exe", "/c", str });
					System.out.println(str);
				}
				String sql = "insert into Test1 values(?,?,?)";

//				s = new Scanner(new File(fileLocation));
				
				bi = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileLocation))));

				Class.forName("org.sqlite.JDBC");
				c = DriverManager.getConnection("jdbc:sqlite:" + dbName);
				stmt = c.createStatement();

				System.out.println("stmt");

				// stmt.executeUpdate("drop table Test1");
				// System.out.println(tableCreationSql);

//				stmt.executeUpdate("create table Test1 (DATE date,LEVEL text,MESSAGE String)");
//				stmt.executeUpdate("PRAGMA cache_size = -5120");
				
				stmt.executeUpdate("CREATE TABLE Test1 (DATE date,LEVEL text,MESSAGE String)");

				stmt.executeUpdate("PRAGMA synchronous=OFF");

				stmt.executeUpdate("PRAGMA journal_mode = WAL");
				
				stmt.executeUpdate("PRAGMA locking_mode=OFF");

				System.out.println("transaction begin");

				System.out.println("datePattern " + datePattern);

				stmt.executeUpdate("BEGIN TRANSACTION");

				ps = c.prepareStatement(sql);

//				while (s.hasNextLine()) {

				while ((line = bi.readLine()) != null){
					line = line.trim();
					if (line.indexOf("DEBUG")>0) {
						level = "DEBUG";
						debugFlag = true;
					} else if (line.indexOf("INFO")>0) {
						level = "INFO";
						infoFlag = true;
					} else if (line.indexOf("FATAL")>0) {
						level = "FATAL";
						fatalFlag = true;
					} else if (line.indexOf("ERROR")>0) {
						level = "ERROR";
						errorFlag = true;
					} else if (line.indexOf("WARN")>0) {
						level = "WARN";
						warnFlag = true;
					} else if (line.indexOf("TRACE")>0) {
						level = "TRACE";
						traceFlag = true;
					}

					Pattern p = Pattern.compile(datePattern);
					Matcher m = p.matcher(line);

					date = "";
					// System.out.println("datePattern is " + m.pattern());
					while (m.find()) {
//						System.out.println(m.start() + ">>" + m.group());
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
					// insertOperation(line,date);
					// line=line.replaceAll("[\\']", "\\\\\\'");
					// System.out.println("date>> "+date+" >>level>>"+level);

					ps.setString(1, date);
					ps.setString(2, level);
					ps.setString(3, line);
					ps.addBatch();
					ps.executeBatch();

				}
			} catch (Exception e) {
				// if (e.toString().contains("already exists"))
				// {try {
				// stmt.execute("drop table Test1");
				// } catch (SQLException e1) {
				// // e1.printStackTrace();
				// }
				// }
				// else
				e.printStackTrace();
				System.exit(1);
			}

			try {
				stmt.executeUpdate("END TRANSACTION");
				System.out.println("db size "+(dbFile.length()/1024)/1024);
				/*ResultSet rs = stmt.executeQuery("select date from Test1 limit 1");
				if (rs == null)
					System.out.println("rs is null");
				rs.next();
				begDate = rs.getString(1);
				System.out.println("beg. date is " + begDate);
				rs = null;
				rs = stmt
						.executeQuery("select date from Test1 limit (select count(date) from Test1)-1,1");
				rs.next();
				lastDate = rs.getString(1);
				System.out.println("end. date is " + lastDate);*/
				stmt.close();
				c.close();
				ps.close();
//				rs.close();
				bi.close();
			} catch (Exception e) {
				System.out.println("Exception catch ");
				e.printStackTrace();
			}
			String returnString = begDate + "#" + lastDate + "##";
			if (debugFlag)
				returnString = returnString + "DEBUG#";
			if (errorFlag)
				returnString = returnString + "ERROR#";
			if (infoFlag)
				returnString = returnString + "INFO#";
			if (warnFlag)
				returnString = returnString + "WARN#";
			if (fatalFlag)
				returnString = returnString + "FATAL#";
			if (traceFlag)
				returnString = returnString + "TRACE";
			return returnString;
		}
	}
