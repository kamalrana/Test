/**
@author : kamal64
*/


package LogParser;

import java.io.File;

public class SizeCheckDB {
public static void main(String[] args) {
	try {
		File f=new File("E:\\sqlite\\testDB.db");
		System.out.println((f.length()/1024)/1024);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
