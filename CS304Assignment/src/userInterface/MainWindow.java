package userInterface;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.sql.SQLException;


public class MainWindow extends JFrame {
	
	private Controller session;
	private JPanel loginPanel;
	private ContentPane contentPane;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserNamePrompt frame = new UserNamePrompt();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
		Controller tempSession=null;

		try{
			tempSession = new Controller();
		}
		catch(SQLException e){
			///JOptionPane.showMessageDialog(e.getMessage()); //TODO: figure how to implelemt
			System.out.println("Driver failed to initialize");
			System.exit(1);
		}
		MainWindow tempWindow = new MainWindow(tempSession);
		new UserNamePrompt(tempSession, tempWindow); //was getting 2 windows with the event queue. weird. 
	}



	/**
	 * Launch the application.
	 *//*// don't really need a MAIN here. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
*/	public ContentPane getMyContentPane(){
	return contentPane;
}
	/**
	 * Create the application.
	 */
	public MainWindow(Controller theSession) {
		super("NOAH's AWESOME LIBRARY PROGRAM");
		session = theSession;
		session.registerAsMainWindow(this);
		this.setVisible(false);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setBounds(400, 400, 550, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmSignOut = new JMenuItem("Sign Out");
		mnFile.add(mntmSignOut);
		mntmSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signOut();
			}
		});
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				session.exit(0);
			}
		});
		mnFile.add(mntmExit);
		

		
		loginPanel = new LoginPanel(this, session);
		getContentPane().add(loginPanel, BorderLayout.CENTER);
	}

	
	public void removeCurrentContent(){
		if(loginPanel != null || loginPanel.getParent() != null){
			System.out.println("login panel removed");
			getContentPane().remove(loginPanel);
			loginPanel.setVisible(false);
		}
		if(contentPane != null){
			contentPane.signOut();
			getContentPane().remove(contentPane);
		}
	}
	public void displayClerkPanel(){
			removeCurrentContent();
			System.out.println("new clerkview");
			contentPane = new ClerkView(this, session);
		

		getContentPane().add(contentPane, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
		}
	
	public void displayBorrowerPanel(){
		removeCurrentContent();
		System.out.println("new borrwerview");
		contentPane = new BorrowerView(this, session);
	

	getContentPane().add(contentPane, BorderLayout.CENTER);
	this.revalidate();
	this.repaint();
	}
	
	public void displayLibrarianPanel(){
		removeCurrentContent();
		System.out.println("new Librarian");
		contentPane = new LibrarianView(this, session);
	

	getContentPane().add(contentPane, BorderLayout.CENTER);
	this.revalidate();
	this.repaint();
	}
	
	
	public void signOut(){
		contentPane.signOut();
		this.removeCurrentContent();
		
		this.loginPanel = new LoginPanel(this, session);
		getContentPane().add(loginPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	}

