package userInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

public class AddNewUserDialog extends JDialog {
//TODO will need to make the rest of the application stop accepting inputs
	// see http://docs.oracle.com/javase/tutorial/uiswing/misc/modality.html
	private final JPanel contentPanel = new JPanel();
	private ClerkView parent;
	private Controller mySession;
	private JTextField txtTxtboxemailaddress;
	private JTextField txtPhoneNumber;
	private JTextField txtAddress;
	private JTextField txtName;
	private JTextField txtPassword;
	private JTextField txtBidshouldWe;
	private JTextField txtSinorstno;
	private JTextField txtDate;
	private ButtonGroup typeButtonGroup;
	
	/**
	 * Create the dialog.
	 */
	public AddNewUserDialog(ClerkView parent, Controller session) {
		this.parent = parent;
		this.mySession = session;
		setBounds(100, 100, 350, 350);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
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
			JLabel lblBid = new JLabel("BID");
			contentPanel.add(lblBid, "8, 2");
		}
		{
			txtName = new JTextField();
			//txtName.setText("Name");
			contentPanel.add(txtName, "2, 4, 5, 1, fill, default");
			txtName.setColumns(10);
		}
		{
			JLabel lblName = new JLabel("Name");
			contentPanel.add(lblName, "8, 4");
		}
		{
			txtPassword = new JTextField();
			//txtPassword.setText("Password");
			contentPanel.add(txtPassword, "2, 6, 5, 1, fill, default");
			txtPassword.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "8, 6");
		}
		{
			txtAddress = new JTextField();
			//txtAddress.setText("Address");
			contentPanel.add(txtAddress, "2, 8, 5, 1, fill, default");
			txtAddress.setColumns(10);
		}
		{
			JLabel lblAddress = new JLabel("Address");
			contentPanel.add(lblAddress, "8, 8");
		}
		{
			txtPhoneNumber = new JTextField();
			//txtPhoneNumber.setText("Phone Number");
			contentPanel.add(txtPhoneNumber, "2, 10, 5, 1, fill, default");
			txtPhoneNumber.setColumns(10);
		}
		{
			JLabel lblPhoneNumber = new JLabel("Phone Number");
			contentPanel.add(lblPhoneNumber, "8, 10");
		}
		{
			txtTxtboxemailaddress = new JTextField();
			//txtTxtboxemailaddress.setText("txtBoxEmailAddress");
			contentPanel.add(txtTxtboxemailaddress, "2, 12, 5, 1, fill, default");
			txtTxtboxemailaddress.setColumns(10);
		}
		{
			JLabel lblEmailAddress = new JLabel("Email Address");
			contentPanel.add(lblEmailAddress, "8, 12");
		}
		{
			txtSinorstno = new JTextField();
			//txtSinorstno.setText("SinOrStNo. ");
			contentPanel.add(txtSinorstno, "2, 14, 5, 1, fill, default");
			txtSinorstno.setColumns(10);
		}
		{
			JLabel lblSinorstnum = new JLabel("SinOrSt.Num");
			contentPanel.add(lblSinorstnum, "8, 14");
		}
		{
			txtDate = new JTextField();
			//txtDate.setText("Date");
			contentPanel.add(txtDate, "2, 16, 5, 1, fill, default");
			txtDate.setColumns(10);
		}
		{
			JLabel lblExpirydate = new JLabel("ExpiryDate");
			contentPanel.add(lblExpirydate, "8, 16");
		}
		{
			typeButtonGroup = new ButtonGroup();
		}
		{
			JRadioButton rdbtnClerk = new JRadioButton("Clerk");
			contentPanel.add(rdbtnClerk, "2, 18");
			typeButtonGroup.add(rdbtnClerk);
		}
		{
			JRadioButton rdbtnBorrower = new JRadioButton("Borrower");
			contentPanel.add(rdbtnBorrower, "4, 18");
			typeButtonGroup.add(rdbtnBorrower);
		}
		{
			JRadioButton rdbtnLibrarian = new JRadioButton("Librarian");
			contentPanel.add(rdbtnLibrarian, "6, 18");
			typeButtonGroup.add(rdbtnLibrarian);
		}
		{
			JLabel lblType = new JLabel("Type");
			contentPanel.add(lblType, "8, 18");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!isInputGood()){
							JOptionPane.showMessageDialog(getInstance(), "bad input");
							return;
						}
						
						try{
							mySession.createNewUser(turnIntoBorrower());
							closeDialogBox();
						}catch(SQLException e1){
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
	private AddNewUserDialog getInstance(){
		return this;
	}
	private boolean isInputGood(){
		//TODO check if input is actually correct
		return true;
	}
	private Borrower turnIntoBorrower(){
		//TODO turn the input into a borrower
		return null;
	}
	private void closeDialogBox(){
		this.dispose();
	}
}
