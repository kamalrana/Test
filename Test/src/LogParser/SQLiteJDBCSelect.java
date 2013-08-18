package LogParser;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class SQLiteJDBCSelect
	{
	  public static void main( String args[] )
	  {
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+"c:\\sqlite\\testDB.db");
	      c.setAutoCommit(false);
	      System.out.println("Opened database successfully");

	      stmt = c.createStatement();
//	      stmt.executeUpdate("delete from Test_Data1");
	      ResultSet rs=stmt.executeQuery("select tables from testDB.db");
//	      ResultSet rs = stmt.executeQuery( "SELECT count(date),count(User),count(MessageType),count(Logger),count(class),count(message) FROM Test_Data1;" );
	      while(rs.next()){
	      System.out.println(rs.getString(1));
	      }
	      /*String ERROR = rs.getString(1);
	      String DEBUG = rs.getString(2);
	      String INFO = rs.getString(3);
	      String INFO1 = rs.getString(4);
	      String INFO2 = rs.getString(5);
	      String INFO3 = rs.getString(6);
	         System.out.println( "ERROR = " + ERROR.length());
	         System.out.println( "DEBUG = " + DEBUG.length());
	         System.out.println( "INFO = " + INFO.length());
	         System.out.println( "INFO = " + INFO1.length());
	         System.out.println( "INFO = " + INFO2.length());
	         System.out.println( "INFO = " + INFO3.length());*/
	         
	      /*while ( rs.next() ) {
	      String ERROR = rs.getString(1);
	      String DEBUG = rs.getString(2);
	      String INFO = rs.getString(3);
	      String INFO1 = rs.getString(4);
	      String INFO2 = rs.getString(5);
	      String INFO3 = rs.getString(6);
	         System.out.println( "ERROR = " + ERROR.length());
	         System.out.println( "DEBUG = " + DEBUG.length());
	         System.out.println( "INFO = " + INFO.length());
	         System.out.println( "INFO = " + INFO1.length());
	         System.out.println( "INFO = " + INFO2.length());
	         System.out.println( "INFO = " + INFO3.length());
	         System.out.println();
	      }*/
	      rs.close();
	      stmt.close();
	      c.commit();
	      c.close();
	    } catch ( Exception e ) {
	    e.printStackTrace();
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    System.out.println("Operation done successfully");
	  }
	}