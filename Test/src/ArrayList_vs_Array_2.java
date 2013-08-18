import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArrayList_vs_Array_2 {
	public static void main(String[] args) throws InterruptedException, IOException {
		ArrayList data = new ArrayList();
		ArrayList data1 = new ArrayList();
		int k = 0;
		int j = 0;
		BufferedReader br=null;
		String date="";
		Thread.currentThread().sleep(20000l);
String reqString="";
//        System.out.println("Log file is :: "+fileName);
        br = new BufferedReader(new InputStreamReader(new FileInputStream("c:/sqlite/testDB.db")));
        String line="";
        while( (line=br.readLine())!=null ){
        	reqString="";
            if (line.contains("error")) {
                reqString = line;
//	System.out.println("adding "+reqString);
//	System.out.println(datePattern);
                Pattern p = Pattern.compile("\\d\\d\\d\\d-\\d\\d-\\d\\d \\d\\d:\\d\\d:\\d\\d,\\d\\d\\d");
                Matcher m = p.matcher(reqString);

                date = "";
//	 System.out.println("datePattern is " + m.pattern());
                while (m.find()) {
//		 System.out.println(m.start() + ">>" + m.group());
                    date = m.group();
                    break;
                }
                if (date == "") {
                    continue;
                }
                if(reqString.length()>0){
                ArrayList l= new ArrayList();
                l.add(date);
                l.add(reqString);
                data1.add(l);
                }
            }
         
        }
        System.out.println("data1 :: "+data1.size());
        br.close();
    
//		Thread.currentThread().sleep(10000l);
		j=0;
		System.out.println("data  size:: "+data.size());
		System.out.println("data 1 size:: "+data1.size());
for (int i = 0; i < data.size(); i++,j++) {
	ArrayList objects = (ArrayList) data.get(i);
	for (Iterator iterator = objects.iterator(); iterator.hasNext();) {
		Object object = (Object) iterator.next();
		System.out.println(object+"--"+j);
	}
}
	}
}
