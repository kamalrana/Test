/**
@author : kamal64
*/

package LogParser;

public class CmdTest {
public static void main(String[] args) {
try {
	 String str ="E:/sqlite/sqlite3.exe testDB.db";
	    Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c",str});
	    System.out.println(str);

} catch (Exception e) {
e.printStackTrace();
}
}
}
