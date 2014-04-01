package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
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

import model.BookCopy;
import model.UserType;

import javax.swing.JList;
import javax.swing.SwingConstants;

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
	private JTextField textCopyNo;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	int copyNo;
	
	/**
	 * Create the dialog.
	 */
	public CheckOutItemsDialog(ClerkView parent, Controller session) {
		setResizable(false);
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
				FormFactory.DEFAULT_COLSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			txtBidshouldWe = new JTextField();
			txtBidshouldWe.setHorizontalAlignment(SwingConstants.RIGHT);
			//txtBidshouldWe.setText("BID (should we autoGenerate?)");
			contentPanel.add(txtBidshouldWe, "8, 2, fill, default");
			txtBidshouldWe.setColumns(10);
		}
		{
			JLabel lblBid = new JLabel("BID");
			contentPanel.add(lblBid, "10, 2");
		}
		{
			JLabel lblCheckOutList = new JLabel("Check Out List");
			contentPanel.add(lblCheckOutList, "6, 4");
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
			contentPanel.add(listOfBooks, "2, 6, 7, 1, fill, fill");
		}
		{
			btnAddButton = new JButton("Add");
			btnAddButton.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					copyNo = -1;
					String title = null;
					try{
						copyNo = Integer.parseInt(textCopyNo.getText());
					}
					catch(IllegalArgumentException e6){
						JOptionPane.showMessageDialog(getInstance(), "Copy Number formatted wrong");
						return;
					}
					try{
						if(txtCallnumber.getText() == "" || txtCallnumber.getText() == null){
							return;
						}
						title = mySession.confirmOkToCheckOut(txtCallnumber.getText(), copyNo);
							System.out.println("yep: "+ txtCallnumber.getText());
							
						
						
					}catch(BadCallNumberException bcn){
						JOptionPane.showMessageDialog(getInstance(), bcn.getMessage());
						textCopyNo.setText("");
						return;
					}catch(NotCheckedInException nci){
						JOptionPane.showMessageDialog(getInstance(), "Not Checked In"); //TODO: handle if it's on hold. 
						return; //maybe. might just be on hold
					}catch(SQLException sql){
						JOptionPane.showMessageDialog(getInstance(), "bad Query"); //TODO: handle if it's on hold.
						sql.printStackTrace();
						return;
					}
					
					
					listModel.addElement("Copy "+copyNo +" - "+txtCallnumber.getText());
					
					
					txtCallnumber.setText("");
					textCopyNo.setText("");
				}
				
			});
			{
				lblNewLabel = new JLabel("Call Number");
				lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
				contentPanel.add(lblNewLabel, "2, 8");
			}
			{
				lblNewLabel_1 = new JLabel("Copy #");
				contentPanel.add(lblNewLabel_1, "4, 8");
			}
			{
				txtCallnumber = new JTextField();
				txtCallnumber.setText("CallNumber");
				contentPanel.add(txtCallnumber, "2, 10, fill, default");
				txtCallnumber.setColumns(10);
			}
			{
				textCopyNo = new JTextField();
				textCopyNo.setHorizontalAlignment(SwingConstants.CENTER);
				contentPanel.add(textCopyNo, "4, 10, fill, default");
				textCopyNo.setColumns(10);
			}
			contentPanel.add(btnAddButton, "6, 10");
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
			contentPanel.add(btnRemoveButton, "8, 10");
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
						
						ArrayList<BookCopy> checkouts = new ArrayList<BookCopy>();
						
						for(int i = 0; i<listModel.size(); i++){
							String phrase = (String) listModel.get(i);
							int thisCopyNo = 0;
							String tempString = phrase.substring("copy ".length());
							for(int spotCount = 0; tempString.charAt(i) != ' '; i++){
								thisCopyNo++;
							}
							try{
								thisCopyNo = Integer.parseInt(tempString.substring(0, thisCopyNo));
							}catch(IllegalArgumentException ilae){
							System.out.println("Couldn't convert copy number from the list");
							ilae.printStackTrace();
							return;
						}
						
						System.out.println("Copy no: " + thisCopyNo);
						String temp = ""+thisCopyNo;
						String callNumber = phrase.substring("Copy ".length() + temp.length() + " - ".length());
						
						

						checkouts.add(new BookCopy(callNumber, thisCopyNo)); //remove the copyNo at the beggining
						
						
						
					}
					//	for(int i = 0; i<numberOfBooks; i++){
							try {
							
								if(txtBidshouldWe.getText() == null || txtBidshouldWe.getText().equals("")){
									System.out.println("GSDGDSGFDG");
								}
								mySession.checkOut(checkouts, Integer.parseInt(txtBidshouldWe.getText()));
								
								
							} catch (SQLException e1) {
								JOptionPane.showMessageDialog(getInstance(), "SQLException");
								//return; //TODO wanring! If we encounter errors half way through the list, we'd probably have to revert it.
								return;
							}
							 catch (NotCheckedInException e2) {
								 //TODO check if it was on hold. 
								 JOptionPane.showMessageDialog(getInstance(), "Not Checked In");
								 return;
								}
							 catch (BadCallNumberException e3) {
									// TODO Auto-generated catch block
								 JOptionPane.showMessageDialog(getInstance(), "Bad Call number (how did it get thru the first check?)");
								 
								 return;
								}
							 catch (BadUserIDException e4) {
									// TODO Auto-generated catch block
								 JOptionPane.showMessageDialog(getInstance(), "User Not Found in the DB");
								return;
								}
							catch(IllegalArgumentException e5){
								 JOptionPane.showMessageDialog(getInstance(), "BID not an int");
								 return;
							} catch (UserCreationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
					//	}
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
