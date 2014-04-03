package userInterface;

import javax.jws.WebParam.Mode;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

public class BorrowerAccountPanel extends JPanel implements ListSelectionListener  {
	    private DefaultListModel listModel;
	    private BorrowerView parent;
	    private Controller mySession;
	    private static final String sendMessageString = "Send Message";
	    private static final String searchString = "Search";
	    private JButton searchButton;
	    private JTable table;
	    private JComboBox<String> comboBox;
	    private String[] coloumnNames ={"Call Number", "Title", "Checked Out", "Due Date"};
	    private JButton btnPayFine;

	    public BorrowerAccountPanel(BorrowerView borrowerView, final Controller mySession) {
	        super(new BorderLayout());
	        parent = borrowerView;
	        this.mySession = mySession;

	        listModel = new DefaultListModel();
	        JScrollPane listScrollPane = new JScrollPane();

	        searchButton = new JButton(searchString);
	        searchButton.setActionCommand(searchString);
	        searchButton.addActionListener(new SearchListener());
	        //employeeName.addActionListener(hireListener);
	        //employeeName.getDocument().addDocumentListener(hireListener);
	       /* String name = listModel.getElementAt(
	                              list.getSelectedIndex()).toString();*/

	        //Create a panel that uses BoxLayout.
	        JPanel buttonPane = new JPanel();
	        buttonPane.setLayout(new BoxLayout(buttonPane,
	                                           BoxLayout.LINE_AXIS));
	        buttonPane.add(searchButton);
	        
	        comboBox = new JComboBox<String>();
	        populateComboBox();
	        buttonPane.add(comboBox);
	        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
	        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	        
	        JLabel lblSearchForBooks = new JLabel("Account Information");
	        add(lblSearchForBooks, BorderLayout.NORTH);

	        add(listScrollPane, BorderLayout.CENTER);
	        
	        table = new JTable();
	        table.setModel(new DefaultTableModel(
	        	new Object[][] {
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        		{null, null, null, null, null},
	        	},
	        	new String[] {
	        		"Call Number", "Title", "Checked Out", "Due Date", "Overdue?"
	        	}
	        ) {
	        	Class[] columnTypes = new Class[] {
	        		String.class, String.class, Object.class, Object.class, String.class
	        	};
	        	public Class getColumnClass(int columnIndex) {
	        		return columnTypes[columnIndex];
	        	}
	        	boolean[] columnEditables = new boolean[] {
	        		false, false, false, false, false
	        	};
	        	public boolean isCellEditable(int row, int column) {
	        		return false;
	        	}
	        });
	        table.getColumnModel().getColumn(0).setPreferredWidth(80);
	        table.getColumnModel().getColumn(1).setPreferredWidth(150);
	        table.getColumnModel().getColumn(2).setPreferredWidth(85);
	        table.setFocusable(false);
	        table.setRowSelectionAllowed(false);
	        listScrollPane.setViewportView(table);
	        add(buttonPane, BorderLayout.PAGE_END);
	        
	        btnPayFine = new JButton("Pay Fine");
	        
	        //TODO: make sure we won't get a null. Disable the 'pay fine' button until a row has been
	        //selected
	        btnPayFine.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
			        if(getSelectedFID() == -1){
			        	JOptionPane.showMessageDialog(getInstance(), "No fine selected");
			        	return;
			        }
			        
					PayFineDialog temp = new PayFineDialog(getInstance(), mySession, getSelectedFID(), parent.getLoggedInUser().getName(), getSelectedTitle(), getSelectedDate(), getSelectedAmount() );//parent.getLoggedInUser().getName());
					temp.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					
			        temp.setVisible(true);
				}
			});

	        buttonPane.add(btnPayFine);
	    }

	    private void populateComboBox() {
			comboBox.addItem("Hold Requests");
			comboBox.addItem("Loaned books");
			comboBox.addItem("Outstanding Fines");
			
		}
	    
	    private BorrowerAccountPanel getInstance(){
	    	return this;
	    }

		class SearchListener implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	        	TableModel temp = table.getModel();
	        	String subjectFilter = (String) comboBox.getSelectedItem();
	        	String[][] toAdd = null;
	        	if(subjectFilter.equals("Hold Requests")){
	        		try {
						toAdd = mySession.getHoldRequests(parent.getLoggedInUser());
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(getInstance(), "Could not load hold requests");
						e1.printStackTrace();
					}
	        	
	        		removeAllColoumnsFromTable();
	        		
	    	        table.setModel(new DefaultTableModel(
	    	        	new Object[][] {
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        		{null, null, null},
	    	        	},
	    	        	new String[] {
	    	        			"Call Number", "Title", "Author"
	    	        	}
	    	        ) {
	    	        	Class[] columnTypes = new Class[] {
	    	        		String.class, String.class, String.class
	    	        	};
	    	        	public Class getColumnClass(int columnIndex) {
	    	        		return columnTypes[columnIndex];
	    	        	}
	    	        	boolean[] columnEditables = new boolean[] {
	    	        		false, false, false, false, false
	    	        	};
	    	        	public boolean isCellEditable(int row, int column) {
	    	        		return false;
	    	        	}
	    	        });
	    	        table.setRowSelectionAllowed(true);
	        	}
	        	else if(subjectFilter.equals("Loaned books")){
	        		try {
						toAdd = mySession.getLoanedBooks(parent.getLoggedInUser());
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(getInstance(), "Could not load loaned books");
						e1.printStackTrace();
					}
	        	
	        		removeAllColoumnsFromTable();
	        		
	    	        table.setModel(new DefaultTableModel(
	    	        	new Object[][] {
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        	},
	    	        	new String[] {
	    	        		"Call Number", "Title", "Checked Out", "Due", "OverDue?"
	    	        	}
	    	        ) {
	    	        	Class[] columnTypes = new Class[] {
	    	        		String.class, String.class, Object.class, Object.class, String.class
	    	        	};
	    	        	public Class getColumnClass(int columnIndex) {
	    	        		return columnTypes[columnIndex];
	    	        	}
	    	        	boolean[] columnEditables = new boolean[] {
	    	        		false, false, false, false, false
	    	        			};
	    	        	public boolean isCellEditable(int row, int column) {
	    	        		return false;
	    	        	}
	    	        });
	    	        table.setRowSelectionAllowed(true);
	        	}
	        	else if(subjectFilter.equals("Outstanding Fines")){
	        		
	        		try {
						toAdd = mySession.getOutstandingFines(parent.getLoggedInUser());
					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(getInstance(), "Could not get fines");
						e1.printStackTrace();
						return;
					}
	        		removeAllColoumnsFromTable();
	        		
	    	        table.setModel(new DefaultTableModel(
	    	        	new Object[][] {
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        		{null, null, null, null, null},
	    	        	},
	    	        	new String[] {
	    	        			"Fine ID", "Title", "Checked Out", "Returned", "Fine Amount"
	    	        	}
	    	        ) {
	    	        	Class[] columnTypes = new Class[] {
	    	        		String.class, String.class, Object.class, Object.class, String.class
	    	        	};
	    	        	public Class getColumnClass(int columnIndex) {
	    	        		return columnTypes[columnIndex];
	    	        	}
	    	        	boolean[] columnEditables = new boolean[] {
	    	        		false, false, false, false, false
	    	        	};
	    	        	public boolean isCellEditable(int row, int column) {
	    	        		return false;
	    	        	}
	    	        });
	    	        table.getColumnModel().getColumn(0).setPreferredWidth(40);
	    	        table.getColumnModel().getColumn(1).setPreferredWidth(150);
	    	        table.getColumnModel().getColumn(2).setPreferredWidth(85);
	    	        table.setRowSelectionAllowed(true);
	        	}
	        	
	        	
	        	
	        	if(toAdd == null || toAdd.length ==0){
	        		System.out.println("returning, no elements to put in chart");

	        		return;
	        	}
	        	DefaultTableModel model = (DefaultTableModel) table.getModel();
	        	while(model.getRowCount()>0){
	        		model.removeRow(0);
	        	}
	        	
	        	System.out.println(toAdd[0].length);
	        	for(String[] eee : toAdd){
	        		/*if(Date.parse(eee[3])<System.currentTimeMillis()){
	        			eee[4] = "OVERDUE";
	        		}*/
	        		model.addRow(eee);
	        	}
		        
	        	
		        
	        }
	    }
		public void removeAllColoumnsFromTable(){
			TableColumnModel allColoumns = table.getColumnModel();
    		while(allColoumns.getColumns().hasMoreElements()){
    			allColoumns.removeColumn(allColoumns.getColumns().nextElement());
    		}
			
		}
	    //This method is required by ListSelectionListener.
	    public void valueChanged(ListSelectionEvent e) {
	        /*if (e.getValueIsAdjusting() == false) {

	            if (list.getSelectedIndex() == -1) {
	            //No selection, disable search button.
	                searchButton.setEnabled(false);

	            } else {
	            //Selection, enable the search button.
	                searchButton.setEnabled(true);
	            }
	        }*/
	    }

	    /**
	     * Create the GUI and show it.  For thread safety,
	     * this method should be invoked from the
	     * event-dispatching thread.
	     *//*
	    private static void createAndShowGUI() {
	        //Create and set up the window.
	        JFrame frame = new JFrame("ListDemo");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	        //Create and set up the content pane.
	        JComponent newContentPane = new ListDemo();
	        newContentPane.setOpaque(true); //content panes must be opaque
	        frame.setContentPane(newContentPane);

	        //Display the window.
	        frame.pack();
	        frame.setVisible(true);
	    }*/
	    
	    public int getSelectedFID(){
	    	if(table.getSelectedRow() == -1){
	    		return -1;
	    	}
	    	String fid = (String)table.getValueAt(table.getSelectedRow(), 0);
	    	try{
	    		return Integer.parseInt(fid);
	    	}catch(IllegalArgumentException e){
	    		return -1;
	    	}
	    	
	    }
	    
	   public String getSelectedTitle(){
		   if(table.getSelectedRow() == -1){
	    		return null;
	    	}
	    	return (String)table.getValueAt(table.getSelectedRow(), 1);
	   }
	   
	   
	   public String getSelectedAmount(){
		   if(table.getSelectedRow() == -1){
	    		return null;
	    	}
	    	return (String)table.getValueAt(table.getSelectedRow(), 4);
	   }
	    
	   public String getSelectedDate(){
		   if(table.getSelectedRow() == -1){
	    		return null;
	    	}
	    	return (String)table.getValueAt(table.getSelectedRow(), 3);
	   }
	   
	    public int getLoggedInUserBID(){
	    	return parent.getLoggedInUserBID();
	    }
}
