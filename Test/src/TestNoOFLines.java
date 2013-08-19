import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class TestNoOFLines {
public static void main(String[] args) {
	try {
		Long startmili = System.currentTimeMillis();
//		BufferedReader br= new BufferedReader(new InputStreamReader(new FileInputStream(new File("c:/sqlite/big.out"))));
		System.out.println("no of lines :: "+countLines(new File("c:/sqlite/big.out")) );
		 Long endMili = System.currentTimeMillis();
			Long millis = endMili - startmili;
			System.out.println("End at :" + Calendar.getInstance().getTime());

			System.out.println(String.format(
					"%d min, %d sec",
					TimeUnit.MILLISECONDS.toMinutes(millis),
					TimeUnit.MILLISECONDS.toSeconds(millis)
							- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
									.toMinutes(millis))));
	} catch (Exception e) {
		// TODO: handle exception
	}
}

public static int countLines(File aFile) throws IOException {
    LineNumberReader reader = null;
    try {
        reader = new LineNumberReader(new FileReader(aFile));
        while ((reader.readLine()) != null);
        return reader.getLineNumber();
    } catch (Exception ex) {
        return -1;
    } finally { 
        if(reader != null) 
            reader.close();
    }
}
}
