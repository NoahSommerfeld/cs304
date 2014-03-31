package userInterface;

import javax.swing.JPanel;
import javax.swing.JLabel;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;

import exceptions.UserCreationException;

import javax.swing.JButton;

import model.UserType;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.SystemColor;

public class BorrowerView extends ContentPane {
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
	private JPanel panel_3;
	private User loggedInUser;
	
	public BorrowerView(MainWindow parent, Controller session, String username, String password) {
		
		super(parent, session, username, password);
		
			/*public User(String address, String password, String name, String phone, 
			String emailAddress, String sinOrStNo, String expiryDate, UserType type)*/
		loggedInUser = mySession.getUser(this.userName, this.password);
		
		mainPanel = new JPanel();
		mainPanel.setBackground(SystemColor.window);
		mainPanel.setMinimumSize(new Dimension(100,100));

		add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		panel_2 = new JPanel();
		mainPanel.add(panel_2, BorderLayout.NORTH);
		
		btnNewButton = new JButton("New button");
		panel_2.add(btnNewButton);
		
		btnNewButton_1 = new JButton("New button");
		panel_2.add(btnNewButton_1);
		
		btnNewButton_2 = new JButton("New button");
		panel_2.add(btnNewButton_2);
		
		listContentPanel = new BorrowerSearchPanel(this, this.mySession);
		mainPanel.add(listContentPanel, BorderLayout.WEST);
		
		panel_3 = new BorrowerAccountPanel(this, mySession);
		mainPanel.add(panel_3, BorderLayout.EAST);
		
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

	
	public User getLoggedInUser(){
		return this.loggedInUser;
	}
	@Override
	public void signOut() {
		//TODO put any saving methods here. Maybe DB.commit?
		
	}

	@Override
	protected UserType getNeededUserType() {
		return UserType.borrower;
	}

}
