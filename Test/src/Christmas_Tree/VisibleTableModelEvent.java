/*
 * VisibleTableModelEvent.java
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
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
 * VisibleTableModelEvent adds the method isVisible to test if the cell identified
 * by the event is visible.
 */
public class VisibleTableModelEvent extends TableModelEvent {
    private Point tmpPoint;

    // This implementation caches the information for one JTable, it is
    // certainly possible to cache it for more than one should
    // you have this need.
    private boolean valid;
    private int firstVisRow;
    private int lastVisRow;
    private int firstVisCol;
    private int lastVisCol;

    public VisibleTableModelEvent(TableModel source) {
        super(source, 0, 0, 0, UPDATE);
        tmpPoint = new Point();
    }

    /**
     * Resets the underlying fields of the TableModelEvent. This assumes
     * no ONE is going to cache the TableModelEvent.
     */
    public void set(int row, int col) {
        firstRow = row;
        lastRow = row;
        column = col;
    }

    /**
     * Invoked to indicate the visible rows/columns need to be recalculated
     * again.
     */
    public void reset() {
        valid = false;
    }

    public boolean isVisible(JTable table) {
        if (!valid) {
	    // Determine the visible region of the table.
            Rectangle visRect = table.getVisibleRect();

            tmpPoint.x = visRect.x;
            tmpPoint.y = visRect.y;
            firstVisCol = table.columnAtPoint(tmpPoint);
            firstVisRow = table.rowAtPoint(tmpPoint);

            tmpPoint.x += visRect.width;
            tmpPoint.y += visRect.height;
            lastVisCol = table.columnAtPoint(tmpPoint);
            if (lastVisCol == -1) {
                lastVisCol = table.getColumnCount() - 1;
            }
            if ((lastVisRow = table.rowAtPoint(tmpPoint)) == -1) {
                lastVisRow = table.getRowCount();
            }
            valid = true;
        }
        return (firstRow >= firstVisRow && firstRow <= lastVisRow &&
                column >= firstVisCol && column <= lastVisCol);
    }
}
