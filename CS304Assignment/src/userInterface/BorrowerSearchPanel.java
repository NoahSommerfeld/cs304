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

import model.SearchAbleKeywords;

public class BorrowerSearchPanel extends JPanel implements ListSelectionListener  {
//Some code reverse engineered from http://docs.oracle.com/javase/tutorial/uiswing/examples/components/ListDemoProject/src/components/ListDemo.java
	

	  private JList list;
	    private DefaultListModel listModel;
	    private BorrowerView parent;
	    private Controller mySession;
	    private static final String sendMessageString = "Send Message";
	    private static final String searchString = "Search";
	    private JTextField employeeName;
	    private JTextField searchArgument;
	    private JButton btnSearch;
	    private JButton btbPlaceOnHold;
	    private JComboBox cmboKeyWords;

	    public BorrowerSearchPanel(BorrowerView borrowerView, Controller mySession) {
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
/*
	        employeeName = new JTextField(10);
	        employeeName.addActionListener(hireListener);
	        employeeName.getDocument().addDocumentListener(hireListener);
	        String name = listModel.getElementAt(
	                              list.getSelectedIndex()).toString();*/

	        //Create a panel that uses BoxLayout.
	        JPanel buttonPane = new JPanel();
	        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

	        add(listScrollPane, BorderLayout.CENTER);
	        
	        JPanel panel = new JPanel();
	        listScrollPane.setColumnHeaderView(panel);
	        
	        JLabel lblSearchForBooks = new JLabel("Search For Books");
	        panel.add(lblSearchForBooks);
	        add(buttonPane, BorderLayout.PAGE_END);
	        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.X_AXIS));
	        
	        JSplitPane splitPane = new JSplitPane();
	        splitPane.setContinuousLayout(true);
	        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
	        buttonPane.add(splitPane);
	        
	        JPanel panel_1 = new JPanel();
	        splitPane.setLeftComponent(panel_1);
	        
	        cmboKeyWords = new JComboBox();
	        cmboKeyWords.setModel(new DefaultComboBoxModel(new String[] {"Title", "Author", "Subject"}));
	        cmboKeyWords.setSize(100, 75);
	        panel_1.add(cmboKeyWords);
	        
	        searchArgument = new JTextField();
	        panel_1.add(searchArgument);
	        searchArgument.setColumns(10);
	        
	        JPanel panel_2 = new JPanel();
	        splitPane.setRightComponent(panel_2);
	        
	        btnSearch = new JButton("Search");
	        btnSearch.addActionListener(new SearchListener());
	        panel_2.add(btnSearch);
	        
	        btbPlaceOnHold = new JButton("Place Holding");
	        panel_2.add(btbPlaceOnHold);
	    }

	    //This method is required by ListSelectionListener.
	    public void valueChanged(ListSelectionEvent e) {
	        if (e.getValueIsAdjusting() == false) {

	            if (list.getSelectedIndex() == -1) {
	            //No selection, disable search button.
	               // searchButton.setEnabled(false);

	            } else {
	            //Selection, enable the search button.
	             //  searchButton.setEnabled(true);
	            }
	        }
	    }
	    
	    public int getLoggedInUserBID(){
	    	return this.getLoggedInUserBID();
	    }
	    
	    class SearchListener implements ActionListener {
	        public void actionPerformed(ActionEvent e) {
	        	listModel.clear();
	        	ArrayList<String> searchResults = null;
				try {
					searchResults	 = mySession.searchBooks(SearchAbleKeywords.valueOf((String)cmboKeyWords.getSelectedItem()), searchArgument.getText());
				} catch (SQLException e1) {
					//e1.printStackTrace();
					JOptionPane.showMessageDialog(parent, e1.getMessage());
					return;
				}
				if(searchResults != null && searchResults.size()>=1){
	        	for(String s : searchResults){
	        		listModel.addElement(s);
	        	}
	        	btbPlaceOnHold.setEnabled(true);
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
