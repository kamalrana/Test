import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class TestScrollBar extends JFrame {

    public static class CustomScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void installListeners() {
            super.installListeners();
            CustomListener listener = new CustomListener();
            scrollbar.addMouseListener(listener);
            scrollbar.addMouseMotionListener(listener);
            scrollbar.addMouseWheelListener(listener);
        }

        protected class CustomListener extends MouseAdapter {
            boolean isInsideThumb = false;
            boolean a=false;
            boolean b=false;
            boolean c=false;
          /*  @Override
            public void mouseEntered(MouseEvent e) {
                handleMouseEvent(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseEvent(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                handleMouseEvent(e);
            }*/
            @Override
            public void mouseClicked(MouseEvent e) {
            	System.out.println("mouselicked");
            }
            @Override
            public void mouseWheelMoved(MouseWheelEvent arg0) {
            	System.out.println("mouse wheel moved");
            }
            
            @Override
            public void mouseDragged(MouseEvent paramMouseEvent) {
//            	super.mouseDragged(paramMouseEvent);
            	System.out.println("mouse Dragged");
            	b=true;
            }
            
            @Override
            public void mousePressed(MouseEvent paramMouseEvent) {
            	super.mousePressed(paramMouseEvent);
            	System.out.println("mousePressed");
            	a=true;
            	b=false;
            }

            @Override
            public void mouseReleased(MouseEvent paramMouseEvent) {
            	super.mouseReleased(paramMouseEvent);
            	System.out.println("mouse realease");
            	c=true;
            	operation();
            }
            private void operation() {
				if(a&&b&&c)
				System.out.println("yes");
			}

			private void handleMouseEvent(MouseEvent e) {
                if (getThumbBounds().contains(e.getX(), e.getY())) {
                    if (!isInsideThumb) {
                        System.out.println("THUMB");
                        isInsideThumb = true;
                    }
                } else {
                    if (isInsideThumb) {
                        System.out.println("OUT OF THUMB");
                        isInsideThumb = false;
                    }
                }
            }
        }
    }

    public TestScrollBar() {

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        for (int i = 1; i <= 10; i++) {
            innerPanel.add(new JLabel("Label " + i));
            innerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setPreferredSize(new Dimension(innerPanel.getPreferredSize().width, innerPanel.getPreferredSize().height / 2));

        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());

        this.setLayout(new BorderLayout());
        this.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.NORTH);
        this.add(Box.createRigidArea(new Dimension(0, 20)), BorderLayout.SOUTH);
        this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.EAST);
        this.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TestScrollBar frame = new TestScrollBar();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}