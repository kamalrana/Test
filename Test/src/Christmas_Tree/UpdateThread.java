/*
 * UpdateThread.java
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
import java.lang.reflect.*;
import java.util.*;
import javax.swing.*;

/**
 * Thread responsible for publishing changes to the Model. Sleeps for
 * a defined amount of time, waits for no activity in the UI and then
 * users invokeAndWait to publish changes.
 */
public class UpdateThread extends Thread {
    private int sleepTime;
    private int eqSleepTime;
    private int threshhold;
    private boolean updatesEnabled;
    private Runnable publishRunnable;
    private Runnable emptyRunnable;
    private GeneratorThread generator;
    private CTTableModel model;

    private Map lastData;

    private long notifyTime;
    private long paintTime;
    private int updateCount;

    private boolean done;

    public UpdateThread(GeneratorThread generator, CTTableModel model,
                        int sleepTime, int eqSleepTime, int threshhold) {
        super();
        setPriority(Thread.MIN_PRIORITY);
        this.sleepTime = sleepTime;
        this.eqSleepTime = eqSleepTime;
        updatesEnabled = true;
        this.threshhold = threshhold;
        this.generator = generator;
        this.model = model;

	// Runnable used to publish changes to the event dispatching thread
        publishRunnable = new Runnable() {
            public void run() {
                publishChangesOnEventDispatchingThread();
            }
        };

	// Empty runnable, used to wait until the event dispatching thread
	// has finished processing any pending events.
        emptyRunnable = new Runnable() {
            public void run() {
            }
        };
    }

    public void interrupt() {
        done = true;
        super.interrupt();
    }

    public void run() {
        while (!isInterrupted() && !done) {
            try {
                sleep(sleepTime);
                publishChanges();
            } catch (InterruptedException ie) {
            }
        }
        System.out.println("UpdateThread done");
    }

    /**
     * Publishes changes on the event dispatching thread when the system
     * isn't busy. This blocks the caller until the changes have been
     * published.
     */
    private void publishChanges() {
        // Wait until the user isn't scrolling
        synchronized(this) { 
            while (!updatesEnabled) {
                try {
                    wait();
                } catch (InterruptedException ie) {
                }
            }
        }
        // And wait until there are no pending events.
        EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
        while (queue.peekEvent() != null) {
            try {
                sleep(eqSleepTime);
            } catch (InterruptedException ie) {
            }
        }

        final long start = System.currentTimeMillis();

        try {
            // publish the changes on the event dispatching thread
            SwingUtilities.invokeAndWait(publishRunnable);
        } catch (InterruptedException ie) {
        } catch (InvocationTargetException ite) {
        }

        try {
            // Wait until the system has completed processing of any
            // events we triggered as part of publishing changes.
            SwingUtilities.invokeAndWait(emptyRunnable);
        } catch (InterruptedException ie) {
        } catch (InvocationTargetException ite) {
        }

        final long end = System.currentTimeMillis();

        // Update the display
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    Main.totalUpdateTime.setText("Total: " +
                                     Integer.toString((int)(end - start)));
                    Main.notifyTime.setText("Notify: " +
                                Integer.toString((int)notifyTime));
                    Main.paintTime.setText("Paint: " +
                                       Integer.toString((int)paintTime));
                    Main.updateCount.setText("Updated: " +
                                 Integer.toString((int)updateCount));
                }
            });
        } catch (InterruptedException ie) {
        } catch (InvocationTargetException ite) {
        }
    }

    /**
     * Does the actual publishing of changes.
     */
    private void publishChangesOnEventDispatchingThread() {
        long start = System.currentTimeMillis();

        model.setBatchUpdates(true);

        Map data = generator.getData();
        boolean notify = !(data.size() > threshhold ||
                           (lastData != null && lastData.size() +
                            data.size() > threshhold));


        updateCount = data.size();
        if (lastData != null) {
            updateCount += lastData.size();
        }
	// Reset the data for the last set of changes we did, this forces
	// the cells to change color.
        if (lastData != null) {
            publishData(lastData, true, notify);
            Iterator dataIterator = lastData.keySet().iterator();
            while (dataIterator.hasNext()) {
                DataChange.releaseDataChange((DataChange)dataIterator.next());
            }
            lastData.clear();
        }

	// Publish the current set of data.
        publishData(data, false, notify);

        model.setBatchUpdates(false);
        if (!notify) {
            model.fireTableDataChanged();
        }

        lastData = data;

        long end = System.currentTimeMillis();

        notifyTime = (end - start);

        start = System.currentTimeMillis();
        RepaintManager.currentManager(null).paintDirtyRegions();
        end = System.currentTimeMillis();

        paintTime = (end - start);
    }

    /**
     * Publish the passed in set of data.
     */
    private void publishData(Map data, boolean negate, boolean notify) {
        Iterator dataIterator = data.keySet().iterator();

        while (dataIterator.hasNext()) {
            DataChange change = (DataChange)dataIterator.next();
            Object value = data.get(change);

            if (negate) {
                value = new Integer(((Integer)value).intValue() * -1);
            }
            model.set(change.getData(), change.getColumn(), value, notify);
        }
    }

    /**
     * If enable is true, we are allowed to publish changes, otherwise
     * we aren't.
     */
    public void setUpdatesEnabled(boolean enable) {
        synchronized(this) {
            updatesEnabled = enable;
            if (updatesEnabled) {
                notify();
            }
        }
    }

    public boolean getUpdatesEnabled() {
        return updatesEnabled;
    }
}
