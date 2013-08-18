/*
 * CTTableCellRenderer.java
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
import javax.swing.border.*;
import javax.swing.table.*;

/**
 * A custom TableCellRenderer that overrides a handful of methods:
 * <ul>
 *   <li>isOpaque and setBackground are overridden to avoid filling the
 *       background, if possible.
 *   <li>firePropertyChange is overridden to do nothing. If you need to
 *       support HTML text in the renderer than this should NOT be overridden.
 *   <li>paint is overridden to forward the call directly to the UI, avoiding
 *       the creation of a Graphics. This will NOT work if you need the
 *       renderer to contain other childre or the Graphics is clobbered as
 *       part of painting the UI.
 *  </ul>
 */
public class CTTableCellRenderer extends DefaultTableCellRenderer {
    private Color background;
    private Color foreground;

    private Color editableForeground;
    private Color editableBackground;
    private Border focusBorder;

    public CTTableCellRenderer() {
        focusBorder = UIManager.getBorder("Table.focusCellHighlightBorder");
        editableForeground = UIManager.getColor("Table.focusCellForeground");
        editableBackground = UIManager.getColor("Table.focusCellBackground");
    }

    public Component getTableCellRendererComponent(
              JTable table, Object value, boolean isSelected, boolean hasFocus,
              int row, int column) {
	// Reset the background based on the sign of the value.
        boolean negative = (value != null && ((Integer)value).intValue() < 0);

	if (isSelected) {
            setForeground(table.getSelectionForeground());
            setBackground(table.getSelectionBackground());
	}
	else {
	    setForeground(table.getForeground());
            if (!negative) {
                setBackground(null);
            }
            else {
                setBackground(Color.red);
            }
	}

	// NOTICE that we do NOT set the font here, because CTTable knows
	// about us, it will set the font as appropriate.

	if (hasFocus) {
            setBorder(focusBorder);
            if (table.isCellEditable(row, column)) {
                setForeground(editableForeground);
                setBackground(editableBackground);
            }
	} else {
	    setBorder(noFocusBorder);
	}

        setValue(value); 

        return this;
    }

    protected void firePropertyChange(String propertyName, Object oldValue,
                                      Object newValue) {	
        // As long as you don't have any HTML text, this override is ok.
    }

    // This override is only appropriate if this will never contain any
    // children AND the Graphics is not clobbered during painting.
    public void paint(Graphics g) {
        ui.update(g, this);
    }

    public void setBackground(Color c) {
        this.background = c;
    }

    public Color getBackground() {
        return background;
    }

    public void setForeground(Color c) {
        this.foreground = c;
    }

    public Color getForeground() {
        return foreground;
    }

    public boolean isOpaque() { 
        return (background != null);
    }

    // This is generally ok for non-Composite components (like Labels)
    public void invalidate() {
    }

    // Can be ignored, we don't exist in the containment hierarchy.
    public void repaint() {
    }
}
