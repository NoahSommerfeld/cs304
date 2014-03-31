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

import model.UserType;


public class RealUserNamePrompt extends UserNamePrompt implements ActionListener {
	
	private UserType neededType;
	
	
	public RealUserNamePrompt(Controller mySession, MainWindow parent, UserType neededType) {
		super(mySession, parent);
		super.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		//super(mySession, parent);
		
		this.neededType = neededType;
		ImageIcon img = new ImageIcon("res/library-icon.png");
		super.setIconImage(img.getImage());
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		System.out.println("Buton pressed");
		// try{
			if(session.login(usernameField.getText(), String.valueOf(passwordField.getPassword()), neededType)){
				System.out.println("login accepted");
				parent.setUNPW(usernameField.getText(), String.valueOf(passwordField.getPassword()));
				parent.loginGo(neededType);
				this.setVisible(false);
				mainFrame.setVisible(false);

				mainFrame.dispose();
				this.dispose();
				
			}
			else{
				JOptionPane.showMessageDialog(this, "Nope, not logged in");
				passwordField.setText("");
				return;
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
		
	}

