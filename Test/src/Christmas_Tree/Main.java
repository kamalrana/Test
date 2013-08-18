/*
 * Main.java
 * Copyright 2002 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 * 
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * 
 * - Redistribution in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials
 *   provided with the distribution.
 * 
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY
 * DAMAGES OR LIABILITIES SUFFERED BY LICENSEE AS A RESULT OF OR
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THIS SOFTWARE OR
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT,
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF
 * THE USE OF OR INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 */
package Christmas_Tree;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * The Main controller, responsible for wiring everything together. Pressing
 * return in any of the fields will trigger recreation of everything.
 */
public class Main implements ActionListener {
    // properties: columnCount, rowCount, updateSleepTime, eqSleepTime,
    // threshold, generateSleep, generatorBatchCount

    private static final Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);

    private JTextField columnCount;
    private JTextField rowCount;
    private JTextField updateSleepTime;
    private JTextField eqSleepTime;
    private JTextField threshold;
    private JTextField generateSleep;
    private JTextField generatorBatchCount;
    private JFrame frame;

    static JLabel totalUpdateTime;
    static JLabel notifyTime;
    static JLabel paintTime;
    static JLabel updateCount;

    private JTable table;

    private UpdateThread updateThread;
    private GeneratorThread generatorThread;
    private CTTableModel tableModel;

    // Initial values for the 7 properties.
    private static int NUM_COLUMNS = 40;
    private static int NUM_ROWS = 3000;
    private static int UPDATE_SLEEP_TIME = 500;
    private static int EQ_SLEEP_TIME = 10;
    private static int UPDATE_ALL_THRESHOLD = 400000;
    private static int GENERATOR_SLEEP_TIME = 40;
    private static int BATCH_SIZE = 1000;

    Main() {
        frame = new JFrame();

        frame.getContentPane().setLayout(new GridBagLayout());

        columnCount = add("Columns: ", NUM_COLUMNS);
        rowCount = add("Rows: ", NUM_ROWS);
        updateSleepTime = add("Update Sleep: ", UPDATE_SLEEP_TIME);
        eqSleepTime = add("EQ Sleep: ", EQ_SLEEP_TIME);
        threshold = add("Update All Threshold: ", UPDATE_ALL_THRESHOLD);
        generateSleep = add("Generator Sleep: ", GENERATOR_SLEEP_TIME);
        generatorBatchCount = add("Batch Size: ", BATCH_SIZE);

        table = new CTTable(new CTTableCellRenderer());

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        JScrollPane sp = new JScrollPane(table);
        frame.getContentPane().add(sp, new GridBagConstraints(
            0, 3, 6, 1, 1, 1, GridBagConstraints.WEST,
            GridBagConstraints.BOTH, EMPTY_INSETS, 0, 0));

        ChangeListener changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                BoundedRangeModel m = (BoundedRangeModel)(e.getSource());
                if (updateThread != null) {
                    updateThread.setUpdatesEnabled(!(m.getValueIsAdjusting()));
                }
            }
        };
        sp.getVerticalScrollBar().getModel().addChangeListener(changeListener);
        sp.getHorizontalScrollBar().getModel().
                                    addChangeListener(changeListener);

        totalUpdateTime = new JLabel(" ");
        notifyTime = new JLabel(" ");
        paintTime = new JLabel(" ");
        updateCount = new JLabel(" ");

        JPanel statusPanel = new JPanel(new GridBagLayout());

        frame.getContentPane().add(statusPanel, new GridBagConstraints(
            0, 4, 6, 1, 1, 0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, EMPTY_INSETS, 0, 0));

        statusPanel.add(totalUpdateTime, new GridBagConstraints(
            0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, EMPTY_INSETS, 0, 0));

        statusPanel.add(notifyTime, new GridBagConstraints(
            1, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, EMPTY_INSETS, 0, 0));

        statusPanel.add(paintTime, new GridBagConstraints(
            2, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, EMPTY_INSETS, 0, 0));

        statusPanel.add(updateCount, new GridBagConstraints(
            3, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, EMPTY_INSETS, 0, 0));

	frame.setTitle("Christmas Tree Demo Application");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 1000, 800);
        frame.show();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ie) {
        }
        reset();
    }

    public void actionPerformed(ActionEvent ae) {
        reset();
    }

    private JTextField add(String name, int defaultValue) {
        Container parent = frame.getContentPane();
        int row = parent.getComponentCount() / 6;
        int col = parent.getComponentCount() % 6;

        parent.add(new JLabel(name), new GridBagConstraints(
            col, row, 1, 1, 0, 0, GridBagConstraints.WEST, 0,
            EMPTY_INSETS, 0, 0));

        JTextField tf = new JTextField(Integer.toString(defaultValue));
        tf.addActionListener(this);
        parent.add(tf, new GridBagConstraints(
            col + 1, row, 1, 1, 1, 0, GridBagConstraints.WEST,
            GridBagConstraints.HORIZONTAL, EMPTY_INSETS, 0, 0));

        return tf;
    }

    private void reset() {
        System.out.println("Columns: " + getInt(columnCount));
        System.out.println("Rows: " + getInt(rowCount));
        System.out.println("Update Sleep: " + getInt(updateSleepTime));
        System.out.println("EQ Sleep: " + getInt(eqSleepTime));
        System.out.println("Update All Threshold: " + getInt(threshold));
        System.out.println("Generator Sleep: " + getInt(generateSleep));
        System.out.println("Batch Size: " + getInt(generatorBatchCount));

        if (updateThread != null) {
            System.out.println("interrupting!");
            updateThread.interrupt();
            generatorThread.interrupt();
        }
        int cols = getInt(columnCount);
        tableModel = new CTTableModel(cols);
        ArrayList data = new ArrayList();
        for (int counter = getInt(rowCount) - 1; counter >= 0; counter--) {
            Data dataID = new Data();

            data.add(dataID);
            tableModel.addRow(dataID);
            for (int colCounter = 0; colCounter < cols; colCounter++) {
                if (colCounter % 2 == 0) {
                    tableModel.set(dataID, colCounter,
                                   new Integer(counter * 100 + colCounter), false);
                }
                else {
                    tableModel.set(dataID, colCounter,
                                   new Integer(counter * -100 + colCounter), false);
                }
            }
        }
        table.setModel(tableModel);
        generatorThread = new GeneratorThread(
            data, getInt(generateSleep), getInt(generatorBatchCount),
            getInt(columnCount));
        updateThread = new UpdateThread(generatorThread, tableModel,
                                        getInt(updateSleepTime),
                                        getInt(eqSleepTime),
                                        getInt(threshold));

        generatorThread.start();
        updateThread.start();
    }


    private int getInt(JTextField tf) {
        try {
            return Integer.parseInt(tf.getText());
        } catch (NumberFormatException nfe) {
            System.out.println("exception getting int: " + nfe);
        }
        return 0;
    }

    public static void main(String[] args) {
        new Main();
    }
}
