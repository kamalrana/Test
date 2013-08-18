package LogParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class One1
{
	public static void main(String[] args)
		{
		try
			{
			File f1 = new File("D:\\t1.txt");
			File f2 = new File("D:\\t2.txt");
			File f3 = new File("D:\\t3.txt");
			FileWriter fileWritter1 = new FileWriter(f1, true);
			PrintWriter pw1 = new PrintWriter(fileWritter1);
			FileWriter fileWritter2 = new FileWriter(f2, true);
			PrintWriter pw2 = new PrintWriter(fileWritter2);
			FileWriter fileWritter3 = new FileWriter(f3, true);
			PrintWriter pw3 = new PrintWriter(fileWritter3);
			Date d1=new Date();
			File file = new File("D:\\Log_CMCM_93x_CascadeTheChangeInCostCenter_UI.txt");
			BufferedReader br =
			        new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String s = "";
			
			Long startmili = System.currentTimeMillis();
			System.out.println("Start at :"+Calendar.getInstance().getTime());
			
			while ((s = br.readLine()) != null)
				{
				if (s.contains("[DEBUG]"))
					{
					pw1.println(s);
					}
				else if (s.contains("[INFO]"))
					{
					pw2.println(s);
					}
				else if (s.contains("[ERROR]"))
					{
					pw3.println(s);
					}
				}

			Long endMili = System.currentTimeMillis();
			Long millis = endMili - startmili;
			System.out.println("End at :"+Calendar.getInstance().getTime());
			 
			System.out.println(String.format("%d min, %d sec", 
					    TimeUnit.MILLISECONDS.toMinutes(millis),
					    TimeUnit.MILLISECONDS.toSeconds(millis) - 
					    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
					));
			Date d2=new Date();
			System.out.println("time is : "+(d2.getTime()-d1.getTime()));
			pw1.close();
			pw2.close();
			pw3.close();
			}
		catch (Exception e)
			{
			e.printStackTrace();
			}
		}
}
