/*
 * CTTable.java
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
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

/**
 * CTTable extends JTable doing the following:
 * <ul>
 *   <li>The UI is forced to be CTTableUI so that a customer CellRendererPane
 *       can be installed.
 *   <li>getCellRenderer is overriden to return the TableCellRenderer passed
 *       into the constructor.
 *   <li>tableChanged is overriden to pass the call to super only if the
 *       cell is visible.
 * </ul>
 */
public class CTTable extends JTable {
    private CTTableCellRenderer renderer;

    public CTTable(CTTableCellRenderer renderer) {
        super();
        this.renderer = renderer;
        renderer.setFont(getFont());
    }

    public void updateUI() {
        super.updateUI();
	// Force the UI to be an instanceof CTTableUI.
	// This approach will not work if you need to support more than
	// one look and feel in your application.
        setUI(new CTTableUI());
    }

    public void setFont(Font font) {
	super.setFont(font);
        if (renderer != null) {
            renderer.setFont(font);
        }
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        return renderer;
    }

    public void tableChanged(TableModelEvent e) {
        if (e instanceof VisibleTableModelEvent &&
                  !((VisibleTableModelEvent)e).isVisible(this)) {
	    // Do nothing if this cell isn't visible.
            return;
        }
        super.tableChanged(e);
    }

    private static class CTTableUI extends BasicTableUI {
        public void installUI(JComponent c) {
	    // Overriden to install our own CellRendererPane
            super.installUI(c);
            c.remove(rendererPane);
            rendererPane = new CTCellRendererPane();
            c.add(rendererPane);
        }
    }


    /**
     * CTCellRendererPane overrides paintComponent to NOT clone the Graphics
     * passed in and NOT validate the Component passed in. This will NOT
     * work if the painting code of the Component clobbers the graphics
     * (scales it, installs a Paint on it...) and will NOT work if the
     * Component needs to be validated before being painted.
     */
    private static class CTCellRendererPane extends CellRendererPane {
        private Rectangle tmpRect = new Rectangle();

        // We can safely ignore this because we shouldn't be visible
        public void repaint() {
        }

        public void repaint(int x, int y, int width, int height) {
        }

        public void paintComponent(Graphics g, Component c, Container p,
                                   int x, int y, int w, int h,
                                   boolean shouldValidate) {
            if (c == null) {
                if (p != null) {
                    Color oldColor = g.getColor();
                    g.setColor(p.getBackground());
                    g.fillRect(x, y, w, h);
                    g.setColor(oldColor);
                }
                return;
            }
            if (c.getParent() != this) {
                this.add(c);
            }

            c.setBounds(x, y, w, h);

	    // As we are only interested in using a JLabel as the renderer,
	    // which does nothing in validate we can override this to do
	    // nothing, if you need to support components that can do layout,
	    // this will need to be commented out, or conditionally validate.
            shouldValidate = false;
            if(shouldValidate) {
                c.validate();
            }

            boolean wasDoubleBuffered = false;
            JComponent jc = (c instanceof JComponent) ? (JComponent)c : null;
            if (jc != null && jc.isDoubleBuffered()) {
                wasDoubleBuffered = true;
                jc.setDoubleBuffered(false);
            }

	    // Don't create a new Graphics, reset the clip and translate
	    // the origin.
            Rectangle clip = g.getClipBounds(tmpRect);
            g.clipRect(x, y, w, h);
            g.translate(x, y);
            c.paint(g);
            g.translate(-x, -y);
            g.setClip(clip.x, clip.y, clip.width, clip.height);
            if (wasDoubleBuffered) {
                jc.setDoubleBuffered(true);
            }
            c.setBounds(-w, -h, 0, 0);
        }
    }
}
