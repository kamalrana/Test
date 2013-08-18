package log4j_fetch;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

public class PaginationJTableDemo extends javax.swing.JFrame {
	private DefaultTableModel model;
	// private PaginationTableModel model;
	private String[] headers = { "Date", "level", "message", "no" };
	private Vector userVector = new Vector();
	private static int rowPerPage = 30;
	private int totalPage;
	private int totalRow;
	int currentPage = 1;
	private String sqlm;

	/** Creates new form PaginationJTableDemo */
	public PaginationJTableDemo() {
		initComponents();
		model = new DefaultTableModel(headers, 10);

		totalRow = getCount("select count(*) from test1 where level='DEBUG'");
		System.out.println("totoal row " + totalRow);
		this.sqlm = "select * from test1 where level='DEBUG'";
		this.totalPage = (totalRow) / rowPerPage;
		if (totalPage % rowPerPage > 0)
			this.totalPage++;
		System.out.println("totoal page " + totalPage + "& :: " + totalPage
				% rowPerPage);
		this.contentTable.setModel(model);
		showPage(1);
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		prevPageBtn = new javax.swing.JButton();
		nextBtn = new javax.swing.JButton();
		firstBtn = new javax.swing.JButton();
		lastBtn = new javax.swing.JButton();
		currentPageLable = new javax.swing.JLabel();
		currentPageNum = new javax.swing.JLabel();
		jPanel2 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		contentTable = new javax.swing.JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		prevPageBtn.setText("Prev Page");
		prevPageBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				prevPageBtnActionPerformed(evt);
			}
		});
		jPanel1.add(prevPageBtn);

		nextBtn.setText("next page");
		nextBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				nextBtnActionPerformed(evt);
			}
		});
		jPanel1.add(nextBtn);

		firstBtn.setText("first page");
		firstBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				firstBtnActionPerformed(evt);
			}
		});
		jPanel1.add(firstBtn);

		lastBtn.setText("last page");
		lastBtn.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				lastBtnActionPerformed(evt);
			}
		});
		jPanel1.add(lastBtn);

		currentPageLable.setText("curren:");
		jPanel1.add(currentPageLable);
		jPanel1.add(currentPageNum);
//		jPanel1.setBounds(1, 1, 1, 1);
		
		getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

		jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

		contentTable.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null, null },
						{ null, null, null, null }, { null, null, null, null },
						{ null, null, null, null } }, new String[] { "Title 1",
						"Title 2", "Title 3", "Title 4" }));
		
		contentTable.setAutoCreateRowSorter(true);
		contentTable.setFillsViewportHeight(true);
		contentTable.setPreferredScrollableViewportSize(new Dimension(1000,500));
		contentTable.setRowSelectionAllowed(true);
//		contentTable.getColumn("Title 1").setPreferredWidth(10);
		jScrollPane1.setViewportView(contentTable);

		contentTable.setAutoCreateRowSorter(true);
		jPanel2.add(jScrollPane1);

		getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>

	private void nextBtnActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
		if (currentPage < totalPage) {
			currentPage++;
			System.out.println("curren page :: " + currentPage + "&& :: "
					+ ((currentPage * 30) + 1));
			showPage((currentPage * 30) + 1);

			this.currentPageNum.setText("" + currentPage);
		}
	}

	private void prevPageBtnActionPerformed(java.awt.event.ActionEvent evt) {
		if (currentPage > 1) {
			// currentPage = (currentPage/30) - 1;
			System.out.println(currentPage);
			System.out.println(currentPage--);
			System.out.println("previous page records "+((currentPage - 1)*30));
			showPage((currentPage - 1)*30);
			this.currentPageNum.setText("" + currentPage);
		}
	}

	private void firstBtnActionPerformed(java.awt.event.ActionEvent evt) {
		if(currentPage==1)
			return;
		currentPage = 1;
		showPage(currentPage);
		this.currentPageNum.setText("" + currentPage);
	}

	private void lastBtnActionPerformed(java.awt.event.ActionEvent evt) {
		if(currentPage==totalPage)
			return;
		currentPage = totalPage;
		System.out.println("total page :: " + totalPage);
		showPage(currentPage);
		this.currentPageNum.setText("" + currentPage);
	}

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new PaginationJTableDemo().setVisible(true);
			}
		});
	}

	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.sqlite.JDBC");
			String url = "jdbc:sqlite:c:/sqlite/testDB.db";
			String user = "";
			String pwd = "";
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public int getCount(String sql) {
		int result = 0;
		try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void showPage(int i) {
		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int rows, int column) {
				return false;
			}
		};
		final int key = model.getRowCount();

		model.setColumnIdentifiers(headers);
		System.out.println("Current page is:" + currentPage);

		TableSwingWorker readFromDbWorker = new TableSwingWorker(model,
				contentTable);
		readFromDbWorker.execute();

		System.out.println(readFromDbWorker.isDone() + " rows: "
				+ readFromDbWorker.getTempTableMode().getRowCount());
		this.contentTable.setModel(model);
	}

	private javax.swing.JTable contentTable;
	private javax.swing.JButton firstBtn;
	private javax.swing.JLabel currentPageLable;
	private javax.swing.JLabel currentPageNum;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JButton lastBtn;
	private javax.swing.JButton nextBtn;
	private javax.swing.JButton prevPageBtn;

	private class FetchDataFromDBWorker extends
			SwingWorker<Vector<Vector>, Vector<Vector>> {

		@Override
		protected Vector<Vector> doInBackground() throws Exception {
			throw new UnsupportedOperationException("Not supported yet.");
		}

	}

	class TableSwingWorker extends SwingWorker<DefaultTableModel, Object[]> {
		private DefaultTableModel tableModel;

		public DefaultTableModel getTempTableMode() {
			return tableModel;
		}

		private boolean loadDone = false;
		private final JTable contentTable;

		public TableSwingWorker(DefaultTableModel tableModel,
				JTable contentTable) {
			this.tableModel = tableModel;
			this.contentTable = contentTable;
			tableModel = new DefaultTableModel() {
				@Override
				public boolean isCellEditable(int rows, int column) {
					return false;
				}
			};
		}

		@Override
		protected DefaultTableModel doInBackground() throws Exception {
			loadData();
			return tableModel;
		}

		@Override
		protected void done() {
			String text;
			int i = -1;
			// DefaultTableModel model =null;
			if (isCancelled()) {
				text = "Cancelled";
			} else {
				try {
					tableModel = get();
					System.out.println("in done=" + tableModel.getRowCount());
				} catch (Exception ignore) {
					ignore.printStackTrace();
					text = ignore.getMessage();
				}
			}
			// System.out.println(key +":"+text+"("+i+"ms)");
		}

		private Object[] loadData() {
			ResultSet rs = null;
			Object[] user = null;
			try {
				Connection conn = getConnection();
				Statement stmt = conn.createStatement();
				String sql = sqlm + " limit " + ( (currentPage-1) * 30) + ","
						+ rowPerPage;
				System.out.println(sql);
				rs = stmt.executeQuery(sql);
				int i = 1;
				while (rs.next()) {
					user = new Object[4];
					user[0] = rs.getString(1);
					user[1] = (rs.getString(2));
					user[2] = (rs.getString(3));
					user[3] = i++;
					tableModel.addRow(user);

				}
				loadDone = true;
				currentPageNum.setText(currentPage+"");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return user;
		}
	}

}
