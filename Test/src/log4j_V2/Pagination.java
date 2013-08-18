/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package log4j_V2;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.JTable;

/**
 *
 * @author kk
 */
public class Pagination{
    JFrame frame;
    public static void main(String arg[]){
        new Pagination().buildFrame();
    }
private void buildFrame() {
	frame = new JFrame("Demo");
	frame.setSize(300, 300);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	addStuffToFrame();
	frame.setVisible(true);

}

private void addStuffToFrame() {
	final JTable table = getTable();
	final JScrollPane scrollPane = new JScrollPane(table);
	scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
	final JButton next = new JButton("next");
	final JButton prev = new JButton("prev");

	   ActionListener al = new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			                 Rectangle rect = scrollPane.getVisibleRect();
			JScrollBar  bar = scrollPane.getVerticalScrollBar();
			int blockIncr = scrollPane.getViewport().getViewRect().height;
			if (e.getSource() == next) {
				bar.setValue(bar.getValue() + blockIncr);
			} else if (e.getSource() == prev) {
				bar.setValue(bar.getValue() - blockIncr);
			}
			scrollPane.scrollRectToVisible(rect);
		}
	};

	next.addActionListener(al);
	prev.addActionListener(al);

	JPanel panel = new JPanel(new BorderLayout());
	JPanel buttonPanel = new JPanel();
	buttonPanel.add(prev);
	buttonPanel.add(next);
	panel.add(buttonPanel, BorderLayout.NORTH);
	panel.add(scrollPane, BorderLayout.CENTER);
	frame.getContentPane().add(panel);
}

private JTable getTable() {
	String[] colNames = new String[]{
			"col 0", "col 1", "col 2", "col 3"
	};

	String[][] data = new String[100][4];
	for (int i = 0; i < 100; i++) {
		for (int j = 0; j < 4; j++) {
			data[i][j] = "r:" + i + " c:" + j;
		}
	}

	return new JTable(data,colNames);

}
}