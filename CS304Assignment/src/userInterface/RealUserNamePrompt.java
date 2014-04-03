package userInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import exceptions.UserLoginException;
import model.UserType;


public class RealUserNamePrompt extends JFrame implements ActionListener {
	
	private UserType neededType;
	protected JFrame mainFrame;
	private JPanel contentPane;
	protected JPasswordField passwordField;
	protected JTextField usernameField;
	protected Controller session;
	protected MainWindow parent;

	
	public RealUserNamePrompt(Controller mySession, MainWindow parent, UserType neededType) {

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	
		
		initializeUI();
		//super(mySession, parent);
		this.passwordField.setText("");
		this.usernameField.setText("");
		this.neededType = neededType;
		this.parent = parent;
		this.session = mySession;
		ImageIcon img = new ImageIcon("res/library-icon.png");
		setIconImage(img.getImage());

	}

	@Override
	public void actionPerformed(ActionEvent e) {
	//	parent.loginGo(neededType); //used to bypass the login system
	//	System.out.println("Buton pressed");
		// try{
			try {
				if(mainFrame == null){
					System.out.println("NOOOPE");
				}
				if(session.login(Integer.parseInt(usernameField.getText()), String.valueOf(passwordField.getPassword()), neededType)){
					System.out.println("login accepted");
					parent.setUNPW(Integer.parseInt(usernameField.getText()), String.valueOf(passwordField.getPassword()));
					parent.loginGo(neededType);
					this.setVisible(false);
					setVisible(false);
					mainFrame.dispose();
					dispose();
	
					
				}
				else{
					JOptionPane.showMessageDialog(this, "Nope, not logged in");
					passwordField.setText("");
					return;
				}
			} catch (HeadlessException | SQLException | UserLoginException | IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(this, e1.getMessage());
				e1.printStackTrace();
			}
			
			 
			 // return;
		 
	// }
	//	catch(SQLException error){
		//JOptionPane.showMessageDialog(this, error.getMessage());
		//}
		//switchToMain();
	//		JOptionPane.showMessageDialog(this, error.getMessage());
	//	}
//		switchToMain();
		  // if the username and password are valid, 
		 // remove the login window and display a text menu 
  

		//  }
		}        
		
	




	private void initializeUI(){
		/*		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	*/
	//FOLLOWING CODE TAKEN AND MODIFIED FROM BRANCH.JAVA
	mainFrame = new JFrame("User Login");

    JLabel usernameLabel = new JLabel("Enter BID");
    JLabel passwordLabel = new JLabel("Enter password: ");

    usernameField = new JTextField(10);
    passwordField = new JPasswordField(10);
    passwordField.setEchoChar('*');

    usernameField.setText("ora_q4k8");
    passwordField.setText("a19338128");
    
    JButton loginButton = new JButton("Log In");

    JPanel contentPane = new JPanel();
    mainFrame.setContentPane(contentPane);
    ImageIcon img = new ImageIcon("res/library-icon.png");
	mainFrame.setIconImage(img.getImage());

    // layout components using the GridBag layout manager

    GridBagLayout gb = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();

    contentPane.setLayout(gb);
    contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    // place the username label 
    c.gridwidth = GridBagConstraints.RELATIVE;
    c.insets = new Insets(10, 10, 5, 0);
    gb.setConstraints(usernameLabel, c);
    contentPane.add(usernameLabel);

    // place the text field for the username 
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.insets = new Insets(10, 0, 5, 10);
    gb.setConstraints(usernameField, c);
    contentPane.add(usernameField);

    // place password label
    c.gridwidth = GridBagConstraints.RELATIVE;
    c.insets = new Insets(0, 10, 10, 0);
    gb.setConstraints(passwordLabel, c);
    contentPane.add(passwordLabel);

    // place the password field 
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.insets = new Insets(0, 0, 10, 10);
    gb.setConstraints(passwordField, c);
    contentPane.add(passwordField);

    // place the login button
    c.gridwidth = GridBagConstraints.REMAINDER;
    c.insets = new Insets(5, 10, 10, 10);
    c.anchor = GridBagConstraints.CENTER;
    gb.setConstraints(loginButton, c);
    contentPane.add(loginButton);

    // register password field and OK button with action event handler
    passwordField.addActionListener(this);
    loginButton.addActionListener(this);

    // anonymous inner class for closing the window
    mainFrame.addWindowListener(new WindowAdapter() 
    {
	public void windowClosing(WindowEvent e) 
	{ 
	  getInstance().dispose(); 
	}
    });

    // size the window to obtain a best fit for the components
    mainFrame.pack();

    // center the frame
    Dimension d = mainFrame.getToolkit().getScreenSize();
    Rectangle r = mainFrame.getBounds();
    mainFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );

    // make the window visible
    mainFrame.setVisible(true);

    // place the cursor in the text field for the username
    usernameField.requestFocus();
/*
    try 
    {
	// Load the Oracle JDBC driver
	DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
    }
    catch (SQLException ex)
    {
	System.out.println("Message: " + ex.getMessage());
	System.exit(-1);
    }*/
    
    //END OF BORROWED CODE

	}

	private void switchToMain(){
		parent.setVisible(true);
		mainFrame.dispose();
		//TODO launch the next window here. 
	}
	      
	private RealUserNamePrompt getInstance(){
		return this;
	}
		
	}


