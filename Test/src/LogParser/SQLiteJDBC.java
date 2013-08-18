package LogParser;

import java.sql.*;
import java.util.Date;

public class SQLiteJDBC
{
  public static void main( String args[] )
  {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:"+"D:\\sqlite\\testDB.db");
      System.out.println("Opened database successfully");
      String sql="";
      stmt = c.createStatement();
      System.out.println(stmt.execute("drop table Test_Data2"));
      sql = "CREATE TABLE Test_Data2 " +
                   "(date           text   , " +
                   "User           text   , " +
                   "MessageType           text   , " +
                   "Logger           text   , " +
                   "class           text   , " +
                   "message           BLOB)";
      stmt.executeUpdate(sql);
      stmt.executeUpdate("PRAGMA synchronous=OFF");
      stmt.executeUpdate("PRAGMA locking_mode=OFF");
      stmt.executeUpdate("BEGIN TRANSACTION");
      sql="insert into Test_Data2 values("+"'"+new Date().toString()+"'"+",'sdfvsdv','ascsac','sdfvsdv','ascsac','sdvsd')";
      stmt.executeUpdate(sql);
      stmt.executeUpdate("END TRANSACTION");
      stmt.close();
      c.close();
    } catch ( Exception e ) {
      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
      System.exit(0);
    }
    System.out.println("Table created successfully");
  }
}