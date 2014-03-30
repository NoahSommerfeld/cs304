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

import exceptions.BadCopyNumberException;
import exceptions.BookCreationException;
import exceptions.UserCreationException;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JRadioButton;

import model.Book;
import model.BorrowStatus;
import model.UserType;

public class AddNewBookDialog extends JDialog {
//TODO will need to make the rest of the application stop accepting inputs
	// see http://docs.oracle.com/javase/tutorial/uiswing/misc/modality.html
	private final JPanel contentPanel = new JPanel();
	private LibrarianView parent;
	private Controller mySession;
	private JTextField txtYear;
	private JTextField txtPublisher;
	private JTextField txtAuthor;
	private JTextField txtISBN;
	private JTextField txtTitle;
	private JTextField txtCallNo;
	private ButtonGroup typeButtonGroup;
	
	/**
	 * Create the dialog.
	 */
	public AddNewBookDialog(LibrarianView parent, Controller session) {
		setTitle("Add New ");
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
			txtCallNo = new JTextField();
			//txtBidshouldWe.setText("BID (should we autoGenerate?)");
			contentPanel.add(txtCallNo, "2, 2, 5, 1, fill, default");
			txtCallNo.setColumns(10);
		}
		{
			JLabel lblCallNo = new JLabel("Call Number");
			contentPanel.add(lblCallNo, "8, 2");
		}
		{
			txtISBN = new JTextField();
			//txtName.setText("Name");
			contentPanel.add(txtISBN, "2, 4, 5, 1, fill, default");
			txtISBN.setColumns(10);
		}
		{
			JLabel lblISBN = new JLabel("ISBN");
			contentPanel.add(lblISBN, "8, 4");
		}
		{
			txtTitle = new JTextField();
			//txtPassword.setText("Password");
			contentPanel.add(txtTitle, "2, 6, 5, 1, fill, default");
			txtTitle.setColumns(10);
		}
		{
			JLabel lblTitle = new JLabel("Title");
			contentPanel.add(lblTitle, "8, 6");
		}
		{
			txtAuthor = new JTextField();
			//txtAddress.setText("Address");
			contentPanel.add(txtAuthor, "2, 8, 5, 1, fill, default");
			txtAuthor.setColumns(10);
		}
		{
			JLabel lblMainAuthor = new JLabel("Main Author");
			contentPanel.add(lblMainAuthor, "8, 8");
		}
		{
			txtPublisher = new JTextField();
			//txtPhoneNumber.setText("Phone Number");
			contentPanel.add(txtPublisher, "2, 10, 5, 1, fill, default");
			txtPublisher.setColumns(10);
		}
		{
			JLabel lblPublisher = new JLabel("Publisher");
			contentPanel.add(lblPublisher, "8, 10");
		}
		{
			txtYear = new JTextField();
			//txtTxtboxemailaddress.setText("txtBoxEmailAddress");
			contentPanel.add(txtYear, "2, 12, 5, 1, fill, default");
			txtYear.setColumns(10);
		}
		{
			JLabel lblYear = new JLabel("Year");
			contentPanel.add(lblYear, "8, 12");
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
						}catch(BookCreationException ue){
							JOptionPane.showMessageDialog(getInstance(), ue.getMessage());
						}
						catch(SQLException e1){
							JOptionPane.showMessageDialog(getInstance(), e1.getMessage());
						}
						catch(BadCopyNumberException bcn){
							try {
								Book temp2 = turnIntoBook();
								temp2.setCopyNo(bcn.getFirstFreeCopyNo());
								mySession.createNewBook(temp2);
							} catch (BookCreationException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}catch(SQLException e1){
								JOptionPane.showMessageDialog(getInstance(), e1.getMessage());
							}
							catch(BadCopyNumberException e1){
								JOptionPane.showMessageDialog(getInstance(), "Unable to add, copy number issue");
							}
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
	private Book turnIntoBook() throws BookCreationException{
		/*private JTextField txtYear;
		private JTextField txtPublisher;
		private JTextField txtAuthor;
		private JTextField txtISBN;
		private JTextField txtTitle;
		private JTextField txtCallNo;
		private ButtonGroup typeButtonGroup;*/
		Book temp = new Book(txtCallNo.getText(), txtISBN.getText(), txtTitle.getText(),
							txtAuthor.getText(), txtPublisher.getText(), txtYear.getText(),
							"0", BorrowStatus.CHECKED_IN);
							
		return temp;
	}
	private void closeDialogBox(){
		this.dispose();
	}
}
