package userInterface;

import javax.swing.JDialog;
import java.awt.Dialog;
import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import javax.swing.JButton;

import model.UserType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClerkView extends ContentPane {
	private JPanel mainPanel;
	private JPanel panel;
	private JButton btnNewButton_3;
	private JPanel panel_1;
	private JButton btnNewButton_4;
	private JPanel panel_2;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JButton btnNewButton_2;
	private JPanel listContentPanel;
	private JDialog addNewuserDialog;
	private JDialog processReturnDialog;
	private JDialog checkOutItems;
	private ClerkView getThisSession(){
		return this;
	}
	public ClerkView(MainWindow parent, Controller session, int userBID, String password) {
		
		super(parent, session, userBID, password);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(SystemColor.window);
		mainPanel.setMinimumSize(new Dimension(100,100));
		mainPanel.setMaximumSize(new Dimension(100,100));
		mainPanel.setSize(new Dimension(100,100));
		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		mainPanel.add(panel_2, BorderLayout.NORTH);
		
		btnNewButton = new JButton("Check Out Items");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(checkOutItems != null && checkOutItems.isVisible()){
						return; //don't open a second window
					}
					if(checkOutItems != null){
						checkOutItems.dispose();
					}
					checkOutItems = new CheckOutItemsDialog(getThisSession(), getMySession());
					checkOutItems.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
					
					checkOutItems.setVisible(true);
					}
		});
		panel_2.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Add New User");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			if(addNewuserDialog != null && addNewuserDialog.isVisible()){
				return; //don't open a second window
			}
			if(addNewuserDialog != null){
				addNewuserDialog.dispose();
			}
				addNewuserDialog = new AddNewUserDialog(getThisSession(), getMySession());
				
				addNewuserDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				addNewuserDialog.setVisible(true);
			}
		});
		panel_2.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("Process Return");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(processReturnDialog != null && processReturnDialog.isVisible()){
					return; //don't open a second window
				}
				if(processReturnDialog != null){
					processReturnDialog.dispose();
				}
				processReturnDialog = new ProcessReturnDialog(getThisSession(), getMySession());
				processReturnDialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
				
				processReturnDialog.setVisible(true);
				}
		});
		panel_2.add(btnNewButton_2);
		
		listContentPanel = new ClerkListPanel(this, this.mySession);
		mainPanel.add(listContentPanel, BorderLayout.CENTER);
		
		panel = new JPanel();
		add(panel, BorderLayout.WEST);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("1px:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("1px:grow"),}));
		

		
		panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("1px:grow"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("1px:grow"),}));
		

	/*	
		JLabel lblClerkYo = new JLabel("Clerk, yo");
		add(lblClerkYo, "2, 2, left, top");
		
		JButton btnHaahaha = new JButton("HAAHAHA");
		add(btnHaahaha, "2, 14");
		*/
		
		
	}
	
	/**
	 * @wbp.parser.constructor
	 *//*
	public ClerkView(MainWindow parent){
		super(parent,null);

	}*/

	
	
	@Override
	public void signOut() {
		//TODO put any saving methods here. Maybe DB.commit?
		
	}
	@Override
	protected UserType getNeededUserType() {
		return UserType.clerk;
	}
	


}
