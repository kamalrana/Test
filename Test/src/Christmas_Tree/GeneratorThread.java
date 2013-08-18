/*
 * GeneratorThread.java
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
import java.util.*;

/**
 * Thread responsible for generating the changes. In a real application
 * this would likely talk to a data source. This sleeps for a defined
 * set of time, generates a set of random DataChanges and enters the cycle
 * again. The DataChanges are stored in a Map. To avoid garbage 3 Maps
 * are used:
 * <ul>
 *   <li>Map containing current set of changes that need to be published.
 *   <li>Map containing changes that UpdateThread is publishing.
 *   <li>Map of previously published changes. This allows the UpdateThread
 *       to reset the data for these to cause them to blink.
 * </ul>
 */
public class GeneratorThread extends Thread {
    private Map currentMap;
    private Map[] maps;
    private int currentMapIndex;

    private int sleepMS;
    private int batchCount;

    private java.util.List data;
    private int colCount;

    private Random random;

    private boolean done;

    public GeneratorThread(List data, int sleepMS, int batchCount,
                           int colCount) {
        this.data = data;
        this.sleepMS = sleepMS;
        this.batchCount = batchCount;
        this.colCount = colCount;
        setPriority(MIN_PRIORITY);
        maps = new Map[3];
        maps[0] = new HashMap();
        maps[1] = new HashMap();
        maps[2] = new HashMap();
        currentMap = maps[0];
        random = new Random();
    }

    public void interrupt() {
        done = true;
        super.interrupt();
    }

    public void run() {
        while (!isInterrupted() && !done) {
            try {
                sleep(sleepMS);
            } catch (InterruptedException ie) {
            }
            for (int counter = 0; counter < batchCount; counter++) {
                // pick a row
                Data datum = (Data)data.get(random.nextInt(data.size()));
		// column
                int col = random.nextInt(colCount);
		// Get a DataChange for it
                DataChange dc = DataChange.getDataChange(datum, col);
                Object value = new Integer(-1 * random.nextInt(9999999));
                synchronized(this) {
		    // And add it to the current map.
                    currentMap.put(dc, value);
                }
            }
        }
        System.out.println("Generator thread done");
    }

    /**
     * Returns the current set of changes to be published. This changes the
     * Map that changes are added to. It is assumed consumers will have
     * cleared out the Map when done with it.
     */
    public Map getData() {
        Map map;

        synchronized(this) {
            map = currentMap;
            currentMapIndex = (currentMapIndex + 1) % 3;
            currentMap = maps[currentMapIndex];
        }
        return map;
    }
}
