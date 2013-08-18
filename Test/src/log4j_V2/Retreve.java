package log4j_V2;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class Retreve extends JPanel{
    private static Long startmili=System.currentTimeMillis();
	public Retreve(String cond, String level) {
		super(new GridLayout(1,0));
		JTable jt=null;
        try {
            jt = new JTable(new Table(cond,level));
        } catch (SQLException ex) {
            Logger.getLogger(Retreve.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Retreve.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		jt.setPreferredScrollableViewportSize(new Dimension(500,100));
		jt.setAutoCreateRowSorter(true);
		jt.setFillsViewportHeight(true);
		JScrollPane jcp=new JScrollPane(jt);
		add(jcp);
	}

	class Table extends AbstractTableModel{
		Connection con;
		Statement stmt;
		ResultSet rs;
		int row;
		int col;
		ArrayList<String> colNameList=new ArrayList();
		Object [][] data;
                String sql;
		String [] colName;
public Table(String cond,String level) throws SQLException, ClassNotFoundException{
	  Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:"+"c:/sqlite/testDB.db");
			stmt = con.createStatement();

              sql="select date,level,message from Test1 where level='"+level+"'";

      rs=stmt.executeQuery(sql);
	while(rs.next()){
		row++;
	}
	rs=null;
	rs=stmt.executeQuery(sql);
	ResultSetMetaData rsmd=rs.getMetaData();
	col=rsmd.getColumnCount();

	for(int i=1;i<=col;i++){
		colNameList.add(rsmd.getColumnTypeName(i));
	}
        System.out.println("rows "+row);
	System.out.println(colNameList);

	colName=new String [colNameList.size()];
	colName=colNameList.toArray(colName);
	data=new Object[row][col];
	int j=0,k=0;
	Object b;
        
	while(rs.next()){
		for(int i=1;i<=col;i++){
			if(colNameList.get(k).equalsIgnoreCase("text")){
			b=rs.getString(i);
			}
			else{
				b=rs.getObject(i);
			}
			k=i;
			k--;
			data[j][k]=b;
//			System.out.println(j+" "+k+" "+b);
		}j++;
	}
}


		@Override
		public int getRowCount() {
			return data.length;
		}

		@Override
		public int getColumnCount() {
			return colName.length;
		}

		@Override
		public Object getValueAt(int paramInt1, int paramInt2) {
			return data[paramInt1][paramInt2];
		}

	}
public static void main(String[] args) {
	JFrame frame=new JFrame("table");
	frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	Retreve pane=new Retreve("Test1",args[0]);
	pane.setOpaque(true);
	frame.add(pane);
	frame.pack();
	frame.setVisible(true);
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

}
