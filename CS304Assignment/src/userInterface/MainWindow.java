package userInterface;

import java.awt.Dimension;
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
import java.awt.event.ComponentListener;

import javax.swing.JPanel;

import model.UserType;

import java.awt.BorderLayout;
import java.sql.SQLException;


public class MainWindow extends JFrame implements ComponentListener{
	
	private Controller session;
	private JPanel loginPanel;
	private ContentPane contentPane;
	private static Controller staticSession;
	private int userBID;
	private String loggedInUserPassword;
	static final int WIDTH = 400;
    static final int HEIGHT = 400;
    static final int MIN_WIDTH = 800;
    static final int MIN_HEIGHT = 600;
    

    public void componentResized(ComponentEvent e) {
       int width = getWidth();
       int height = getHeight();
     //we check if either the width
     //or the height are below minimum
     boolean resize = false;
       if (width < MIN_WIDTH) {
            resize = true;
            width = MIN_WIDTH;
     }
       if (height < MIN_HEIGHT) {
            resize = true;
            height = MIN_HEIGHT;
       }
     if (resize) {
           setSize(width, height);
     }
    }
    public void componentMoved(ComponentEvent e) {
    }
    public void componentShown(ComponentEvent e) {
    }
    public void componentHidden(ComponentEvent e) {
    }
    /*public static void main(String args[]) {
            MinSizeFrame f = new MinSizeFrame();
            f.setVisible(true);
    }
}*/
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
		tempWindow.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));
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
		super("Digital Dewey Index");
		session = theSession;
		session.registerAsMainWindow(this);
		this.setVisible(false);
		this.setSize(WIDTH, HEIGHT);
        addComponentListener(this);
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
		ImageIcon img = new ImageIcon("res/library-icon.png");
		tempWindow.setIconImage(img.getImage());

		}
	
	public void clerkGo(){
		removeCurrentContent();
		System.out.println("new clerkview");
		contentPane = new ClerkView(this, session,this.userBID, this.loggedInUserPassword);
	

	getContentPane().add(contentPane, BorderLayout.CENTER);
	this.revalidate();
	this.repaint();
	}
	
	public void displayBorrowerPanel(){
		RealUserNamePrompt tempWindow = new RealUserNamePrompt(getSession(), this, UserType.borrower);
		ImageIcon img = new ImageIcon("res/library-icon.png");
		tempWindow.setIconImage(img.getImage());
	}
	public void borrowerGo(){
		removeCurrentContent();
	System.out.println("new borrwerview");
	contentPane = new BorrowerView(this, session, this.userBID, this.loggedInUserPassword);


getContentPane().add(contentPane, BorderLayout.CENTER);
this.revalidate();
this.repaint();
	}
	
	public void displayLibrarianPanel(){
		RealUserNamePrompt tempWindow = new RealUserNamePrompt(getSession(), this, UserType.librarian);
		ImageIcon img = new ImageIcon("res/library-icon.png");
		tempWindow.setIconImage(img.getImage());
		
	}
	
	public void librarianGo(){
		removeCurrentContent();
		System.out.println("new Librarian");
		contentPane = new LibrarianView(this, session, this.userBID, this.loggedInUserPassword);
	

	getContentPane().add(contentPane, BorderLayout.CENTER);
	this.revalidate();
	this.repaint();
	}
	
	
	public void signOut(){
		contentPane.signOut();
		this.userBID = -1;
		this.loggedInUserPassword = "";
		this.removeCurrentContent();
		
		this.loginPanel = new LoginPanel(this, session);
		getContentPane().add(loginPanel, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	public void setUNPW(int un, String pw) {
		this.userBID = un;
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

