package log4j_fetch;

public class Test {
public static void main(String[] args) {
	String s="select * from test1 where level='ERROR'";
	s=s.toLowerCase();
if(s.contains("where") && s.contains("level")){
	System.out.println("-"+s.substring(s.indexOf("level")+"level".length()+1).replaceAll("'",""));
	System.out.println(s.substring(s.indexOf("from")+5, s.indexOf("where")));
	System.out.println(s.substring(s.indexOf("where")));
}

}
}
