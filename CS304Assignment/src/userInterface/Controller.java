/**
 * This class is the central controller. All UI commands will call methods here.  
 */

package userInterface;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import model.Book;
import model.SQLType;
import model.SearchAbleKeywords;
import model.User;
import model.UserType;
import exceptions.BadCallNumberException;
import exceptions.BadCopyNumberException;
import exceptions.BadUserIDException;
import exceptions.FineAssessedException;
import exceptions.NotCheckedInException;
import exceptions.UserCreationException;
import exceptions.UserLoginException;

public class Controller {
	private Connection con;
	private Statement stmt;
	
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
	      }
	

	
	//---------------------------------User Basics-------------------------------------//
	
	
	/**
	 * This method is for creating new users
	 * @return A message of confirmation and containing the new bid of the user.
	 * @throws SQLException - if for some reason, the query fails. Static command though...
	 * 			
	 */
	
	// To do:
	//			- confirm user is not already in the DB. (check sinorst)
	//			- update message to return bid
	
	
	public void createNewUser(User newUser) throws SQLException, ParseException{
		
		stmt = con.createStatement();
		
		try{

			String query = "INSERT INTO borrower VALUES (bid_counter.nextVal, '"
					+ newUser.getPassword() + "', '"
					+ newUser.getName() + "', '" 
					+ newUser.getAddress() + "', "
					+ newUser.getPhone() + ", '" 
					+ newUser.getEmailAddress() + "', "
					+ newUser.getSinOrStNo() + ", '"
					+ formatDate(newUser.getExpiryDate()) + "', '"
					+ newUser.getType() +"')";
			
			System.out.println(query);
			
			stmt.executeUpdate(query);

			updateMessage("Adding User", true);
			
			
			
		}
		/*catch (IOException e)
		{
		    System.out.println("IOException!");
		}*/

		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    try 
		    {
			// undo the insert
			con.rollback();	
		    }
		    catch (SQLException ex2)
		    {
			System.out.println("Message: " + ex2.getMessage());
			throw ex2;
		    }
		    throw ex;

		    
//		if(newUser == null){
//			throw new SQLException("Null User");
//		}
//		System.out.println(newUser.getName() + " was added to the database");
//		this.updateStatusBar("New user added to DB");
//		// TODO Auto-generated method stub
//		

		}
	
	}


	
	
	public boolean login (int bid, String Password, UserType sectionType) throws SQLException, UserLoginException {
		String query;
		ResultSet rs;
		boolean legit = false;
		try{
			Statement stmt = con.createStatement();
			
			query = "select count(*) from borrower where bid = '"+ bid +"'";
			rs = sql(query, SQLType.query );

			rs.next();
			int count = rs.getInt(1);

			//Check if user is in the system
			if(count < 1){
				throw new UserLoginException("There is no record of this user in our library. Please be sure to type your Library Number correctly.");
			}else if(count >1){
				throw new UserLoginException("Duplicate users in the system. Please consult a staff member before loggin in.");
			}else{
				
				query = "select * from borrower where bid = '" + bid + "'";
				
				rs = stmt.executeQuery(query);
				rs = sql(query, SQLType.query );

				rs.next();
				
				//Check if the user has the correct login information
				String currUserPass = rs.getString("password");
				if (!currUserPass.equals(Password)){
					
					throw new UserLoginException("Incorrect password. Please try again.");
				}else{
					
					//Check If user has access to specific section
					String currUserType = rs.getString("type");
					if(UserType.librarian == UserType.valueOf(currUserType)){
						legit = true;
					}else if(UserType.valueOf(currUserType) == UserType.clerk && sectionType != UserType.librarian){
						legit = true;
					}else if(UserType.valueOf(currUserType) == UserType.borrower && sectionType == UserType.borrower){
						legit = true;
					}else{
						throw new UserLoginException("You are trying to access a section you do not have permissions for. Please select another section.");
					}
				}
			}	
		}catch(UserLoginException e){
			throw e;
		}
		
			
		return legit;
	}

	
	//TODO: Maybe implement user checks
	
	public User getUser(int bid) throws SQLException, UserCreationException {
	
		User user;
		
		String statement = "Select * from Borrower where bid = '"+ bid +"' and ROWNUM = 1";
		
		ResultSet rs = sql(statement, SQLType.query);
		rs.next();
		
		String address = rs.getString("address");
		String password = rs.getString("password");
		String name = rs.getString("name");
		long phone = rs.getLong("phone");
		String emailAddress = rs.getString("emailAddress");
		long sinorstno = rs.getLong("sinorstno");
		Date date = rs.getDate("expirydate");
		UserType type = UserType.valueOf(rs.getString("type"));
		
		user = new User(address,password,name,phone,
				emailAddress,sinorstno,date,type);
					
		return user;
			
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
		

		stmt = con.createStatement();
		
		String query = "SELECT count(*) FROM Borrower";
		
		try{
			ResultSet rs = stmt.executeQuery(query);
			rs.next();
			int numUsers = rs.getInt(1);
			return numUsers;
			
		}catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    try 
		    {
			// undo the insert
			con.rollback();	
		    }
		    catch (SQLException ex2)
		    {
			System.out.println("Message: " + ex2.getMessage());
			throw ex2;
		    }
		    throw ex;
		}
		
	}
	
	
//---------------------------------------BOOKS--------------------------------------//	
	
	/**
	 * DANIEL: THIS IS IMPORTANT
	 * the books will be passed with the default copy no (0)
	 * If it already exists, please throw a new BadCopyNumberException 
	 * with the first free copyno included. 
	 * Might want to search for # of copies first, rather than trying to sort
	 * through the SQLExceptions coming back to see if that was the reason 
	 * @param newBook - the book to be added to the DB
	 * @throws SQLException - if Oracle complains
	 * @throws BadCopyNumberException - if the copy number is already taken. 
	 */
	
public void createNewBook(Book newBook) throws SQLException, BadCopyNumberException{
		try{
			String statement;
			ResultSet rs;
			//book
			
			statement = "INSERT INTO Book VALUES (CN_counter.nextVal, '"
					+ newBook.getISBN() + "', '"
					+ newBook.getTitle() + "', '" 
					+ newBook.getMainAuthor() + "', "
					+ newBook.getPublisher() + ", '" 
					+ newBook.getYear() + "')";
			
			System.out.println(statement);
			
			sql(statement, SQLType.insert);
			
			
			
			//Copy Number
			
				//Check if there is a 
				statement = "select count(*) from BookCopy where callnumber ='" 
							+ newBook.getCallNumber() + "' copyNo='" + newBook.getCopyNo()+"')";
				rs = sql(statement, SQLType.query);
				rs.next();
				
				int copyCount = rs.getInt(1);
				if( copyCount >= 1){
					statement = "select max(copyNo) from bookCopy where callNumber='" + newBook.getCallNumber() + "')";
					rs = sql(statement, SQLType.query);
					rs.next();
					copyCount = rs.getInt(1);
					
					
					String message = "The copy number" + newBook.getCopyNo() + "does not exist. The next available copy number is: ";
					throw new BadCopyNumberException(message,copyCount++);
				}else{
					statement = "INSERT INTO BookCopy VALUES (CN_counter.curVal, '" 
								+ newBook.getCopyNo() + "')";
					
					sql(statement, SQLType.insert);
				}
				

				//Author

				statement = "INSERT INTO HasAuthor VALUES (CN_counter.curVal, '"
						+ newBook.getMainAuthor() + "')";
				
				sql(statement, SQLType.insert);
				
				ArrayList secAuthors = newBook.getAuthors();
					
			/*	for(String s : newBook.getAuthors()){
					statement = "INSERT INTO HasAuthor VALUES (CN_counter.curVal, '"
				}
				while(){

					System.out.println(statement);
					
				}
				
				//Subject
				
				while(){
	//				query = "INSERT INTO HasSubject VALUES (CN_counter.curVal, '"
			//				+ newBook.) + "')";

					System.out.println(statement);
				}*/
				
				
				updateMessage("Adding User", true);
				
				
				
				
			}catch(BadCopyNumberException BDCPY){
				throw BDCPY;
			}
			


	}
	

/** DANIEL! 
 * Please format like below (# in, # out, #hold) - TITLE. Next Due date if there's outs
 * 
 * @param selectedItem - Title, author, subject. 
 * @param what the user is searching for 
 * @return
 * @throws SQLException - if you screwed up the query. Jerk. 
 */
public ArrayList<String> searchBooks(SearchAbleKeywords selectedItem, String searchArgument) throws SQLException{
	//I would reccomend doing several helper methods, one for each SearchAbleKeyword. 
	ArrayList<String> results = new ArrayList<String>();
	results.add("(1 in, 2 out, 1 hold) - Hitchhiker's guide to the galaxy. Next due back on January 14, 2015");
	results.add("(3 in, 0 out, 0 hold) - Daniel's guide to the galaxy. All in");
	results.add("(0 in, 2 out, 1 hold) - Shit Shit, fucking kittens. Next due back on April 20, 2015");
	
	return results;
	
}






	/**
	 * Returns the most popular books, and the number of times they've been
	 * checked out. May need to modify this later from just titles. 
	 * @return a list of books and the times they've been checked out, all as one concatenated String. 
	 */
	public List<String> getPopularBooks(int year, int numResults) throws SQLException{
		ArrayList<String> theResults = new ArrayList<String>();
		theResults.add("(5) - Hitchhiker's guide to your mom");
		theResults.add("(2) - war of your mom");
		theResults.add("(2) - around Daniel's mom in 80 days");
		return theResults;
		
	}
	
	public ArrayList<String> getSubjects() throws SQLException{
		ArrayList<String> temp = new ArrayList<String>();
		temp.add("Science");
		temp.add("Fantasy");
		temp.add("non-fiction");
		return temp;
	}
	


	
	
//----------------------------------ProcessingBooks--------------------------------------//
	

	
	//Checks out a book, when given a call number
	public void checkOut(String callNumber, int userID) throws SQLException, NotCheckedInException, BadCallNumberException, BadUserIDException {
		// TODO Auto-generated method stub
		
		//SQLException from the db
		//notcheckedinexception if it's not 'borrowable' //TODO differentiate between 'out' and 'on hold'
		//badCallNumberException if it isn't in the db. (vs. db connection errors)
		//BadUserIDException if the user is not in the DB
		this.updateStatusBar("Item(s) checked out");
		
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

	
	/**
	 * this method changes a book's status to 'checked in' or something...
	 * @param callNumber - the call number to check in
	 * @param currentTimeMillis - the current time. 
	 * @throws SQLException - if the db operation failed. 
	 * @throws FineAssessedException - if it was checked in, but a fine was assessed
	 */
	public void returnBook(String callNumber, long currentTimeMillis, int copyNo) throws SQLException, FineAssessedException{
		
		if(false){
			throw new SQLException(); //for testing. 
		}
		if(true){
			this.updateStatusBar("Book was returned. Fine issued");
			throw new FineAssessedException("Book was late, fine assessed", 1.02);
		}

		//this.updateStatusBar("Book checked back in");
		
	}


public void processPayment(int bid, double paymentAmount, int creditCardNo){
	
	
	
	
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

	
	




//--------------------------------GetRelationships--------------------------------//




	/**
	 * this is a fun one. Every row in the table is represented by an array.
	 * I need a String[] formatted like this:
	 * String[CallNumber, Title, CheckOutDate, DueDate, ""] //need an extra slot
	 * and then an array of those. I'll give you a demo
	 * 
	 * I may need it filtered by subject (null is no filter) :D
	 * @param selectedItem
	 * @return
	 */
	public String[][] getCheckOuts(String filterSubject) {
		String[][] data = {
			    {"A2NRBS2", "Long Island Ice Tea",
			     "January 15, 2014", "April 30, 2014", ""},
			    {"34534q5w", "Noah>Daniel, inc", 
			     "March 25, 2014", "April 25, 2014", ""},
			    {"A6 FGD 234", "That time I saw a ghost",
			     "March 10, 2014", "March 17, 2014", ""},
			    {"43A dfgdfg", "Smokin the While Elephant",
			     "March 12, 2014", "March 16, 2014", ""}
			};

		
		
		return data;
	}

	/**
	 * Need each row formatted like this :)
	 * String["Call Number", "Title", "Hold Requested Date", "Status", "Book Due"]
	 * @param loggedInUser
	 * @return
	 */
	public String[][] getHoldRequests(User loggedInUser) {
		String[][] data = {
			    {"A2NRBS2", "Long Island Ice Tea",
			     "January 15, 2014", "Checked Out", "April 30, 2014"},
			    {"34534q5w", "Noah>Daniel, inc", 
			     "March 25, 2014", "On Hold", "April 25, 2014"},
			    {"A6 FGD 234", "That time I saw a ghost",
			     "March 10, 2014", "Checked Out", "March 17, 2014"},
			    {"43A dfgdfg", "Smokin the While Elephant",
			     "March 12, 2014", "On Hold", "March 16, 2014"}
			};

		
		return data;
	}

	
	/**
	 * String ["Call Number", "Title", "Checked Out", "Due", ""]
	 * @param loggedInUser
	 * @return
	 */
	public String[][] getLoanedBooks(User loggedInUser) {
		String[][] data = {
			    {"A2NRBS2", "Long Island Ice Tea",
			     "January 15, 2014", "April 30, 2014", ""},
			    {"34534q5w", "Noah>Daniel, inc", 
			     "March 25, 2014", "April 25, 2014", ""},
			    {"A6 FGD 234", "That time I saw a ghost",
			     "March 10, 2014", "March 17, 2014", ""},
			    {"43A dfgdfg", "Smokin the While Elephant",
			     "March 12, 2014", "March 16, 2014", ""}
			};

		return data;
	}
	
	/**
	 * String ["Fine ID", "Title", "Book Due", "Returned", "Fine Amount"]
	 * @param loggedInUser
	 * @return
	 */

	public String[][] getOutstandingFines(User loggedInUser) {
			String[][] date = { 
				{"22", "Long Island Ice Tea",
		     "January 15, 2014", "April 30, 2014", "$25.03"},
		    {"34534", "Noah>Daniel, inc", 
		     "March 25, 2014", "April 25, 2014", "$26.02"},
		    {"6234", "That time I saw a ghost",
		     "March 10, 2014", "March 17, 2014", "$1.03"},
		    {"43", "Smokin the While Elephant",
		     "March 12, 2014", "March 16, 2014", "$5.01"}
	};
		return date;
	}


	
//------------------------------------Other-------------------------------------//	
	
	
	/** 
	 * Used for looking up information in DB
	 * 
	 * @param Query string
	 * @param what the user is searching for
	 * @return The result of the query in a ResultSet
	 * @throws SQLException - if you screwed up the query. Jerk. 
	 */
public ResultSet sql(String query, SQLType type) throws SQLException{
		
		try {
			Statement stmt = con.createStatement();
			
			if(type == SQLType.query){
			ResultSet rs = stmt.executeQuery(query);
			return rs;
			
			}else if(type == SQLType.insert){
				
			}else if(type == SQLType.delete){
				
			}
			return null;
		}
		catch (SQLException ex)
		{
		    System.out.println("Message: " + ex.getMessage());
		    try 
		    {
			// undo the insert
			con.rollback();	
		    }
		    catch (SQLException ex2)
		    {
			System.out.println("Message: " + ex2.getMessage());
			throw ex2;
		    }
		    throw ex;
		}
	}

public void updateMessage(String comment, boolean was) throws SQLException{
	
	if(was){
		this.updateStatusBar(comment + "was successfull");
	}else{
		this.updateStatusBar(comment + "was not successfull! Please check all criteria and try again.");
		throw new SQLException(comment + "was not successfull! Please check all criteria and try again.");
	}
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

	public Date formatDate(Date oldDate) throws ParseException{
		String oldString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(oldDate);
		Date newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(oldString);
		return newDate;
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
	 * Calls System.exit(), but let's us close
	 * anything that needs to be closed in the model (in case we need to gracefully kill the driver)
	 * @param reasonCode - same as System.exit's parameter.
	 */
	public void exit(int reasonCode){
		System.out.println("Controller Exit called");
		System.exit(reasonCode);
	}

}

