package userInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import exceptions.BadCallNumberException;
import exceptions.BadUserIDException;
import exceptions.FineAssessedException;
import exceptions.NotCheckedInException;

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
		System.out.println("Controller Exit called");
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
	public void createNewUser(User toAdd) throws SQLException {
		if(toAdd == null){
			throw new SQLException("Null User");
		}
		System.out.println(toAdd.getName() + " was added to the database");
		this.updateStatusBar("New user added to DB");
		// TODO Auto-generated method stub
		
	}

	public void returnBook(String text, long currentTimeMillis) throws SQLException, FineAssessedException{
		
		if(false){
			throw new SQLException(); //for testing. 
		}
		if(false){
			this.updateStatusBar("Book was returned. Fine issued");
			throw new FineAssessedException("Book was late, fine assessed");
		}

		this.updateStatusBar("Book checked back in");
		
	}

	public boolean confirmOkToCheckOut(String text) throws BadCallNumberException, NotCheckedInException {
		if(false){
			throw new BadCallNumberException("nope"); //for testing. 
		}
		if(false){
			throw new NotCheckedInException("nope"); //for testing. 
		}
		
		//if it's a valid call number
		this.updateStatusBar("Call Number: " + text + " Checked.");
		return true; 
		
	}

	//Checks out a book, when given a call number
	public void checkOut(String callNumber, String userID) throws SQLException, NotCheckedInException, BadCallNumberException, BadUserIDException {
		// TODO Auto-generated method stub
		
		//SQLException from the db
		//notcheckedinexception if it's not 'borrowable' //TODO differentiate between 'out' and 'on hold'
		//badCallNumberException if it isn't in the db. (vs. db connection errors)
		//BadUserIDException if the user is not in the DB
		this.updateStatusBar("Item(s) checked out");
		
	}
	
}
