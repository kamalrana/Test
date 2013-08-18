/*
 * CTTableModel.java
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
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;

/**
 * CTTableModel, a TableModel, models a set of Datas as the rows.
 * The data is stored in a List of Lists. As the changes come in against
 * a particular Data object we also contain a map from Data to row. This
 * can obviously be made faster by pushing the row to the Data, but this
 * may not be feasable in applications of this sort.
 */
public class CTTableModel extends AbstractTableModel {
    /**
     * Maps from Data to an integer id giving the row of the data.
     */
    private Map rowMap;
    /**
     * Number of columns to display.
     */
    private int columns;
    /**
     * A List of Lists.
     */
    private java.util.List rowData;

    /**
     * If true, batch cell updates using sharedModelEvent.
     */
    private boolean batchChange;
    /**
     * Shared model event.
     */
    private VisibleTableModelEvent sharedModelEvent;


    public CTTableModel(int columns) {
        this.columns = columns;
        // Notice how they are not synchronized, we do NOT access this class
        // from another thread, and therefore do not have to worry about
        // synchronization.
        rowData = new ArrayList();
        rowMap = new HashMap();
    }

    public void addRow(Data rowID) {
        int row = rowData.size();

        rowMap.put(rowID, new Integer(row));

        ArrayList colData = new ArrayList();
        for (int counter = 0; counter < columns; counter++) {
            colData.add(null);
        }
        rowData.add(colData);
        fireTableRowsInserted(row, row);
    }

    /**
     * Toggles batch updates. When true and model changes are notified
     * using a VisibleTableModelEvent.
     */
    public void setBatchUpdates(boolean batch) {
        this.batchChange = batch;
        if (sharedModelEvent == null) {
            sharedModelEvent = new VisibleTableModelEvent(this);
        }
        sharedModelEvent.reset();
    }

    public boolean getBatchUpdates() {
        return batchChange;
    }

    /**
     * Sets the display value for a particular Data item at a particular cell.
     * If notify is true listeners are notified, otherwise no listeners are
     * notified.
     */
    public void set(Data rowID, int col, Object data, boolean notify) {
        int row = ((Integer)rowMap.get(rowID)).intValue();

        ((java.util.List)rowData.get(row)).set(col, data);
        if (notify) {
            if (batchChange) {
                sharedModelEvent.set(row, col);
                fireTableChanged(sharedModelEvent);
            }
            else {
                fireTableCellUpdated(row, col);
            }
        }
    }

    public int getRowCount() {
        return rowData.size();
    }

    public int getColumnCount() {
        return columns;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((java.util.List)rowData.get(rowIndex)).get(columnIndex);
    }
}
