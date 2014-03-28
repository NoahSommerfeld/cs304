package userInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	private Connection con;
	private MainWindow mainWindow;
	
	public Controller() throws SQLException{
		
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	}
	
	public void registerAsMainWindow(MainWindow window){
		this.mainWindow = window;
	}
	
	private void updateStatusBar(String message){
		mainWindow.getMyContentPane().setStatusLbl(message);
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
		//this.updateStatusBar("Number of users counted");
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
		
		this.updateStatusBar("Overdues searched for");
		
		//throw new SQLException(); //for testing
		return slackers;
		
		
	}

	//TODO: implement this. Called by ClerkListPanel when the 'send late message button to these poeple' button is pressed
	public void sendLateMessage(String userKey) throws SQLException{
		// TODO Auto-generated method stub
		System.out.println("Sent message to: " + userKey);
		this.updateStatusBar("Late Message Sent");
		
	}

	//TODO: implement this. Called by AddNewUserDialog. need to send request to the server
	public void createNewUser(Borrower toAdd) throws SQLException {
		if(toAdd == null){
			throw new SQLException("Null Borrower");
		}
		System.out.println(toAdd.getName() + " was added to the database");
		this.updateStatusBar("New user added to DB");
		// TODO Auto-generated method stub
		
	}
	
}
