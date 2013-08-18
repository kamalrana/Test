
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JTable;

public class Jtable1 {
    private static Object request;
    static JTable mysTable;
    //constructor method

    public static void main (String args []){
    String [] columnNames = {"Name","Lastname","Id","Style"};
        mysTable = new JTable(100,3);
        mysTable.setBounds(20,10,300,300);

        JFrame frame = new JFrame("King Musa Saloon Software");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(500,500);
        frame.setResizable(false);
           frame.setVisible(true);
            frame.add(mysTable);

//frame.add(mysTable);
        try {
        	 Class.forName("org.sqlite.JDBC");
            System.out.println("Driver loading success!");
            String url = "jdbc:sqlite:d:/sqlite/testDB.db";
            String name = "";
            String password = "";
            try {


                java.sql.Connection con = DriverManager.getConnection(url, name, password);
                System.out.println("Connected.");
         // pull data from the database 
java.sql.Statement stmts = null;
String query = "select  date, level, message from Test1 limit 100";
stmts = con.createStatement();
ResultSet rs = stmts.executeQuery(query);
int li_row = 0;
while(rs.next()){
    mysTable.setValueAt(rs.getString("date"),li_row,0);
    mysTable.setValueAt(rs.getString("level"),li_row,1);
    mysTable.setValueAt(rs.getString("message"),li_row,2);
//    int userid = rs.getInt("userid");
//    String username = rs.getString("username");
//    String name1     = rs.getString("name");
//    System.out.println(name1);
    li_row++;


}




            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}