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
import javax.swing.ImageIcon;
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

import model.User;
import model.UserType;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;

public class PayFineDialog extends JDialog {
//TODO will need to make the rest of the application stop accepting inputs
	// see http://docs.oracle.com/javase/tutorial/uiswing/misc/modality.html
	private final JPanel contentPanel = new JPanel();
	private BorrowerAccountPanel parent;
	private Controller mySession;
	private JTextField txtAmountDue;
	private JTextField txtBorrowerName;
	private JTextField txtIssueDate;
	private JTextField txtFID;
	private JTextField txtBookTitle;
	private ButtonGroup typeButtonGroup;
	private int fineID;
	private JComboBox cmboCardType;
	private final String defaultCardMessage = "<Card Type>";
	private JTextField textCardNumber;
	private JTextField textFineAmount;
	private String borrowerName;
	
	/**
	 * Create the dialog.
	 */
	public PayFineDialog(BorrowerAccountPanel parent, Controller session, int FID, String borrowerName) {
		setResizable(false);
		setTitle("Pay Fine");
		this.parent = parent;
		this.mySession = session;
		this.fineID = FID;
		this.borrowerName  = borrowerName;
		ImageIcon img = new ImageIcon("res/library-icon.png");
		this.setIconImage(img.getImage());
		setBounds(100, 100, 350, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(40dlu;default)"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(32dlu;default):grow"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			txtFID = new JTextField();
			txtFID.setHorizontalAlignment(SwingConstants.RIGHT);
			txtFID.setEditable(false);
			txtFID.setText("" + fineID);			
			//txtName.setText("Name");
			contentPanel.add(txtFID, "2, 2, 5, 1, fill, default");
			txtFID.setColumns(10);
		}
		{
			JLabel lblFID = new JLabel("Fine ID");
			contentPanel.add(lblFID, "8, 2");
		}
		{
			txtBookTitle = new JTextField();
			txtBookTitle.setHorizontalAlignment(SwingConstants.RIGHT);
			txtBookTitle.setEditable(false);
			//txtPassword.setText("Password");
			contentPanel.add(txtBookTitle, "2, 4, 5, 1, fill, default");
			txtBookTitle.setColumns(10);
		}
		{
			JLabel lblBookTitle = new JLabel("Book Title");
			contentPanel.add(lblBookTitle, "8, 4");
		}
		{
			txtIssueDate = new JTextField();
			txtIssueDate.setHorizontalAlignment(SwingConstants.RIGHT);
			txtIssueDate.setEditable(false);
			//txtAddress.setText("Address");
			contentPanel.add(txtIssueDate, "2, 6, 5, 1, fill, default");
			txtIssueDate.setColumns(10);
		}
		{
			JLabel lblIssueDate = new JLabel("Issue Date");
			contentPanel.add(lblIssueDate, "8, 6");
		}
		{
			txtBorrowerName = new JTextField();
			txtBorrowerName.setHorizontalAlignment(SwingConstants.RIGHT);
			txtBorrowerName.setEditable(false);
			txtBorrowerName.setText(this.borrowerName);
			//txtPhoneNumber.setText("Phone Number");
			contentPanel.add(txtBorrowerName, "2, 8, 5, 1, fill, default");
			txtBorrowerName.setColumns(10);
		}
		{
			JLabel lblBorrowerName = new JLabel("Borrower Name");
			contentPanel.add(lblBorrowerName, "8, 8");
		}
		{
			txtAmountDue = new JTextField();
			txtAmountDue.setHorizontalAlignment(SwingConstants.RIGHT);
			txtAmountDue.setEditable(false);
			txtAmountDue.setText("21.02"); //TODO: GET THIS FROM DANIEL!!
			//txtTxtboxemailaddress.setText("txtBoxEmailAddress");
			contentPanel.add(txtAmountDue, "2, 10, 5, 1, fill, default");
			txtAmountDue.setColumns(10);
		}
		{
			JLabel lblAmountDue = new JLabel("Amount Due");
			contentPanel.add(lblAmountDue, "8, 10");
		}
		{
			cmboCardType = new JComboBox();			
			cmboCardType.setModel(new DefaultComboBoxModel(new String[] {defaultCardMessage, "Visa", "MasterCard", "Maestro", "Amex"}));
			contentPanel.add(cmboCardType, "4, 14, fill, default");
		}
		{
			textCardNumber = new JTextField();
			textCardNumber.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(textCardNumber, "6, 14, fill, default");
			textCardNumber.setColumns(10);
		}
		{
			JLabel lblNewLabel = new JLabel("Card Number");
			contentPanel.add(lblNewLabel, "8, 14");
		}
		{
			JLabel lblDollarSign = new JLabel("$");
			contentPanel.add(lblDollarSign, "4, 16, right, default");
		}
		{
			textFineAmount = new JTextField();
			textFineAmount.setEditable(false);
			textFineAmount.setText(txtAmountDue.getText()); //TODO: Fix this!!
			textFineAmount.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(textFineAmount, "6, 16, fill, default");
			textFineAmount.setColumns(10);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Amount");
			contentPanel.add(lblNewLabel_1, "8, 16");
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
						
						if(textCardNumber.getText() == null || textCardNumber.getText().equals("")){
							JOptionPane.showMessageDialog(getInstance(), "Please Insert a Credit Card Number");
							return;
						}
						String cardType = (String) cmboCardType.getSelectedItem();
						if(cmboCardType.getSelectedIndex() == -1 || cardType.equals(defaultCardMessage)){
							JOptionPane.showMessageDialog(getInstance(), "Please select a Card Type");
							return;
						}
						
						try{
							double payment = Double.parseDouble(textFineAmount.getText());
							int BID = getInstance().parent.getLoggedInUserBID();
							int creditCardNum = Integer.parseInt(textCardNumber.getText().replace(" ", ""));
							mySession.processPayment(fineID, BID, payment, creditCardNum);
							JOptionPane.showMessageDialog(getInstance(), "Payment Processed");
							closeDialogBox();
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
	private PayFineDialog getInstance(){
		return this;
	}
	
	
	private boolean isInputGood(){
		//TODO check if input is actually correct
		return true;
	}
	private User turnIntoUser() throws UserCreationException{
		/*	private JTextField txtTxtboxemailaddress;
	private JTextField txtPhoneNumber;
	private JTextField txtAddress;
	private JTextField txtName;
	private JTextField txtPassword;
	private JTextField txtBidshouldWe;
	private JTextField txtSinorstno;
	private JTextField txtDate;*/
		/*public User(String BID, String password, String name, int phone, 
		String emailAddress, int sinOrStNo, Date expiryDate, BorrowerType type)*/

		/*for(int i = 0; i<typeButtonGroup.getButtonCount(); i++){
			if(typeButtonGroup.isSelected(rdbtnLibrarian));
		}
		*/
	
		

		return null;
	}
	private void closeDialogBox(){
		this.dispose();
	}
}
