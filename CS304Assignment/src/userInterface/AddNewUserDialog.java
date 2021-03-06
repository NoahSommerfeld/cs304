package userInterface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
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
	private JTextField txtSinorstno;
	private JTextField txtDate;
	private ButtonGroup typeButtonGroup;
	private JRadioButton rdbtnFaculty;
	private JRadioButton rdbtnStaff;
	private JRadioButton rdbtnStudent;
	
	/**
	 * Create the dialog.
	 */
	public AddNewUserDialog(ClerkView parent, Controller session) {
		setResizable(false);
		setTitle("Add New User");
		this.parent = parent;
		this.mySession = session;
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
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			txtName = new JTextField();
			//txtName.setText("Name");
			contentPanel.add(txtName, "2, 2, 5, 1, fill, default");
			txtName.setColumns(10);
		}
		{
			JLabel lblName = new JLabel("Name");
			contentPanel.add(lblName, "8, 2");
		}
		{
			txtPassword = new JTextField();
			//txtPassword.setText("Password");
			contentPanel.add(txtPassword, "2, 4, 5, 1, fill, default");
			txtPassword.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password");
			contentPanel.add(lblPassword, "8, 4");
		}
		{
			txtAddress = new JTextField();
			//txtAddress.setText("Address");
			contentPanel.add(txtAddress, "2, 6, 5, 1, fill, default");
			txtAddress.setColumns(10);
		}
		{
			JLabel lblAddress = new JLabel("Address");
			contentPanel.add(lblAddress, "8, 6");
		}
		{
			txtPhoneNumber = new JTextField();
			//txtPhoneNumber.setText("Phone Number");
			contentPanel.add(txtPhoneNumber, "2, 8, 5, 1, fill, default");
			txtPhoneNumber.setColumns(10);
		}
		{
			JLabel lblPhoneNumber = new JLabel("Phone Number");
			contentPanel.add(lblPhoneNumber, "8, 8");
		}
		{
			txtTxtboxemailaddress = new JTextField();
			//txtTxtboxemailaddress.setText("txtBoxEmailAddress");
			contentPanel.add(txtTxtboxemailaddress, "2, 10, 5, 1, fill, default");
			txtTxtboxemailaddress.setColumns(10);
		}
		{
			JLabel lblEmailAddress = new JLabel("Email Address");
			contentPanel.add(lblEmailAddress, "8, 10");
		}
		{
			txtSinorstno = new JTextField();
			
			contentPanel.add(txtSinorstno, "2, 12, 5, 1, fill, default");
			txtSinorstno.setColumns(10);
		}
		{
			JLabel lblSinorstnum = new JLabel("SinOrSt.Num");
			contentPanel.add(lblSinorstnum, "8, 12");
		}
		{
			txtDate = new JTextField();
			//txtDate.setText("January 12, 2015");
			contentPanel.add(txtDate, "2, 14, 5, 1, fill, default");
			txtDate.setColumns(10);
		}
		{
			JLabel lblExpirydate = new JLabel("ExpiryDate");
			contentPanel.add(lblExpirydate, "8, 14");
		}
		{
			typeButtonGroup = new ButtonGroup();
		}
		{
			rdbtnFaculty = new JRadioButton("Faculty");
			contentPanel.add(rdbtnFaculty, "2, 16");
			typeButtonGroup.add(rdbtnFaculty);
		}
		{
			rdbtnStaff = new JRadioButton("Staff");
			contentPanel.add(rdbtnStaff, "4, 16");
			typeButtonGroup.add(rdbtnStaff);
		}
		{
			rdbtnStudent = new JRadioButton("Student");
			contentPanel.add(rdbtnStudent, "6, 16");
			typeButtonGroup.add(rdbtnStudent);
		}
		{
			JLabel lblType = new JLabel("Type");
			contentPanel.add(lblType, "8, 16");
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
							User newUser = turnIntoUser();

							int BID = mySession.createNewUser(newUser);
							JOptionPane.showMessageDialog(getInstance(), "New User Created with BID: " + BID);
							closeDialogBox();
						}catch(UserCreationException ue){
							JOptionPane.showMessageDialog(getInstance(), ue.getMessage());
						}
						catch(SQLException e1){
							JOptionPane.showMessageDialog(getInstance(), e1.getMessage());
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
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
		UserType tempType;
		/*for(int i = 0; i<typeButtonGroup.getButtonCount(); i++){
			if(typeButtonGroup.isSelected(rdbtnLibrarian));
		}
		*/
		if(rdbtnFaculty.isSelected()){
			tempType = UserType.faculty;
		}
		else if(rdbtnStaff.isSelected()){
			tempType = UserType.staff;
		}
		else if(rdbtnStudent.isSelected()){
			tempType = UserType.student;
		}
		else{
			throw new UserCreationException("No type selected");
		}
		
		String phone = txtPhoneNumber.getText().replace("-", "");
		phone = phone.replace("(", "");
		phone = phone.replace(")", "");
		phone = phone.replace(" ", "");
		
		User temp = new User("-1", txtAddress.getText(), txtPassword.getText(), txtName.getText(), phone,
				txtTxtboxemailaddress.getText(), txtSinorstno.getText(), txtDate.getText(), tempType);
		

		return temp;
	}
	private void closeDialogBox(){
		parent.refreshStatsLbls();
		this.dispose();
	}
}
