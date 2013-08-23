
public class LevelFormattest {
public static void main(String[] args) {
	
String conversionPattern = "%d{ISO8601} [%3.3t] [%p] [%c{1}] %C{1}.%M - %m%n";
	char c1 = conversionPattern.charAt(conversionPattern.indexOf("%p")-1);
		char c2 = conversionPattern.charAt(conversionPattern.indexOf("%p")+2);

		System.out.println(c1+""+c2);
}
}
