package userInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	private Connection con;
	
	public Controller() throws SQLException{
		
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	}
	
	
	public void connect(String username, String password) throws SQLException{
		 String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

	       
	      
		con = DriverManager.getConnection(connectURL,username,password);

	      }
	
	
	/**
	 * Calls System.exit(), but let's us close
	 * anything that needs to be closed in the model 
	 * @param reasonCode - same as System.exit's parameter.
	 */
	public void exit(int reasonCode){
		System.exit(reasonCode);
	}


	public int getNumberOfUsers() throws SQLException{
		// TODO query and get total number of users
		
		return 25;
	}
	
	//Search for over due books. Called by ClerkListPanel.
	//Need to figure out exactly what the format of the return should be.
	//Can I return a list of Users?? 
	public ArrayList<String> searchForOverDues() throws SQLException{
		
		//TODO actually query the DB
		ArrayList<String> slackers = new ArrayList<String>();
		slackers.add("Noah - textbook 32");
		slackers.add("Daniel - Hamster's guide to the Galaxy");
		slackers.add("Daniel - how to seduce mothers");
		
		//throw new SQLException(); //for testing
		return slackers;
		
		
	}

	//TODO: implement this. Called by ClerkListPanel when the 'send late message button to these poeple' button is pressed
	public void sendLateMessage(String userKey) throws SQLException{
		// TODO Auto-generated method stub
		System.out.println("Sent message to: " + userKey);
		
	}
	
}
