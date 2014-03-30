package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import exceptions.BadCallNumberException;
import exceptions.BadUserIDException;
import exceptions.NotCheckedInException;
import exceptions.UserCreationException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import model.UserType;

import javax.swing.JList;

public class CheckOutItemsDialog extends JDialog {
//TODO will need to make the rest of the application stop accepting inputs
	// see http://docs.oracle.com/javase/tutorial/uiswing/misc/modality.html
	private final JPanel contentPanel = new JPanel();
	private ClerkView parent;
	private Controller mySession;
	private JTextField txtBidshouldWe;
	private ButtonGroup typeButtonGroup;
	private JTextField txtCallnumber;
	private JList listOfBooks;
	private JButton btnAddButton;
	private JButton btnRemoveButton;
	private DefaultListModel listModel;
	
	/**
	 * Create the dialog.
	 */
	public CheckOutItemsDialog(ClerkView parent, Controller session) {
		setTitle("Check Out Items");
		this.parent = parent;
		this.mySession = session;
		ImageIcon img = new ImageIcon("res/library-icon.png");
		this.setIconImage(img.getImage());
		setBounds(100, 100, 400, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(39dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(33dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(35dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("center:max(33dlu;default)"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(92dlu;default):grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			txtBidshouldWe = new JTextField();
			//txtBidshouldWe.setText("BID (should we autoGenerate?)");
			contentPanel.add(txtBidshouldWe, "2, 2, 5, 1, fill, default");
			txtBidshouldWe.setColumns(10);
		}
		{
			JLabel lblBid = new JLabel("BID");
			contentPanel.add(lblBid, "8, 2");
		}
		{
			JLabel lblCheckOutList = new JLabel("Check Out List");
			contentPanel.add(lblCheckOutList, "4, 4");
		}
		{
			listModel = new DefaultListModel();
			listOfBooks = new JList(listModel);
			listOfBooks.addListSelectionListener(new ListSelectionListener(){

				@Override
				public void valueChanged(ListSelectionEvent e) { 
					if (listOfBooks.getSelectedIndex() == -1) {
			            //No selection, disable fire button.
						btnRemoveButton.setEnabled(false);

		            } else {
		            //Selection, enable the fire button.
		            	btnRemoveButton.setEnabled(true);
		            }
					
				}
				
			});
			listOfBooks.setBorder(BorderFactory.createLineBorder(Color.black));
			listOfBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			contentPanel.add(listOfBooks, "2, 6, 5, 1, fill, fill");
		}
		{
			txtCallnumber = new JTextField();
			txtCallnumber.setText("CallNumber");
			contentPanel.add(txtCallnumber, "2, 8, fill, default");
			txtCallnumber.setColumns(10);
		}
		{
			btnAddButton = new JButton("Add");
			btnAddButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					try{
						if(!mySession.confirmOkToCheckOut(txtCallnumber.getText())){
							JOptionPane.showMessageDialog(getInstance(), "Call number invalid for some reason");
							return;
						};
					}catch(BadCallNumberException bcn){
						JOptionPane.showMessageDialog(getInstance(), "Call number invalid");
						return;
					}catch(NotCheckedInException nci){
						JOptionPane.showMessageDialog(getInstance(), "Not Checked IN"); //TODO: handle if it's on hold. 
						return; //maybe. might just be on hold
					}
					
					
					listModel.addElement(txtCallnumber.getText());
					
					txtCallnumber.setText("");
				}
				
			});
			contentPanel.add(btnAddButton, "4, 8");
		}
		{
			btnRemoveButton = new JButton("Remove");
			btnRemoveButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					int index = listOfBooks.getSelectedIndex();
		            listModel.remove(index);
					
				}
				
			});
			contentPanel.add(btnRemoveButton, "6, 8");
			btnRemoveButton.setEnabled(false);
		}
		{
			typeButtonGroup = new ButtonGroup();
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Check Out");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int numberOfBooks = listModel.getSize();
						
						for(int i = 0; i<numberOfBooks; i++){
							try {
								mySession.checkOut((String)listModel.get(i), txtBidshouldWe.getText());
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(getInstance(), "SQLException");
								//return; //TODO wanring! If we encounter errors half way through the list, we'd probably have to revert it.
							}
							 catch (NotCheckedInException e2) {
								 //TODO check if it was on hold. 
								 JOptionPane.showMessageDialog(getInstance(), "Not Checked In");
								}
							 catch (BadCallNumberException e3) {
									// TODO Auto-generated catch block
								 JOptionPane.showMessageDialog(getInstance(), "Bad Call number (how did it get throw the first check?)");
								// return;
								}
							 catch (BadUserIDException e4) {
									// TODO Auto-generated catch block
								 JOptionPane.showMessageDialog(getInstance(), "User Not Found in the DB");
								return;
								}
						}
						//TODO this is a terrible idea. If there's an error, should abandon everything.
						 JOptionPane.showMessageDialog(getInstance(), "Items checked out (except for those with errors)");
						 closeDialogBox();
						
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeDialogBox();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
	}
	private CheckOutItemsDialog getInstance(){
		return this;
	}
	
	private void closeDialogBox(){
		this.dispose();
	}
}
