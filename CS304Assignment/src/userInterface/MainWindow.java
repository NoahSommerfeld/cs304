package userInterface;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import model.UserType;

import java.awt.BorderLayout;
import java.sql.SQLException;


public class MainWindow extends JFrame {
	
	private Controller session;
	private JPanel loginPanel;
	private ContentPane contentPane;
	private static Controller staticSession;
	private String loggedInUserName;
	private String loggedInUserPassword;
	
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
		

		try{
			staticSession = new Controller();
		}
		catch(SQLException e){
			///JOptionPane.showMessageDialog(e.getMessage()); //TODO: figure how to implelemt
			System.out.println("Driver failed to initialize");
			System.exit(1);
		}
		MainWindow tempWindow = new MainWindow(staticSession);
		tempWindow.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                System.out.println("Replace sysout with your method call");
                getSession().exit(0); //this is so we can close the database server calls gracefully in the event of a close.
                ((JFrame)(e.getComponent())).dispose();
            }
        });
		new UserNamePrompt(staticSession, tempWindow); //was getting 2 windows with the event queue. weird. 
	}
	public static Controller getSession(){
		return staticSession;
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

		setBounds(400, 400, 800, 600);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //the MAIN method will exit
		
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
		

		ImageIcon img = new ImageIcon("res/library-icon.png");
		this.setIconImage(img.getImage());
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
		RealUserNamePrompt tempWindow = new RealUserNamePrompt(getSession(), this, UserType.clerk);

		}
	
	public void clerkGo(){
		removeCurrentContent();
		System.out.println("new clerkview");
		contentPane = new ClerkView(this, session,this.loggedInUserName, this.loggedInUserPassword);
	

	getContentPane().add(contentPane, BorderLayout.CENTER);
	this.revalidate();
	this.repaint();
	}
	
	public void displayBorrowerPanel(){
		RealUserNamePrompt tempWindow = new RealUserNamePrompt(getSession(), this, UserType.borrower);
	
	}
	public void borrowerGo(){
		removeCurrentContent();
	System.out.println("new borrwerview");
	contentPane = new BorrowerView(this, session, this.loggedInUserName, this.loggedInUserPassword);


getContentPane().add(contentPane, BorderLayout.CENTER);
this.revalidate();
this.repaint();
	}
	
	public void displayLibrarianPanel(){
		RealUserNamePrompt tempWindow = new RealUserNamePrompt(getSession(), this, UserType.librarian);
	
		
	}
	
	public void librarianGo(){
		removeCurrentContent();
		System.out.println("new Librarian");
		contentPane = new LibrarianView(this, session, this.loggedInUserName, this.loggedInUserPassword);
	

	getContentPane().add(contentPane, BorderLayout.CENTER);
	this.revalidate();
	this.repaint();
	}
	
	
	public void signOut(){
		contentPane.signOut();
		this.loggedInUserName = "";
		this.loggedInUserPassword = "";
		this.removeCurrentContent();
		
		this.loginPanel = new LoginPanel(this, session);
		getContentPane().add(loginPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	public void setUNPW(String un, String pw) {
		this.loggedInUserName = un;
		this.loggedInUserPassword = pw;
		
	}
	public void loginGo(UserType neededType) {
		if(neededType == UserType.librarian){
			librarianGo();
		}
		else if(neededType == UserType.borrower){
			borrowerGo();
		}
		else if(neededType == UserType.clerk){
			clerkGo();
		}
		
	}
	}

