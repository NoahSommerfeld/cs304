package userInterface;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
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

import javax.swing.JList;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.SwingConstants;

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
	private JTextField textAddSubjects;
	private JButton btnAddSubject;
	private JLabel lblSubjectLabel;
	private JTextField textAddAuthors;
	private JButton btnAddAuthors;
	private JLabel lblAuthors;
	private JList<String> listSubjects;
	private JList<String> listAuthors;
	private DefaultListModel<String> listModelSubjects;
	private DefaultListModel<String> listModelAuthors;
	private JScrollPane scrollPanelAuthors;
	private JScrollPane scrollPanelSubjects;
	private JRadioButton rdbtnNewBook;
	private JRadioButton rdbtnNewCopy;
	private JTextField txtCopyNo;
	private JLabel lblCopyNo;
	
	/**
	 * Create the dialog.
	 */
	public AddNewBookDialog(LibrarianView parent, Controller session) {
		setResizable(false);
		setTitle("Add New ");
		this.parent = parent;
		this.mySession = session;
		listModelSubjects = new DefaultListModel<String>();
		listModelAuthors = new DefaultListModel<String>();
		ImageIcon img = new ImageIcon("res/library-icon.png");
		this.setIconImage(img.getImage());
		setBounds(100, 100, 300, 450);
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("fill:default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		{
			rdbtnNewBook = new JRadioButton("New Book");
			rdbtnNewBook.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					disableForNewBook();
					
				}
				
			});
			rdbtnNewBook.setSelected(true);

			contentPanel.add(rdbtnNewBook, "2, 2");
		}
		{
			rdbtnNewCopy = new JRadioButton("New Copy");
			rdbtnNewCopy.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					disableForCopy();
					
				}
				
			});
			contentPanel.add(rdbtnNewCopy, "4, 2");
		}
		{
			ButtonGroup theButtonGroup = new ButtonGroup();
			theButtonGroup.add(rdbtnNewCopy);
			theButtonGroup.add(rdbtnNewBook);
			
		}
		{
			txtCopyNo = new JTextField();
			txtCopyNo.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(txtCopyNo, "6, 2, fill, default");
			txtCopyNo.setColumns(10);
		}
		{
			lblCopyNo = new JLabel("Copy #");
			contentPanel.add(lblCopyNo, "8, 2");
		}
		{
			txtCallNo = new JTextField();
			//txtBidshouldWe.setText("BID (should we autoGenerate?)");
			contentPanel.add(txtCallNo, "2, 4, 5, 1, fill, default");
			txtCallNo.setColumns(10);
		}
		{
			JLabel lblCallNo = new JLabel("Call Number");
			contentPanel.add(lblCallNo, "8, 4");
		}
		{
			txtISBN = new JTextField();
			//txtName.setText("Name");
			contentPanel.add(txtISBN, "2, 6, 5, 1, fill, default");
			txtISBN.setColumns(10);
		}
		{
			JLabel lblISBN = new JLabel("ISBN");
			contentPanel.add(lblISBN, "8, 6");
		}
		{
			txtTitle = new JTextField();
			//txtPassword.setText("Password");
			contentPanel.add(txtTitle, "2, 8, 5, 1, fill, default");
			txtTitle.setColumns(10);
		}
		{
			JLabel lblTitle = new JLabel("Title");
			contentPanel.add(lblTitle, "8, 8");
		}
		{
			txtAuthor = new JTextField();
			//txtAddress.setText("Address");
			contentPanel.add(txtAuthor, "2, 10, 5, 1, fill, default");
			txtAuthor.setColumns(10);
		}
		{
			JLabel lblMainAuthor = new JLabel("Main Author");
			contentPanel.add(lblMainAuthor, "8, 10");
		}
		{
			txtPublisher = new JTextField();
			//txtPhoneNumber.setText("Phone Number");
			contentPanel.add(txtPublisher, "2, 12, 5, 1, fill, default");
			txtPublisher.setColumns(10);
		}
		{
			JLabel lblPublisher = new JLabel("Publisher");
			contentPanel.add(lblPublisher, "8, 12");
		}
		{
			txtYear = new JTextField();
			//txtTxtboxemailaddress.setText("txtBoxEmailAddress");
			contentPanel.add(txtYear, "2, 14, 5, 1, fill, default");
			txtYear.setColumns(10);
		}
		{
			JLabel lblYear = new JLabel("Year");
			contentPanel.add(lblYear, "8, 14");
		}
		{
			listSubjects = new JList<String>(listModelSubjects);
			scrollPanelSubjects = new JScrollPane(listSubjects);
			listSubjects.setVisibleRowCount(2);
			//listSubjects.setBorder(new LineBorder(new Color(0, 0, 0)));
			contentPanel.add(scrollPanelSubjects, "2, 16, 5, 2, fill, fill");
		}
		{
			
			lblSubjectLabel = new JLabel("Subject(s)");
			contentPanel.add(lblSubjectLabel, "8, 16");
		}
		{
			textAddSubjects = new JTextField();
			contentPanel.add(textAddSubjects, "2, 18, 5, 1, fill, default");
			textAddSubjects.setColumns(10);
		}
		{
			btnAddSubject = new JButton("Add");
			btnAddSubject.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(isNoDuplicates(textAddSubjects.getText(), listModelSubjects)){
					listModelSubjects.addElement(textAddSubjects.getText());
					textAddSubjects.setText("");
					}
				}
				private boolean isNoDuplicates(String text,
						ListModel<String> listModel) {
					for(int i = 0; i<listModel.getSize(); i++){
						if(listModel.getElementAt(i).equals(text)){
							return false;
						}
					}

					return true;
				}
			});
			contentPanel.add(btnAddSubject, "8, 18");
		}
		{
			listAuthors = new JList<String>(listModelAuthors);
			//listAuthors.setBorder(new LineBorder(new Color(0, 0, 0)));
			scrollPanelAuthors = new JScrollPane(listAuthors);
			contentPanel.add(scrollPanelAuthors, "2, 20, 5, 2, fill, fill");
		}
		{
			lblAuthors = new JLabel("Authors");
			contentPanel.add(lblAuthors, "8, 20");
		}
		{
			textAddAuthors = new JTextField();
			contentPanel.add(textAddAuthors, "2, 22, 5, 1, fill, default");
			textAddAuthors.setColumns(10);
		}
		{
			btnAddAuthors = new JButton("Add");
			btnAddAuthors.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(isNoDuplicates(textAddAuthors.getText(), listModelAuthors)){
					listModelAuthors.addElement(textAddAuthors.getText());
					textAddAuthors.setText("");
					}
					
				
				}

				private boolean isNoDuplicates(String text,
						ListModel<String> listModel) {
					for(int i = 0; i<listModel.getSize(); i++){
						if(listModel.getElementAt(i).equals(text)){
							return false;
						}
					}

					return true;
				}
				
			});
			contentPanel.add(btnAddAuthors, "8, 22");
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
								int copyNo = mySession.createNewBook(temp2);
								JOptionPane.showMessageDialog(getInstance(), "Book added as copy: " + copyNo);
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
			this.disableForNewBook();
		}
		
	}
	
	
	/*
	 * 	private Controller mySession;
	private JTextField txtYear;
	private JTextField txtPublisher;
	private JTextField txtAuthor;
	private JTextField txtISBN;
	private JTextField txtTitle;
	private JTextField txtCallNo;
	private ButtonGroup typeButtonGroup;
	private JTextField textAddSubjects;
	private JButton btnAddSubject;
	private JLabel lblSubjectLabel;
	private JTextField textAddAuthors;
	private JButton btnAddAuthors;
	private JLabel lblAuthors;
	private JList<String> listSubjects;
	private JList<String> listAuthors;
	private DefaultListModel<String> listModelSubjects;
	private DefaultListModel<String> listModelAuthors;
	private JScrollPane scrollPanelAuthors;
	private JScrollPane scrollPanelSubjects;
	private JRadioButton rdbtnNewBook;
	private JRadioButton rdbtnNewCopy;
	private JTextField txtCopyNo;
	private JLabel lblCopyNo;
	
	 */
	protected void disableForCopy() {
		txtCopyNo.setEditable(true);
		txtCallNo.setEditable(true);
		txtTitle.setEditable(false);
		txtISBN.setEditable(false);
		txtAuthor.setEditable(false);
		txtPublisher.setEditable(false);
		txtYear.setEditable(false);
		textAddSubjects.setEditable(false);
		textAddAuthors.setEditable(false);
		listSubjects.setEnabled(false);
		listAuthors.setEnabled(false);
		
		
	}
	protected void disableForNewBook() {
		
		txtCopyNo.setEditable(false);
		txtCallNo.setEditable(true);
		txtTitle.setEditable(true);
		txtISBN.setEditable(true);
		txtAuthor.setEditable(true);
		txtPublisher.setEditable(true);
		txtYear.setEditable(true);
		textAddSubjects.setEditable(true);
		textAddAuthors.setEditable(true);
		listSubjects.setEnabled(true);
		listAuthors.setEnabled(true);
		
	}
	private AddNewBookDialog getInstance(){
		return this;
	}
	
	

	private Book turnIntoBook() throws BookCreationException{
		/*private JTextField txtYear;
		private JTextField txtPublisher;
		private JTextField txtAuthor;
		private JTextField txtISBN;
		private JTextField txtTitle;
		private JTextField txtCallNo;
		private ButtonGroup typeButtonGroup;*/
		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> subjects = new ArrayList<String>();
		//TODO convert from that list. 
		
		for(int i = 0; i< listModelSubjects.getSize(); i++){
			subjects.add(listModelSubjects.getElementAt(i));
		}
		for(int i = 0; i< listModelAuthors.getSize(); i++){
			subjects.add(listModelAuthors.getElementAt(i));
		}
		Book temp = new Book(txtCallNo.getText(), txtISBN.getText(), txtTitle.getText(),
							txtAuthor.getText(), txtPublisher.getText(), txtYear.getText(),
							"0", BorrowStatus.CHECKED_IN,authors, subjects);
							
		return temp;
	}
	private void closeDialogBox(){
		this.dispose();
	}
}
