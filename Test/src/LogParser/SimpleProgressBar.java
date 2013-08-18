package LogParser;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class SimpleProgressBar extends JFrame implements PropertyChangeListener {

	JProgressBar bar = new JProgressBar();
	JButton button = new JButton("Test Progress Bar");

	public SimpleProgressBar() {

		super("Creating a Simple Progress Bar");

		setSize(350, 100);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container pane = getContentPane();
		setContentPane(pane);

		bar.setStringPainted(true);

		GridLayout grid = new GridLayout(2, 1);
		pane.setLayout(grid);

		pane.add(bar);
		pane.add(button);

		button.addActionListener(new ActionListener() {

			private Task task;

			// Handle JButton event if it is clicked
			public void actionPerformed(ActionEvent event) {
				button.setEnabled(false); // Disable the JButton if the progress
				task = new Task();
				task.addPropertyChangeListener(SimpleProgressBar.this);
				task.execute();
											// bar starts the progress
//				Thread run = new threadPlugin(bar); // Calling the class
//													// "threadPlugin" we created
//													// that extends with Thread
//				run.start(); // run the thread to start the progress
			}
		});
		setVisible(true);
	}

	// Main Method
	public static void main(String[] args) {
		SimpleProgressBar spb = new SimpleProgressBar();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("property changed");
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			System.out.println("setting progress :: "+progress);
			bar.setValue(progress);
			System.out.println("progress set in bar");
		}
	}
	
	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {

			try{

				bar.setVisible( true );
				Random random = new Random();
				int progress = 0;
				//Initialize progress property.
				setProgress(0);

				final TaskPerformed tp = new TaskPerformed();
				new Runnable() {
					
					@Override
					public void run() {
						
						try {
							tp.perform();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				};
				Class c = tp.getClass();

				Method [] methodArray = c.getDeclaredMethods();

				for( Method method : methodArray ){

					progress += random.nextInt( 10 );
					setProgress( Math.min( progress , 100 ) );
					
					if(method.invoke( tp ).equals("secondDone")){
						setProgress(bar.getMaximum());
						System.out.println("setting max");
						Thread.sleep(10000);
					}

					progress += random.nextInt( 10 );
					setProgress( Math.min( progress, 100 ) );

				}
				while (progress < 100) {
					//Sleep for up to one second.
					try {
						Thread.sleep(random.nextInt(1000));
					} catch (InterruptedException ignore) {}
					//Make random progress.
					progress += random.nextInt(10);
					setProgress(Math.min(progress, 100));
				}
			}catch( Exception ie )
			{
				ie.printStackTrace();

			}
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {

		}
	}
	
	class TaskPerformed {
		ArrayList list=new ArrayList();
		public String perform() throws IOException{
			BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(new File("C:\\sqlite\\log1.out"))));
			String line="";
			try {
				while( (line=br.readLine())!=null ){
					line=line.trim();
					list.add(line);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("readig done");
			br.close();
			return "firstDone";
		}
		public String write(){
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				String type = (String) iterator.next();
			}
			System.out.println("writn done");
			return "secondDone";
		}
	}
	
}
