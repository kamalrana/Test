/**
@author : kamal64
*/


package log4j_fetch;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


public class Retreve_FIle extends JPanel{
    private static Long startmili=System.currentTimeMillis();
	public Retreve_FIle() {
		super(new GridLayout(1,0));
		JTable jt=null;
        try {
            jt = new JTable(new Table());
        } catch (SQLException ex) {
            Logger.getLogger(Retreve_FIle.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Retreve_FIle.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		jt.setPreferredScrollableViewportSize(new Dimension(1000,500));
		jt.setAutoCreateRowSorter(true);
		jt.setFillsViewportHeight(true);
//		jt.setPreferredSize(new Dimension(2000,200));
		JScrollPane jcp=new JScrollPane(jt);
		add(jcp);
	}

	class Table extends AbstractTableModel{
		int row;
		int col;
		ArrayList<String> colNameList=new ArrayList();
		Object [][] data;
                String sql;
		String [] colName;
		File f=new File("G:\\usr\\home\\log4j\\log.out");
		Scanner s=null;
		String object="";
		String reqString="";
		HashMap<Integer, HashMap<String,String>> map=new HashMap();
public Table() throws SQLException, ClassNotFoundException{
	String datePattern="\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d";
	String date="";
	int i=0;
	try {
		s=new Scanner(f);
		while (s.hasNextLine()) {
			object = s.nextLine().trim();
			if(object.contains("ERROR")){
				reqString=object;
//			System.out.println("adding "+reqString);
//			System.out.println(datePattern);
			Pattern p = Pattern.compile(datePattern);
			Matcher m = p.matcher(reqString);
			
			date = "";
//			 System.out.println("datePattern is " + m.pattern());
			while (m.find()) {
//				 System.out.println(m.start() + ">>" + m.group());
				date = m.group();
				break;
			}
			if (date == "")
				continue;
			i++;
//			if(i>100)break;
			HashMap temp=new HashMap();
//			System.out.println("putting :: "+date+" :: & :: "+reqString);
			temp.put(date, reqString);
			map.put(i,temp);
//			System.out.println(map.get(i));
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}

	row=map.keySet().size();
        System.out.println("rows "+row);

        colName=new String []{"date","level","message"};
        col=colName.length;
	data=new Object[row][col];
	int j=0,k=0;
	Object b;
        
	for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
		Integer type = (Integer) iterator.next();
		date=map.get(type).keySet().toArray()[0].toString();
//		reqString=map.get(type).get(map.get(type).keySet().toArray()[0]);
		for(int l=1;l<=col;l++){
			k=l;
			k--;
			if(k==0){
				data[j][k]=date;
//				System.out.println(date);
			}
			else if(k==1){
				data[j][k]="ERROR";
			}
			else{
			data[j][k]=reqString;
//			System.out.println(reqString);
			}
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
	Retreve_FIle pane=new Retreve_FIle();
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

