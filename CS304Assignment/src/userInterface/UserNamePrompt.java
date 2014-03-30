package userInterface;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class UserNamePrompt extends JFrame implements ActionListener {
	

	private JFrame mainFrame;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField usernameField;
	private Controller session;
	private MainWindow mainWindow;


	/**
	 * Create the frame.
	 */
	public UserNamePrompt(Controller mySession, MainWindow parent) {
		session = mySession;
		mainWindow = parent;

		initializeUI();
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
	ImageIcon img = new ImageIcon("res/library-icon.png");
	mainFrame.setIconImage(img.getImage());
    JLabel usernameLabel = new JLabel("Enter username: ");
    JLabel passwordLabel = new JLabel("Enter password: ");

    usernameField = new JTextField(10);
    passwordField = new JPasswordField(10);
    passwordField.setEchoChar('*');
    
    JButton loginButton = new JButton("Log In");

    JPanel contentPane = new JPanel();
    mainFrame.setContentPane(contentPane);


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
	  System.exit(0); 
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
		mainWindow.setVisible(true);
		mainFrame.dispose();
		//TODO launch the next window here. 
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		
		/* try{
>>>>>>> branch 'master' of https://github.com/NoahSommerfeld/cs304.git
			 session.connect(usernameField.getText(), String.valueOf(passwordField.getPassword())); 
			  mainFrame.dispose();
			  switchToMain();  
			  return;
		 
	 }
		catch(SQLException error){
<<<<<<< HEAD
		JOptionPane.showMessageDialog(this, error.getMessage());
		}
		//switchToMain();
=======
			JOptionPane.showMessageDialog(this, error.getMessage());
		}*/
		switchToMain();
//>>>>>>> branch 'master' of https://github.com/NoahSommerfeld/cs304.git
		  // if the username and password are valid, 
		 // remove the login window and display a text menu 
   
		      passwordField.setText("");
		//  }
		}             
		
	}

