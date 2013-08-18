import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayList_vs_Array {
	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().sleep(20000l);
        int row;
        int col;
        ArrayList<String> colNameList = new ArrayList();
        Object[][] data;
        String sql;
        String[] colName;
//		File f=new File("D:\\sqlite\\logs\\log.out");
        BufferedReader br =null;
        String reqString = "";
        HashMap<Integer, HashMap<String, String>> map = new HashMap();

	String datePattern="\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d";
            String date = "";
            int i = 0;
//            String datePattern = args[0];
           String fileName="C:\\sqlite\\log1.out";
            try {
                System.out.println("Log file is :: "+fileName);
                br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
                String line="";
                while( (line=br.readLine())!=null ){
                	
                    if (line.indexOf("error")>0) {
                    	System.out.println(line);
                        reqString = line;
//			System.out.println("adding "+reqString);
//			System.out.println(datePattern);
                        Pattern p = Pattern.compile(datePattern);
                        Matcher m = p.matcher(reqString);

                        date = "";
//			 System.out.println("datePattern is " + m.pattern());
                        while (m.find()) {
				 System.out.println(m.start() + ">>" + m.group());
                            date = m.group();
                            break;
                        }
                        if (date == "") {
                            continue;
                        }
                        i++;
//			if(i>100)break;
                        HashMap temp = new HashMap();
//			System.out.println("putting :: "+date+" :: & :: "+reqString);
                        temp.put(date, reqString);
                        map.put(i, temp);
//			System.out.println(map.get(i));
                    }
                }
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            row = map.keySet().size();
//            System.out.println("rows " + row);

            colName = new String[]{"date", "level", "message"};
            col = colName.length;
            data = new Object[row][col];
            int j = 0, k = 0;
            Object b;

            for (Iterator iterator = map.keySet().iterator(); iterator.hasNext();) {
                Integer type = (Integer) iterator.next();
                date = map.get(type).keySet().toArray()[0].toString();
//		reqString=map.get(type).get(map.get(type).keySet().toArray()[0]);
                for (int l = 1; l <= col; l++) {
                    k = l;
                    k--;
                    if (k == 0) {
                        data[j][k] = date;
//				System.out.println(date);
                    } else if (k == 1) {
                        data[j][k] = "ERROR";
                    } else {
                        data[j][k] = reqString;
//			System.out.println(reqString);
                    }
//			System.out.println(j+" "+k+" "+b);
                }
                j++;
            }
        }

    }
