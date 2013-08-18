/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package log4j_fetch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author kamal64
 */
public class Sqlite_java_Insertion {
    public static void main(String arg[]){
        try {
             Connection conn = null;  
             Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:/sqlite/testDB.db";  
            String user = "";  
            String pwd = "";  
            conn = DriverManager.getConnection(url, user, pwd);  
            Statement stmt=conn.createStatement();
            for(int i=0;i<140;i++){
                System.out.println(i);
                System.out.println(stmt.execute("insert into test2 values('kamal','a',"+i+")"));
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
            
}
