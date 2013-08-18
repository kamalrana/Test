package log4j_V2;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class JTableCreatingDemo {
	Connection con;Statement stmt;ResultSet rs;int column=3;
        int row;
    ArrayList TicketNo=new ArrayList();
    ArrayList Class_Code=new ArrayList();
    ArrayList Train_Code=new ArrayList();
    ArrayList Station_Start=new ArrayList();
    ArrayList Station_End=new ArrayList();
    ArrayList<java.util.Date> Date_Begin=new ArrayList<java.util.Date>();
    ArrayList Noofperson=new ArrayList();

	{try {
        Class.forName("org.sqlite.JDBC");
        Connection c = DriverManager.getConnection("jdbc:sqlite:"+"c:/sqlite/testDB.db");
			stmt = c.createStatement();
//        rs=stmt.executeQuery("select count(level) from Test1 where level='[Debugg]'");
//        while(rs.next()){
//        	column=(int)rs.getInt(1);
//        }
//        rs=null;
       
        rs=stmt.executeQuery("select count(level) from Test1 where level='[DEBUG]'");
        while(rs.next()){
        	row=(int)rs.getInt(1);
        }
        System.out.println("column :: "+column);
                    System.out.println("row :: "+row);
        
rs=stmt.executeQuery("SELECT date, level, message from Test1 where level='DEBUG'");
        while(rs.next())
        {
            TicketNo.add(rs.getString(1));
            Class_Code.add(rs.getString(2));
            Train_Code.add(rs.getString(3));
        }
       } catch (Exception e) {e.printStackTrace();
    }
    }
	
	Object data[][] =new Object[column][row];
	public void Gui(){

	    JFrame frame = new JFrame();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	for (int i=0; i<data.length; i++) {
    	data[i][0]=TicketNo.get(i);
        data[i][1]=Class_Code.get(i);
        data[i][2]=Train_Code.get(i);
         }
    

	    Object columnNames[] = {"TicketNo","Class_Code","Train_Code"};
	    JTable table = new JTable(data, columnNames);

	    JScrollPane scrollPane = new JScrollPane(table);
	    frame.add(scrollPane, BorderLayout.CENTER);
	    frame.setSize(300, 150);
	    frame.setVisible(true);

	    table.setPreferredScrollableViewportSize(new Dimension(500, 70));
	    table.setFillsViewportHeight(true);
	    table.setAutoCreateRowSorter(true);

	  
	}
	
	
  public static void main(String args[]) {
	  new JTableCreatingDemo().Gui();
  }
}