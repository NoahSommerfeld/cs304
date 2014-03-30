package userInterface;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class LibrarianCheckedOutPanel extends JPanel implements ListSelectionListener  {
	    private DefaultListModel listModel;
	    private LibrarianView parent;
	    private Controller mySession;
	    private static final String sendMessageString = "Send Message";
	    private static final String searchString = "Search";
	    private JButton searchButton;
	    private JButton sendMessageButton;
	    private JTextField employeeName;
	    private JTable table;

	    public LibrarianCheckedOutPanel(LibrarianView librarianView, Controller mySession) {
	        super(new BorderLayout());
	        parent = librarianView;
	        this.mySession = mySession;

	        listModel = new DefaultListModel();
	        JScrollPane listScrollPane = new JScrollPane();

	        sendMessageButton = new JButton(sendMessageString);
	        SendMessageListener sendMessageListener = new SendMessageListener(sendMessageButton);
	        sendMessageButton.setActionCommand(sendMessageString);
	        sendMessageButton.addActionListener(sendMessageListener);
	        sendMessageButton.setEnabled(false);

	        searchButton = new JButton(searchString);
	        searchButton.setActionCommand(searchString);
	        searchButton.addActionListener(new SearchListener());
/*
	        employeeName = new JTextField(10);
	        employeeName.addActionListener(hireListener);
	        employeeName.getDocument().addDocumentListener(hireListener);
	        String name = listModel.getElementAt(
	                              list.getSelectedIndex()).toString();*/

	        //Create a panel that uses BoxLayout.
	        JPanel buttonPane = new JPanel();
	        buttonPane.setLayout(new BoxLayout(buttonPane,
	                                           BoxLayout.LINE_AXIS));
	        buttonPane.add(searchButton);
	        buttonPane.add(Box.createHorizontalStrut(5));
	        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
	        buttonPane.add(Box.createHorizontalStrut(5));
	      // buttonPane.add(employeeName);
	        buttonPane.add(sendMessageButton);
	        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	        
	        JLabel lblSearchForBooks = new JLabel("Search For Books");
	        add(lblSearchForBooks, BorderLayout.NORTH);

	        add(listScrollPane, BorderLayout.CENTER);
	        
	        table = new JTable();
	        table.setModel(new DefaultTableModel(
	        	new Object[][] {
	        		{null, null, null, null},
	        		{null, null, null, null},
	        	},
	        	new String[] {
	        		"Call Number", "Title", "Checked Out", "Due Date"
	        	}
	        ) {
	        	Class[] columnTypes = new Class[] {
	        		String.class, String.class, Object.class, Object.class
	        	};
	        	public Class getColumnClass(int columnIndex) {
	        		return columnTypes[columnIndex];
	        	}
	        	boolean[] columnEditables = new boolean[] {
	        		false, false, false, false
	        	};
	        	public boolean isCellEditable(int row, int column) {
	        		return columnEditables[column];
	        	}
	        });
	        table.getColumnModel().getColumn(0).setPreferredWidth(80);
	        table.getColumnModel().getColumn(1).setPreferredWidth(239);
	        table.getColumnModel().getColumn(2).setPreferredWidth(85);
	        listScrollPane.setViewportView(table);
	        add(buttonPane, BorderLayout.PAGE_END);
	    }

	    class SearchListener implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	        	listModel.clear();
	        	ArrayList<String> slackers = null;
				try {
					slackers = mySession.searchForOverDues();
				} catch (SQLException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(parent, e1.getMessage());
					return;
				}
				if(slackers != null && slackers.size()>=1){
	        	for(String s : slackers){
	        		listModel.addElement(s);
	        	}
	        	sendMessageButton.setEnabled(true);
				}
		        /*listModel.addElement("Jane Doe");
		        listModel.addElement("John Smith");
		        listModel.addElement("Kathy Green");*/
	            //This method can be called only if
	            //there's a valid selection
	            //so go ahead and remove whatever's selected.
	            /*int index = list.getSelectedIndex();
	            listModel.remove(index);

	            int size = listModel.getSize();

	            if (size == 0) { //Nobody's left, disable firing.
	                searchButton.setEnabled(false);

	            } else { //Select an index.
	                if (index == listModel.getSize()) {
	                    //removed item in last position
	                    index--;
	                }

	                list.setSelectedIndex(index);
	                list.ensureIndexIsVisible(index);
	            }*/
	        }
	    }

	    //This listener is shared by the text field and the hire button.
	    class SendMessageListener implements ActionListener, DocumentListener {
	    	
	        private boolean alreadyEnabled = false;
	        private JButton button;

	        public SendMessageListener(JButton button) {
	            this.button = button;
	        }

	        //Required by ActionListener.
	        public void actionPerformed(ActionEvent e) {
	        	/*int[] index = list.getSelectedIndices();
	        	if(index == null || index.length == 0){
	        		if(listModel.getSize() == 0){
	        			sendMessageButton.setEnabled(false);
	        		}
	        		return;
	        	}
	        	
	        	for(int i : index){
	        		try{
	        		mySession.sendLateMessage((String) listModel.get(i));
	        		}
	        		catch(SQLException e2){
	        			JOptionPane.showMessageDialog(parent, e2.getMessage());
	        		}
	        		catch(Exception e3){
	        			JOptionPane.showMessageDialog(parent, e3.getMessage());
	        		}
	        	}
	        	list.clearSelection();
	        	/*
	            String name = employeeName.getText();

	            //User didn't type in a unique name...
	            if (name.equals("") || alreadyInList(name)) {
	                Toolkit.getDefaultToolkit().beep();
	                employeeName.requestFocusInWindow();
	                employeeName.selectAll();
	                return;
	            }

	            int index = list.getSelectedIndex(); //get selected index
	            if (index == -1) { //no selection, so insert at beginning
	                index = 0;
	            } else {           //add after the selected item
	                index++;
	            }

	            listModel.insertElementAt(employeeName.getText(), index);
	            //If we just wanted to add to the end, we'd do this:
	            //listModel.addElement(employeeName.getText());

	            //Reset the text field.
	            employeeName.requestFocusInWindow();
	            employeeName.setText("");

	            //Select the new item and make it visible.
	            list.setSelectedIndex(index);
	            list.ensureIndexIsVisible(index);*/
	        }

	        //This method tests for string equality. You could certainly
	        //get more sophisticated about the algorithm.  For example,
	        //you might want to ignore white space and capitalization.
	        protected boolean alreadyInList(String name) {
	            return listModel.contains(name);
	        }

	        //Required by DocumentListener.
	        public void insertUpdate(DocumentEvent e) {
	            enableButton();
	        }

	        //Required by DocumentListener.
	        public void removeUpdate(DocumentEvent e) {
	            handleEmptyTextField(e);
	        }

	        //Required by DocumentListener.
	        public void changedUpdate(DocumentEvent e) {
	            if (!handleEmptyTextField(e)) {
	                enableButton();
	            }
	        }

	        private void enableButton() {
	            if (!alreadyEnabled) {
	                button.setEnabled(true);
	            }
	        }

	        private boolean handleEmptyTextField(DocumentEvent e) {
	            if (e.getDocument().getLength() <= 0) {
	                button.setEnabled(false);
	                alreadyEnabled = false;
	                return true;
	            }
	            return false;
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
