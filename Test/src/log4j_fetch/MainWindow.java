/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package log4j_fetch;

/**
 *
 * @author kamal64
 */
import java.awt.Dimension;
import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingWorker;

/**
 *
 * @author Ruben Laguna <ruben.laguna at gmail.com>
 */
public class MainWindow extends javax.swing.JFrame {

    

    private static final Long startmili =System.currentTimeMillis();
	/** Creates new form MainWindow */
    public MainWindow() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        list1 = getList();
//        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = LazyViewport.createLazyScrollPaneFor(jTable1);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, list1, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("id");
        columnBinding.setColumnClass(Integer.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${date}"));
        columnBinding.setColumnName("DATE");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${message}"));
        columnBinding.setColumnName("MESSAGE");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
//        jTable1.getColumn("id").setPreferredWidth(10);
//        jTable1.getColumn("DATE").setPreferredWidth(20);
//        jTable1.getColumn("MESSAGE").setPreferredWidth(70);
//        jTable1.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                .addContainerGap())
        );
        
        JSplitPane jspPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        jspPane.setPreferredSize(new Dimension(800, 200));
        jspPane.add(jScrollPane1);
        getContentPane().add(jspPane);
        bindingGroup.bind();
        setSize(1000, 600);
//        pack();
    }// </editor-fold>                        



    private List<Customer> getList() {
        List<Customer> toReturn = new ResultList();
        return toReturn;
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
                
                Long endMili = System.currentTimeMillis();
            	Long millis = endMili - startmili;
            	System.out.println("End at :" + Calendar.getInstance().getTime());

            	System.out.println(String.format(
            			"%d min, %d sec",
            			TimeUnit.MILLISECONDS.toMinutes(millis),
            			TimeUnit.MILLISECONDS.toSeconds(millis)
            					- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
            							.toMinutes(millis))));
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private java.util.List<Customer> list1;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration                   


}

 class ResultList extends AbstractList {

    private Connection connection;
    private final ExecutorService ex = Executors.newSingleThreadExecutor();
    private int size = -1;
    //maintain a cache with the Customer instances already created and alive
    private Map<Integer, WeakReference<Customer>> cache = new HashMap<Integer, WeakReference<Customer>>();
String dbName ="d:\\sqlite\\testDB.db";
    ResultList() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection("jdbc:sqlite:"+dbName);
//            			stmt = c.createStatement();
        } catch (Exception ex) {
            Logger.getLogger(ResultList.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }

    public int size() {
        if (this.size == -1) {
            try {
                final ResultSet resultset = connection.createStatement().executeQuery("SELECT COUNT(level) FROM Test1 where level='DEBUG'");
                resultset.next();
                final int toReturn = resultset.getInt(1);
                this.size = toReturn;
            } catch (SQLException ex) {
                Logger.getLogger(ResultList.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }
        return this.size;
    }

    public Customer get(int rowIndex) {
        //this way we ensure that we don't create several Customer instances 
        //for the same id. Otherwise it would be confusing for beansbindings. 
        Customer toReturn = null;
        if (null != this.cache.get(rowIndex)) {
            toReturn = this.cache.get(rowIndex).get();
        }
        if (null == toReturn) {
            toReturn = getItem(rowIndex);
            this.cache.put(rowIndex, new WeakReference<Customer>(toReturn));
        }

        return toReturn;
    }

    private Customer getItem(final int j) {
        final Customer customer = new Customer(j);
//System.out.println("getItem :: "+j+"--"+customer);

        Runnable task = new SwingWorker() {

            private String dateValue;
            private String messageValue;

            @Override
            protected Object doInBackground() throws Exception {
                //this is always executed in a different thread from the current thread
                //it doesn't matter if the current thread is the EDT or a thread in the Executor
                final java.sql.Statement stmt = connection.createStatement();
                ResultSet executeQuery = stmt.executeQuery("SELECT date,message FROM Test1 where level='DEBUG'");
                executeQuery.next();
                dateValue = executeQuery.getString(1);
                messageValue = executeQuery.getString(2);
                return null;
            }

            @Override
            protected void done() {
                //this in the other hand will always be executed on the EDT.
                //This has to be done in the EDT because currently JTableBinding
                //is not smart enough to realize that the notification comes in another 
                //thread and do a SwingUtilities.invokeLater. So we are force to execute this
                // in the EDT. Seee http://markmail.org/thread/6ehh76zt27qc5fis and
                // https://beansbinding.dev.java.net/issues/show_bug.cgi?id=60
    
                customer.setDate(dateValue);
                customer.setMessage(messageValue);
//                Logger.getLogger(ResultList.class.getName()).info("updating customer " + customer);
            }
        };

        //NOTE that we don do task.execute()
        //posting the task to an Executor gives more control on 
        //how many threads are created. 
        ex.execute(task);
        return customer;
    }
}