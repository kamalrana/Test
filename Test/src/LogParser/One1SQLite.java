package LogParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.sqlite.SQLiteConfig;

public class One1SQLite
{
	public static void main(String[] args)
		{
		Connection c = null;
	    Statement stmt = null;
		try
			{
			Date d1=new Date();
//			File file = new File("D:\\sqlite\\logs\\Log_CMCM_93x_CascadeTheChangeInCostCenter_UI.txt");
			File file=new File("D:\\sqlite\\logs\\loging.log");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String s = "";
			
			Long startmili = System.currentTimeMillis();
			System.out.println("Start at :"+Calendar.getInstance().getTime());
			Class.forName("org.sqlite.JDBC");
//		      c = DriverManager.getConnection("jdbc:sqlite:"+"D:\\sqlite\\testDB.db");
			 SQLiteConfig liteConfig = new SQLiteConfig();
		     liteConfig.enableFullSync(false);
		     
			  c = DriverManager.getConnection("jdbc:sqlite:"+"D:\\sqlite\\testDB.db",liteConfig.toProperties());
		      c.setAutoCommit(false);
		      System.out.println("Opened database successfully");
		      PreparedStatement ps = c.prepareStatement("INSERT INTO Test_Data1 VALUES(?,?,?,?,?,?)");
		      
		      stmt = c.createStatement();
//		      stmt.execute("delete from Test_Data1");
//		      boolean b=   stmt.execute("select count(*) from Test_Data1");
//		      System.out.println("statemnt executed : "+b);

		      while ((s = br.readLine()) != null)
				{
				String [] sepAray=s.split(" ");
//				System.out.println(sepAray.length);
//				System.out.println(s);
//				for (int i = 0; i < sepAray.length; i++)
//	                {
//	                String string = sepAray[i];
//	                System.out.print(string);
//	                System.out.print("--");
//	                }
//				System.out.println("============");
				if(sepAray.length<4)
					continue;
				String date=sepAray[0]+" "+sepAray[1];
				String user=sepAray[2].replaceAll("\\W", "");
				String type=sepAray[3].replaceAll("\\W", "");
				String loger=sepAray[4].replaceAll("\\W", "");
				String Class=sepAray[5];
				String msg=s.substring(s.indexOf(Class)+Class.length()+3);
				System.out.println("date : "+date+" _ user : "+user+" _type : "+type+" _loger : "+loger+" _Class : "+Class+" _msg : "+msg);
				/*if (s.contains("[DEBUG]"))
					{
					ps.setString(2, s);
					
					}
				else if (s.contains("[INFO]"))
					{
					ps.setString(3, s);
					}
				else if (s.contains("[ERROR]"))
					{
//					System.out.println(s);
					ps.setString(1, s);
					}
					*/
				ps.setString(1, date);
				ps.setString(2, user);
				ps.setString(3, type);
				ps.setString(4, loger);
				ps.setString(5, Class);
				ps.setString(6, msg);
				/*ps.addBatch();
				ps.executeBatch();*/
				}
			
			ps.close();
			stmt.close();
//			 c.commit();
			c.close();
			
			Long endMili = System.currentTimeMillis();
			Long millis = endMili - startmili;
			System.out.println("End at :"+Calendar.getInstance().getTime());
			 
			System.out.println(String.format("%d min, %d sec", 
					    TimeUnit.MILLISECONDS.toMinutes(millis),
					    TimeUnit.MILLISECONDS.toSeconds(millis) - 
					    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
					));
			Date d2=new Date();
			System.out.println("time is : "+(d2.getTime()-d1.getTime()));
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}
}
