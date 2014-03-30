/**
 * This class is the central controller. All UI commands will call methods here.  
 */

package userInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import exceptions.BadCallNumberException;
import exceptions.BadCopyNumberException;
import exceptions.BadUserIDException;
import exceptions.FineAssessedException;
import exceptions.NotCheckedInException;

public class Controller {
	private Connection con;
	private MainWindow mainWindow;
	
	/**
	 * constructor. Register the driver
	 * @throws SQLException - if the driver fails to connect (probably bad internet)
	 */
	public Controller() throws SQLException{
		
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
	}
		
	/**
	 * Log in to the Oracle driver/server database
	 * @param username - user's username
	 * @param password - user's password. Note: stored/sent in plain text...
	 * @throws SQLException - if the user fails to log in. 
	 */
	public void connect(String username, String password) throws SQLException{
		 String connectURL = "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug"; 

	       
	      
		con = DriverManager.getConnection(connectURL,username,password);
		Statement stmt = con.createStatement();
		ResultSet test = stmt.executeQuery("SELECT * FROM borrower");
		while(test.next()){
			System.out.println(test.getString("bid"));
		}
		System.out.println(test.getFetchSize());
		//System.out.println(test.next());
	      }
	
	
	/**
	 * This registers the main window, so that we can update the status bar. 
	 * Will only be one main window per program
	 * @param window - the MainWindow with the status bar. 
	 */
	public void registerAsMainWindow(MainWindow window){
		this.mainWindow = window;
	}
	
	/**
	 * updates the status bar at the bottom of the main window.
	 * Just a helper method for this class
	 * Was thinking of displaying the last complete transaction, for troubleshooting/demo purposes. 
	 * @param message - the message to be displayed on the status bar
	 */
	private void updateStatusBar(String message){
		mainWindow.getMyContentPane().setStatusLbl(message);
	}
	

	/**
	 * Calls System.exit(), but let's us close
	 * anything that needs to be closed in the model (in case we need to gracefully kill the driver)
	 * @param reasonCode - same as System.exit's parameter.
	 */
	public void exit(int reasonCode){
		System.out.println("Controller Exit called");
		System.exit(reasonCode);
	}


	/**
	 * This method is for the nice Stats bar at the top of the main window. 
	 * May add more of these to make it look more impressive. 
	 * @return the total number of users in the database
	 * @throws SQLException - if for some reason, the query fails. Static command though...
	 */
	public int getNumberOfUsers() throws SQLException{
		// TODO query and get total number of users
		//this.updateStatusBar("Number of users counted");
		return 25;
	}
	
	//Search for over due books. Called by ClerkListPanel.
	//Need to figure out exactly what the format of the return should be.
	//Can I return a list of Users??
	/**
	 * Search for overdue books, and return them as a String. 
	 * TODO: figure out what the return type should be. Probably want to display user, book, and due date. 
	 * Currently returning it all as one big string to load into the list
	 * @return List of users, books, and due dates as one String
	 * @throws SQLException - if for some reason the db query fails. 
	 */
	public ArrayList<String> searchForOverDues() throws SQLException{
		
		//TODO actually query the DB
		ArrayList<String> slackers = new ArrayList<String>();
		slackers.add("March 12 - Noah - textbook 32");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		slackers.add("March 25 - Daniel - Hamster's guide to the Galaxy");
		slackers.add("March 20 - Daniel - how to seduce mothers");
		
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
//	
//	
//	
//	
//	
//	
	public void createNewUser(User toAdd) throws SQLException {
		if(toAdd == null){
			throw new SQLException("Null User");
		}
		System.out.println(toAdd.getName() + " was added to the database");
		this.updateStatusBar("New user added to DB");
		// TODO Auto-generated method stub
		
	}
//
//	
//	
//	
//	
//	
//	
//	
	
	/**
	 * this method changes a book's status to 'checked in' or something...
	 * @param callNumber - the call number to check in
	 * @param currentTimeMillis - the current time. 
	 * @throws SQLException - if the db operation failed. 
	 * @throws FineAssessedException - if it was checked in, but a fine was assessed
	 */
	public void returnBook(String callNumber, long currentTimeMillis) throws SQLException, FineAssessedException{
		
		if(false){
			throw new SQLException(); //for testing. 
		}
		if(true){
			this.updateStatusBar("Book was returned. Fine issued");
			throw new FineAssessedException("Book was late, fine assessed", 1.02);
		}

		//this.updateStatusBar("Book checked back in");
		
	}

	/**
	 * This method just confirms that call number is valid. 
	 * @param callNumber
	 * @return
	 * @throws BadCallNumberException
	 * @throws NotCheckedInException
	 */
	public boolean confirmOkToCheckOut(String callNumber) throws BadCallNumberException, NotCheckedInException {
		if(false){
			throw new BadCallNumberException("nope"); //for testing. 
		}
		if(false){
			throw new NotCheckedInException("nope"); //for testing. 
		}
		
		//if it's a valid call number
		this.updateStatusBar("Call Number: " + callNumber + " Checked.");
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
	
	public void createNewBook(Book newBook) throws SQLException, BadCopyNumberException{
		if(Math.random()<0.5){ //for testing purposes. Keep me on my toes
			throw new SQLException("could not create book");
		}
		
		//throw new BadCopyNumberException("That one taken, use this", 5);
		
	}
	
	/**
	 * Returns the most popular books, and the number of times they've been
	 * checked out. May need to modify this later from just titles. 
	 * @return a list of books and the times they've been checked out, all as one concatenated String. 
	 */
	public List<String> getPopularBooks(){
		ArrayList<String> theResults = new ArrayList<String>();
		theResults.add("(5) - Hitchhiker's guide to your mom");
		theResults.add("(2) - war of your mom");
		theResults.add("(2) - around Daniel's mom in 80 days");
		return theResults;
		
	}
	
}
