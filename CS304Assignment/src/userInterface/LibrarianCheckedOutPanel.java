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
import javax.swing.table.TableModel;

public class LibrarianCheckedOutPanel extends JPanel implements ListSelectionListener  {
	    private DefaultListModel listModel;
	    private LibrarianView parent;
	    private Controller mySession;
	    private static final String sendMessageString = "Send Message";
	    private static final String searchString = "Search";
	    private JButton searchButton;
	    private JTable table;
	    private JComboBox<String> comboBox;
	    private String[] coloumnNames ={"Call Number", "Title", "Checked Out", "Due Date"};

	    public LibrarianCheckedOutPanel(LibrarianView librarianView, Controller mySession) {
	        super(new BorderLayout());
	        parent = librarianView;
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
	        
	        JLabel lblSearchForBooks = new JLabel("Search For Books");
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
	        		return columnEditables[column];
	        	}
	        });
	        table.getColumnModel().getColumn(0).setPreferredWidth(80);
	        table.getColumnModel().getColumn(1).setPreferredWidth(150);
	        table.getColumnModel().getColumn(2).setPreferredWidth(85);
	        table.setFocusable(false);
	        table.setRowSelectionAllowed(false);
	        listScrollPane.setViewportView(table);
	        add(buttonPane, BorderLayout.PAGE_END);
	    }

	    private void populateComboBox() {
			ArrayList<String> subjects = null;
			try {
				subjects = mySession.getSubjects();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this,"Couldn't get subjects" + e1.getMessage());
				return;
			}
			
			comboBox.addItem("No Subject Filter");
			for(String e: subjects){
				comboBox.addItem(e);
			}
			
		}

		class SearchListener implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	        	TableModel temp = table.getModel();
	        	String subjectFilter = (String) comboBox.getSelectedItem();
	        	String[][] toAdd;
	        	if(subjectFilter.equals("No Subject Filter")){
	        		toAdd = mySession.getCheckOuts(null);
	        	}
	        	else{
	        		toAdd = mySession.getCheckOuts(subjectFilter);
	        	}
	        	DefaultTableModel model = (DefaultTableModel) table.getModel();
	        	while(model.getRowCount()>0){
	        		model.removeRow(0);
	        	}
	        	
	        	
	        	for(String[] eee : toAdd){
	        		if(Date.parse(eee[3])<System.currentTimeMillis()){
	        			eee[4] = "OVERDUE";
	        		}
	        		model.addRow(eee);
	        	}
		        
	        	table.setRowSelectionInterval(1, 2);
		        
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
}
