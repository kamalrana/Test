package LogParser;

import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class threadPlugin  extends Thread {

	  int Delay = 100; //Creating a delay or the speed of the progress bar
	  JProgressBar pb; //Constructing Progress Bar
	  
	  //Creating a threadPlugin Method initializing JProgressBar so the Main Program "simpleProgressBar.java"
	  //can recognize by the time we call this class for JProgressBar action.
	  public  threadPlugin(JProgressBar progressbar) {
	   pb = progressbar;
	  }
	  
	  //run Method. This is the area where we can adjust the performance of our progress bar.
	  public void run() {
	      int minimum = pb.getMinimum(); //initializing minimum value of the progress bar
	      int maximum = pb.getMaximum(); //initializing maximum value of the progress bar
	      
	      //Initializing Progress from its minimum value 0 to its maximum value 100
	      for (int i = minimum; i < maximum; i++) { 
	        try {
	          int value = pb.getValue();
	          pb.setValue(value + 1);
	          
	          //Testing the progress bar if it already reaches to its maximum value
	          if (pb.getValue() >= maximum) {
	          
	          //Test confirmation if it runs perfectly
	           JOptionPane.showMessageDialog(null, "Test Successful!","Success!",JOptionPane.INFORMATION_MESSAGE);
	          }
	           
	          Thread.sleep(Delay); //Implementing the speed of the progress bar
	        } catch (InterruptedException ignoredException) { //Catch an error if there is any
	        }
	      }
	    }
	 }
