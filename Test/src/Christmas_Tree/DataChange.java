/*
 * DataChange.java
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
import java.util.ArrayList;

/**
 * DataChange is used to associate a Data Object with a column identifier
 * that has changed. To avoid loads of garbage per update DataChanges are
 * cached and reused.
 */
public class DataChange {
    private static ArrayList sharedDataChanges = new ArrayList();

    private Data data;
    private int col;
    private int hashCode;


    /**
     * Obtains a DataChange for the specified Data and column.
     */
    public static DataChange getDataChange(Data data, int col) {
        synchronized(sharedDataChanges) {
            int size = sharedDataChanges.size();
            if (size > 0) {
                DataChange change = (DataChange)sharedDataChanges.
                                    remove(size - 1);
                change.data = data;
                change.col = col;
                return change;
            }
        }
        return new DataChange(data, col);
    }

    /**
     * Indicates the DataChange is no longer needed and can be reused.
     */
    public static void releaseDataChange(DataChange change) {
        synchronized(sharedDataChanges) {
            sharedDataChanges.add(change);
        }
    }


    DataChange(Data data, int col) {
        this.data = data;
        this.col = col;
        hashCode = (data.hashCode() | col);
    }

    public Data getData() {
        return data;
    }

    public int getColumn() {
        return col;
    }

    public int hashCode() {
        return hashCode;
    }

    public boolean equals(DataChange dc) {
        if (dc == this) {
            return true;
        }
        DataChange o = (DataChange)dc;
        return (o.data == data && o.col == col);
    }
}
