import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CustomInfiniteProgressBar extends JPanel {
    private static final long serialVersionUID = 223086939802246968L;
    private static final int DELAY = 100;
    private static final int MAX_AMOUNT = 100;
    private static final int SPACE = 30;
    private static final int NUMBER_OF_RECTS = 5;
	private static CustomInfiniteProgressBar cust;
    public static Timer timer;
    private int value = 0, darkRect = 0;
    public static boolean flag=true;
	private static JButton b;
 
    public CustomInfiniteProgressBar() {
        timer = new Timer(DELAY, new TimerActionListener());
        timer.start();
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
 
        // draw rectangular progress
        int paddingToFrameBorders = 10; //dummy values for position
        int smallerSize = 5, largerSize = 10; //dummy values for rectangle size
 
        int rectX, rectY;
        for (int i = 0; i < NUMBER_OF_RECTS; i++) {
            rectX = paddingToFrameBorders + i * SPACE;
            rectY = paddingToFrameBorders;
            if(darkRect == i) {
                g2d.fillRect(rectX, rectY, largerSize, largerSize);
            } else {
                // to center smaller rectangle respective to larger rectangle.
                rectX += (largerSize - smallerSize) / 2;
                rectY += (largerSize - smallerSize) / 2;
 
                g2d.drawRect(rectX, rectY, smallerSize, smallerSize);
            }
        }
    }
 
    /**
    * Action Listener of Timer.
    */
    private class TimerActionListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            value++;
            if(value == MAX_AMOUNT) { // you can have some condition here to stop the timer.
                timer.stop();
            }
            darkRect = (value % NUMBER_OF_RECTS);
            repaint();
        }
    }
    public static void main(String[] args) throws InterruptedException {
    	JFrame frame=new JFrame();
    	cust = new CustomInfiniteProgressBar();
    	cust.setPreferredSize(new Dimension(200, 40));
    	 b=new JButton("stop");
    	b.setPreferredSize(new Dimension(100, 20));
    	b.addMouseListener( new MouseAdapter() {
			public void mouseClicked( MouseEvent mouseEvent ) {
				if(flag){
				timer.stop();
				flag=!flag;
				}
				else{
					timer.start();
					flag=!flag;
				}
				
				if(b.getText()=="start")
					b.setText("stop");
				else
					b.setText("start");
			}
		});
    	JPanel jp1=new JPanel();
    	jp1.setLayout(new BorderLayout());
    	jp1.add(cust,BorderLayout.CENTER);
    	jp1.add(b,BorderLayout.SOUTH);
    	
    	frame.add(jp1);
    	frame.setVisible(true);
    	frame.pack();
		
    	
//    	cust=null;
    	
	}
}