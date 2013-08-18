/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package log4j_V2;

/**
 *
 * @author kamal64
 */
import java.awt.*;
import java.sql.*;
import java.util.*;

import javax.swing.*;

import java.awt.event.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.plaf.basic.*;

public class GridView extends JPanel {
        RadioButtonUI ui = new RadioButtonUI();
        int pageSize = 5;
        private static JSplitPane splitPane;
        private static JPanel infoPanel;
        static JTextArea info=new JTextArea();
        static JTable table=null;
        static String level="";
        Model model = new Model();
        TableRowSorter sorter = new TableRowSorter(model);
        Box box = Box.createHorizontalBox();
        ListSelectionModel listSelectionModel;
        public GridView() {
                super(new BorderLayout());
                table = new JTable(model) {
                        public Component prepareRenderer(TableCellRenderer tcr, int row,
                                        int column) {
                                Component c = super.prepareRenderer(tcr, row, column);
                                if (isRowSelected(row)) {
                                        c.setForeground(getSelectionForeground());
                                        c.setBackground(getSelectionBackground());
                                } else {
                                        c.setForeground(getForeground());
                                        c.setBackground((row % 2 == 0) ? Color.lightGray
                                                        : getBackground());
                                }
                                return c;
                        }
                };
                table.setIntercellSpacing(new Dimension());
                table.setShowGrid(false);
                table.setRowSorter(sorter);
//                table.getColumn("Date").setPreferredWidth(100);
              table.getColumn("Date").setPreferredWidth(200);
              table.getColumn("Message").setPreferredWidth(1000);
                showPages(50, 1);
                
                table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
//                table.add(table.getTableHeader(), BorderLayout.PAGE_START);
//                table.setFillsViewportHeight(true);

                listSelectionModel = table.getSelectionModel();
                table.setSelectionModel(listSelectionModel);
                listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
                table.setSelectionModel(listSelectionModel);
//                table.setRowHeight(20);
                add(new JScrollPane(table));
                add(box, BorderLayout.SOUTH);
                setPreferredSize(new Dimension(1200, 600));
        }

        private void showPages(final int itemsPerPage, final int currentPageIndex) {
                sorter.setRowFilter(filter(itemsPerPage, currentPageIndex - 1));
                ArrayList<JRadioButton> l = new ArrayList();

                int startPageIndex = currentPageIndex - pageSize;
                if (startPageIndex <= 0)
                        startPageIndex = 1;
                int maxPageIndex = (model.getRowCount() / itemsPerPage) + 1;
                int endPageIndex = currentPageIndex + pageSize - 1;
                if (endPageIndex > maxPageIndex)
                        endPageIndex = maxPageIndex;

                if (currentPageIndex > 1)
                        l .add(createRadioButtons(itemsPerPage, currentPageIndex - 1,
                                                        "Prev"));
                for (int i = startPageIndex; i <= endPageIndex; i++)
                        l.add(createLinks(itemsPerPage, currentPageIndex, i - 1));
                if (currentPageIndex < maxPageIndex)
                        l.add(createRadioButtons(itemsPerPage, currentPageIndex + 1,
                                                        "  Next"));

                box.removeAll();
                ButtonGroup bg = new ButtonGroup();
                box.add(Box.createHorizontalGlue());
                for (JRadioButton r : l) {
                        box.add(r);
                        bg.add(r);
                }
                box.add(Box.createHorizontalGlue());
                box.revalidate();
                box.repaint();
                l.clear();
        }

        private JRadioButton createLinks(final int itemsPerPage, final int current,
                        final int target) {
                JRadioButton radio = new JRadioButton("  " + (target + 1)) {
                        protected void fireStateChanged() {
                                ButtonModel model = getModel();
                                if (!model.isEnabled()) {
                                        setForeground(Color.GRAY);
                                } else if (model.isPressed() && model.isArmed()) {
                                        setForeground(Color.GREEN);
                                } else if (model.isSelected()) {
                                        setForeground(Color.RED);
                                }
                                super.fireStateChanged();
                        }
                };
                radio.setForeground(Color.BLUE);
                radio.setUI(ui);
                if (target + 1 == current) {
                        radio.setSelected(true);
                }
                radio.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                                showPages(itemsPerPage, target + 1);
                        }
                });
                return radio;
        }

        private JRadioButton createRadioButtons(final int itemsPerPage,
                        final int target, String title) {
                JRadioButton radio = new JRadioButton(title);
                radio.setForeground(Color.BLUE);
                radio.setUI(ui);
                radio.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                showPages(itemsPerPage, target);
                        }
                });
                return radio;
        }

        private RowFilter filter(final int itemsPerPage,
                        final int target) {
                return new RowFilter() {
                        public boolean include(
                                        Entry entry) {
                                int ei = (int) entry.getIdentifier();
                                return (target * itemsPerPage <= ei && ei < target
                                                * itemsPerPage + itemsPerPage);
                        }
                };
        }

        public static void main(String[] args) {
            if(args.length<=0)
                level="DEBUG";
            else
            level=args[0];
                JFrame frame = new JFrame("Table");
                infoPanel = new JPanel(new FlowLayout(5));
                info.setEditable(false);
//                infoPanel.setPreferredSize(new Dimension(50, 50));
//                info.setLineWrap(true);
                infoPanel.add(info);
                splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new GridView(), infoPanel);
//                splitPane.setPreferredSize(new Dimension(1000,240));
//                splitPane.setDividerLocation(.5);
//                frame.getContentPane().add(new GridView());
                frame.add(splitPane);
//                frame.setSize(1000, 500);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                if(args.length<=0)
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
                else
                    frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        }
        
}

class RadioButtonUI extends BasicRadioButtonUI {
        public Icon getDefaultIcon() {
                return null;
        }

        private static Dimension size = new Dimension();
        private static Rectangle rec1 = new Rectangle();
        private static Rectangle rec2 = new Rectangle();
        private static Rectangle rec3 = new Rectangle();

        public synchronized void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c;
                ButtonModel model = b.getModel();
                Font f = c.getFont();
                g.setFont(f);
                FontMetrics fm = c.getFontMetrics(f);

                Insets i = c.getInsets();
                size = b.getSize(size);
                rec1.x = i.left;
                rec1.y = i.top;
                rec1.width = size.width - (i.right + rec1.x);
                rec1.height = size.height - (i.bottom + rec1.y);
                rec2.x = rec2.y = rec2.width = rec2.height = 0;
                rec3.x = rec3.y = rec3.width = rec3.height = 0;

                String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(),
                                null, b.getVerticalAlignment(), b.getHorizontalAlignment(), b
                                                .getVerticalTextPosition(), b
                                                .getHorizontalTextPosition(), rec1, rec2, rec3, 0);

                if (c.isOpaque()) {
                        g.setColor(b.getBackground());
                        g.fillRect(0, 0, size.width, size.height);
                }
                if (text == null)
                        return;
                g.setColor(b.getForeground());
                if (!model.isSelected() && !model.isPressed() && !model.isArmed()
                                && b.isRolloverEnabled() && model.isRollover()) {
                        g.drawLine(rec1.x, rec1.y + rec1.height, rec1.x + rec1.width,
                                        rec1.y + rec1.height);
                }
                View v = (View) c.getClientProperty(BasicHTML.propertyKey);
                if (v != null) {
                        v.paint(g, rec3);
                } else {
                        paintText(g, b, rec3, text);
                }
        }
}

class Model extends DefaultTableModel {
        Model() {
                JTable table = new JTable(this);
                addColumn("Date");
//                addColumn("level");
                addColumn("Message");
//                table.getColumn("Date").setPreferredWidth(100);
//                table.getColumn("level").setHeaderValue("10 %");
//                table.getColumn("Message").setPreferredWidth(1000);
//                table.addColumn(new TableColumn(1,1,TableCellRenderer.this,TableCellEditor.));
                try {
                	Class.forName("org.sqlite.JDBC");
                    String url = "jdbc:sqlite:C:/sqlite/testDB.db";
                        Connection con = DriverManager.getConnection(url,"","");
                        String query = "select * from Test1 where level='"+GridView.level+"'";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(query);
                        int i=0;
                        while (rs.next()) {
                                String date = rs.getString(1);
//                                String level = rs.getString(2);
                                String message = rs.getString(3);
                                System.out.println(i++);
//                                System.out.println((i++)+" ----- "+ message);
                                addRow(new Object[] { date,   message.toString()});
                        }
                         rs.close();
                         st.close();
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
}


class SharedListSelectionHandler implements ListSelectionListener {

    @Override
    public void valueChanged(ListSelectionEvent e) {
        ListSelectionModel lsm = (ListSelectionModel) e.getSource();
        String contents = "";

        if(lsm.isSelectionEmpty()) {
            System.out.println("<none>");
        } else {
            int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
            if (minIndex==maxIndex) {
                setFields(minIndex);
            } else {
                clearFields();
                /*for(int i = minIndex; i <= maxIndex; i++) {
                    if(lsm.isSelectedIndex(i)) {
                        for(int j = 0; j < GridView.table.getColumnCount(); j++) {
                            contents += GridView.table.getValueAt(i, j) + " ";
                        }
                    }
                }*/
                System.out.println(contents);
            }
        }
    }

	private void clearFields() {
		GridView.info.setText("");
		
	}

	private void setFields(int minIndex) {
		GridView.info.setText(GridView.table.getValueAt(minIndex, 1).toString());
		
	}

}