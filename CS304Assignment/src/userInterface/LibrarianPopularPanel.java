package userInterface;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LibrarianPopularPanel extends JPanel implements ListSelectionListener  {
//Some code reverse engineered from http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
	

	  private JList list;
	    private DefaultListModel listModel;
	    private LibrarianView parent;
	    private Controller mySession;
	    private static final String sendMessageString = "Send Message";
	    private static final String searchString = "Search";
	    private static final String popularBookSearch = "Get Popular Books";
	    private JButton searchButton;
	  //  private JButton sendMessageButton;
	    private JTextField employeeName;
	    private JComboBox<String> numOfBooks;
	    private JComboBox<String> comboBoxYear;
	    private String defaultYearString = "year";
	    private String defaultNoResults = "# of Results";

	    public LibrarianPopularPanel(LibrarianView borrowerView, Controller mySession) {
	        super(new BorderLayout());
	        
	        parent = borrowerView;
	        this.mySession = mySession;

	        listModel = new DefaultListModel();


	        //Create the list and put it in a scroll pane.
	        list = new JList(listModel);
	        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	        list.setSelectedIndex(0);
	        list.addListSelectionListener(this);
	        list.setVisibleRowCount(5);
	        JScrollPane listScrollPane = new JScrollPane(list);

	       /* sendMessageButton = new JButton(sendMessageString);
	        SendMessageListener sendMessageListener = new SendMessageListener(sendMessageButton);
	        sendMessageButton.setActionCommand(sendMessageString);
	        sendMessageButton.addActionListener(sendMessageListener);
	        sendMessageButton.setEnabled(false);*/

	        searchButton = new JButton("Get Popular Books");
	        searchButton.setActionCommand(popularBookSearch);
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
	        
	        comboBoxYear = new JComboBox();
	        comboBoxYear.setModel(new DefaultComboBoxModel(new String[] {defaultYearString, "2013", "2012", "2011", "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001", "2000", "1999", "1998", "1997", "1996", "1995", "1994", "1993", "1992", "1991", "1990", "1989", "1988", "1987", "1986", "1985", "1984", "1983", "1982", "1981", "1980", "1979", "1978", "1977", "1976", "1975", "1974", "1973", "1972", "1971", "1970", "1969", "1968", "1967", "1966", "1965", "1964", "1963", "1962", "1961"}));
	        buttonPane.add(comboBoxYear);
	        
	        numOfBooks = new JComboBox();
	        numOfBooks.setModel(new DefaultComboBoxModel(new String[] {defaultNoResults, "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24"}));
	    
	        buttonPane.add(numOfBooks);
	      // buttonPane.add(employeeName);
	       // buttonPane.add(sendMessageButton);
	        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

	        add(listScrollPane, BorderLayout.CENTER);
	        
	        JPanel panel = new JPanel();
	        listScrollPane.setColumnHeaderView(panel);
	        
	        JLabel lblSearchForBooks = new JLabel("View Popular Books");
	        panel.add(lblSearchForBooks);
	        add(buttonPane, BorderLayout.PAGE_END);
	    }
	    public 
	    class SearchListener implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	     
	        	if(comboBoxYear.getSelectedItem().equals(defaultYearString)){
	        		JOptionPane.showMessageDialog(parent, "Please Select Year");
	        		return;
	        	}
	        	if(numOfBooks.getSelectedItem().equals(defaultNoResults)){
	        		JOptionPane.showMessageDialog(parent, "Please Select Number of Results");
	        		return;
	        	}
	        	
	        	listModel.clear();
	        	ArrayList<String> slackers = null;
	        	
				try {
					slackers = (ArrayList<String>) mySession.getPopularBooks(Integer.parseInt((String) comboBoxYear.getSelectedItem()), Integer.parseInt((String) numOfBooks.getSelectedItem()));
				} catch (SQLException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(parent, e1.getMessage());
					return;
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(getInstance(),"Something went wrong");
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(slackers != null && slackers.size()>=1){
	        	for(String s : slackers){
	        		listModel.addElement(s);
	        	}
	        	//sendMessageButton.setEnabled(true);
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
	        	int[] index = list.getSelectedIndices();
	        	if(index == null || index.length == 0){
	        		if(listModel.getSize() == 0){
	        	//		sendMessageButton.setEnabled(false);
	        		}
	        		return;
	        	}
	        	
	        	for(int i : index){
	        		/*try{
	        		mySession.sendLateMessage((String) listModel.get(i));
	        		}
	        		catch(SQLException e2){
	        			JOptionPane.showMessageDialog(parent, e2.getMessage());
	        		}
	        		catch(Exception e3){
	        			JOptionPane.showMessageDialog(parent, e3.getMessage());
	        		}*/
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
	        if (e.getValueIsAdjusting() == false) {

	            if (list.getSelectedIndex() == -1) {
	            //No selection, disable search button.
	                searchButton.setEnabled(false);

	            } else {
	            //Selection, enable the search button.
	                searchButton.setEnabled(true);
	            }
	        }
	    }

		public Component getInstance() {
			// retur
			return this;
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
