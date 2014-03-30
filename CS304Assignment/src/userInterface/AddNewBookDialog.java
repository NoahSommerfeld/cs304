package userInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import exceptions.UserCreationException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import model.Book;
import model.UserType;

public class AddNewBookDialog extends JDialog {
//TODO will need to make the rest of the application stop accepting inputs
	// see http://docs.oracle.com/javase/tutorial/uiswing/misc/modality.html
	private final JPanel contentPanel = new JPanel();
	private LibrarianView parent;
	private Controller mySession;
	private JTextField txtTxtboxemailaddress;
	private JTextField txtPhoneNumber;
	private JTextField txtAddress;
	private JTextField txtName;
	private JTextField txtPassword;
	private JTextField txtBidshouldWe;
	private ButtonGroup typeButtonGroup;
	
	/**
	 * Create the dialog.
	 */
	public AddNewBookDialog(LibrarianView parent, Controller session) {
		setTitle("Add New User");
		this.parent = parent;
		this.mySession = session;
		setBounds(100, 100, 300, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			txtBidshouldWe = new JTextField();
			//txtBidshouldWe.setText("BID (should we autoGenerate?)");
			contentPanel.add(txtBidshouldWe, "2, 2, 5, 1, fill, default");
			txtBidshouldWe.setColumns(10);
		}
		{
			JLabel lblCallNo = new JLabel("BID");
			contentPanel.add(lblCallNo, "8, 2");
		}
		{
			txtName = new JTextField();
			//txtName.setText("Name");
			contentPanel.add(txtName, "2, 4, 5, 1, fill, default");
			txtName.setColumns(10);
		}
		{
			JLabel lblISBN = new JLabel("Name");
			contentPanel.add(lblISBN, "8, 4");
		}
		{
			txtPassword = new JTextField();
			//txtPassword.setText("Password");
			contentPanel.add(txtPassword, "2, 6, 5, 1, fill, default");
			txtPassword.setColumns(10);
		}
		{
			JLabel lblTitle = new JLabel("Password");
			contentPanel.add(lblTitle, "8, 6");
		}
		{
			txtAddress = new JTextField();
			//txtAddress.setText("Address");
			contentPanel.add(txtAddress, "2, 8, 5, 1, fill, default");
			txtAddress.setColumns(10);
		}
		{
			JLabel lblMainAuthor = new JLabel("Address");
			contentPanel.add(lblMainAuthor, "8, 8");
		}
		{
			txtPhoneNumber = new JTextField();
			//txtPhoneNumber.setText("Phone Number");
			contentPanel.add(txtPhoneNumber, "2, 10, 5, 1, fill, default");
			txtPhoneNumber.setColumns(10);
		}
		{
			JLabel lblPublisher = new JLabel("Phone Number");
			contentPanel.add(lblPublisher, "8, 10");
		}
		{
			txtTxtboxemailaddress = new JTextField();
			//txtTxtboxemailaddress.setText("txtBoxEmailAddress");
			contentPanel.add(txtTxtboxemailaddress, "2, 12, 5, 1, fill, default");
			txtTxtboxemailaddress.setColumns(10);
		}
		{
			JLabel lblYear = new JLabel("Email Address");
			contentPanel.add(lblYear, "8, 12");
		}
		{
			typeButtonGroup = new ButtonGroup();
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							Book newBook = turnIntoBook();

							mySession.createNewBook(newBook);
							JOptionPane.showMessageDialog(getInstance(), "New Book Created");
							closeDialogBox();
						}catch(UserCreationException ue){
							JOptionPane.showMessageDialog(getInstance(), ue.getMessage());
						}
						catch(SQLException e1){
							JOptionPane.showMessageDialog(getInstance(), e1.getMessage());
						}
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
	private AddNewBookDialog getInstance(){
		return this;
	}
	
	
	private boolean isInputGood(){
		//TODO check if input is actually correct
		return true;
	}
	private Book turnIntoBook() throws UserCreationException{
		

		return null;
	}
	private void closeDialogBox(){
		this.dispose();
	}
}
