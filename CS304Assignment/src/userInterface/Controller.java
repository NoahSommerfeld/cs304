/**
 * This class is the central controller. All UI commands will call methods here.  
 */

package userInterface;

import java.io.IOException;
import java.math.RoundingMode;
import java.security.interfaces.RSAKey;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import model.Book;
import model.BookCopy;
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
	private final String ON_HOLD = "on-hold";
	private final String IN = "in";
	private final String OUT = "out";
	
	
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
	
	
	public int createNewUser(User newUser) throws SQLException, ParseException{
		
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
			
			String newQuery = "SELECT BID from Borrower where sinOrStNo = " + newUser.getSinOrStNo();
			
			ResultSet rs = stmt.executeQuery(newQuery);
			rs.next();
			return rs.getInt(1);
			
			
			
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
	
	public User getUser(int bid) throws SQLException, UserCreationException, BadUserIDException {

		User user;
		String statement;
		ResultSet rs;
		
		try{
		
			statement = "select count(*) from Borrower where bid = '"+bid+"'";
			rs = sql(statement, SQLType.query);
			rs.next();
			
			if(rs.getInt(1)<1){
				throw new BadUserIDException("This user does not exist.");
			}
		}catch (BadUserIDException e){
			throw e;
		}
		
		try{
		statement = "Select * from Borrower where bid = '"+ bid +"' and ROWNUM = 1";
		rs = sql(statement, SQLType.query);
		rs.next();
		
		
		String address = rs.getString("address");
		String password = rs.getString("password");
		String name = rs.getString("name");
		long phone = rs.getLong("phone");
		String emailAddress = rs.getString("emailAddress");
		long sinorstno = rs.getLong("sinorstno");
		Date date = rs.getDate("expirydate");
		UserType type = UserType.valueOf(rs.getString("type"));
		
		user = new User(bid,address,password,name,phone,
				emailAddress,sinorstno,date,type);
		}catch(UserCreationException e){
			throw e;
		}
		
		return user;
			
	}


	/**
	 * This method is for the nice Stats bar at the top of the main window. 
	 * May add more of these to make it look more impressive. 
	 * @return the total number of users in the database
	 * @throws SQLException - if for some reason, the query fails. Static command though...
	 */
	public int getNumberOfUsers() throws SQLException{


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
	 * DANIEL: THIS IS IMPORTANnew BadUserIDExceptionT
	 * the books will be passed with the default copy no (0)
	 * If it already exists, please throw a new BadCopyNumberException 
	 * with the first free copyno included. 
	 * Might want to search for # of copies first, rather than trying to sort
	 * through the SQLExceptions coming back to see if that was the reason 
	 * @param newBook - the book to be added to the DB
	 * @throws SQLException - if Oracle complains
	 * @throws BadCopyNumberException - if the copy number is already taken. 
	 */
	
public int createNewBook(Book newBook) throws SQLException, BadCopyNumberException{
		try{
			String statement;
			ResultSet rs;
			
			
			//Copy Number
				
				//Check if there is a 
				statement = "select count(*) from BookCopy where callnumber ='" 
							+ newBook.getCallNumber() + "' and copyNo=" + newBook.getCopyNo();
				rs = sql(statement, SQLType.query);
				rs.next();
				
				int copyCount = rs.getInt(1);
				
				if (copyCount == 0){
					
					//book
					
					statement = "INSERT INTO Book VALUES ('" 
							+ newBook.getCallNumber() + "', "
							+ newBook.getISBN() + ", '"
							+ newBook.getTitle() + "', '" 
							+ newBook.getMainAuthor() + "', '"
							+ newBook.getPublisher() + "', " 
							+ newBook.getYear() + ")";
					
					System.out.println(statement);
					System.out.println(newBook.getCopyNo());
					sql(statement, SQLType.insert);
					
					
					
					//CopyNo
					
					statement = "INSERT INTO BookCopy VALUES ('" 
							+ newBook.getCallNumber() + "'," 
							+ newBook.getCopyNo() + ",'in')";
				
					System.out.println(statement);
					sql(statement, SQLType.insert);
					System.out.println(statement);
					//Author

					//Add Main Author
					statement = "INSERT INTO HasAuthor VALUES ('" 
							+ newBook.getCallNumber() + "', '"
							+ newBook.getMainAuthor() + "')";
					System.out.println(statement);
					sql(statement, SQLType.insert);
						System.out.println("Size: " + newBook.getAuthors().size());
					//Add Secondary Authors
					for(String s : newBook.getAuthors()){
						statement = "INSERT INTO HasAuthor VALUES ('" + newBook.getCallNumber() + "', '" + s +"')";
						System.out.println(statement);
						sql(statement, SQLType.insert);
					}

					//Subject
					
					for(String s : newBook.getSubjects()){
						s = s.toLowerCase();
						statement = "INSERT INTO HasSubject VALUES ('" + newBook.getCallNumber() + "', '" + s +"')";
						System.out.println(statement);
						sql(statement, SQLType.insert);
					}
					
					
				}else 
					
					if( copyCount >= 1){
						System.out.println("Test: " + newBook.getCopyNo());
					statement = "select max(copyNo) from bookCopy where callNumber='" + newBook.getCallNumber() + "'";
					rs = sql(statement, SQLType.query);
					rs.next();
					copyCount = rs.getInt(1);
					
					
					String message = "The copy number" + newBook.getCopyNo() + "does not exist. The next available copy number is: ";
					copyCount++;

				
					statement = "INSERT INTO BookCopy VALUES ('" 
								+ newBook.getCallNumber() + "', " 
								+ copyCount + ", "
								+ "'in' " +  ")";
					System.out.println(statement);
					sql(statement, SQLType.insert);
					throw new BadCopyNumberException(message,copyCount);
				}else{
					throw new SQLException("wasn't suposed to get here");
				}
				
				
				return newBook.getCopyNo();
				
				
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
	/*results.add("(1In-2Out-1Hld) - Hitchhiker's guide to the galaxy. Next due back on January 14, 2015");
	results.add("(1In-0Out-0Hldd) - Daniel's guide to the galaxy. All in");
	results.add("(0In-1Out-2Hld) - Shit Shit, fucking kittens. Next due back on April 20, 2015");*/
	String query = makeSearchQuery(selectedItem, searchArgument);
	
	stmt = con.createStatement();
	ResultSet rs;
	rs = stmt.executeQuery(query);
	
	while(rs.next()){
		System.out.println("should have worked");
		results.add(rs.getString(1) + " - " + rs.getString(3));
		
	}
	
	//TODO: add in # of copies in and out
	return results;
	
}


	private String makeSearchQuery(SearchAbleKeywords selectedItem,
		String searchArgument) {
		
		//TODO implement subject and author search
		//TODO try doing keyword search instead
		String query = "";
	if(selectedItem == SearchAbleKeywords.Title){
		query = "SELECT * From book where title = '" + searchArgument + "'";
		System.out.println(query);
	}
	else if(selectedItem == SearchAbleKeywords.Subject){
		searchArgument = searchArgument.toLowerCase();
		query = "select * from book, hassubject where"
				+ " book.callnumber = hasSubject.callnumber"
				+ " AND hassubject.subject = '" + searchArgument + "'";

	}
	else if(selectedItem == SearchAbleKeywords.Author){
		query = "select * from book, hasAuthor where"
				+ " book.callnumber = hasAuthor.callnumber"
				+ " AND hasAuthor.name = '" + searchArgument + "'";
	}
	
	return query;
}

	/**
	 * Returns the most popular books, and the number of times they've been
	 * checked out. May need to modify this later from just titles. 
	 * @return a list of books and the times they've been checked out, all as one concatenated String. 
	 */
	public List<String> getPopularBooks(int year, int numResults) throws SQLException{
		ArrayList<String> theResults = new ArrayList<String>();
		//AAAAA
		theResults.add("(5) - Hitchhiker's guide to your mom");
		theResults.add("(2) - war of your mom");
		theResults.add("(2) - around Daniel's mom in 80 days");
		return theResults;
		/*SELECT borrowing.callnumber, book.title
		FROM borrowing, book,
		WHERE borrowing.CALLNUMBER = book.CALLNUMBER
		AND
		salesDate BETWEEN '11/11/2010 00:00:00.00' AND '11/11/2010 23:59:59.999'*/
		
		/*
		SELECT borrowing.callnumber, book.title, count(column_name)
		FROM borrowing
		GROUP BY column_name; */
		
	}
	
	public ArrayList<String> getSubjects() throws SQLException{
		ArrayList<String> temp = new ArrayList<String>();
		HashSet<String> tempSet = new HashSet<String>();
		String statement;
		ResultSet rs;
		
		statement = "SELECT subject from HasSubject group By(subject)";
		rs = sql(statement, SQLType.query);
				
		while(rs.next()){
			tempSet.add(rs.getString(1));
		}
		
		for(String s : tempSet){
			temp.add(s);
		}
		
		return temp;
	}
	


	
	
//----------------------------------ProcessingBooks--------------------------------------//
	

	
	//Checks out a book, when given a call number
	public void checkOut(ArrayList<BookCopy> copies, int bid) throws SQLException, NotCheckedInException, BadCallNumberException, BadUserIDException, UserCreationException, ParseException {
		String statement;
		ResultSet rs;
		User user = getUser(bid);
		
		
		for(BookCopy copy: copies){

			String title = confirmOkToCheckOut(copy.getCallNo(), copy.getCopyNo());
			Date date = new Date(System.currentTimeMillis());
			statement = "insert into borrowing values (borid_counter.nextVal, "
					+ user.getBID()+", '" 
					+ copy.getCallNo()+"', "
					+ copy.getCopyNo() +", '"
					+ formatDate(date) +"', Null)";
			
			System.out.println(statement);
			rs = sql(statement, SQLType.insert);
		//	rs.next();
			
			
		}
		
		ResultSet rs1;
		ResultSet rs2;
		
		
		//SQLException from the db
		//notcheckedinexception if it's not 'borrowable' //TODO differentiate between 'out' and 'on hold'
		//badCallNumberException if it isn't in the db. (vs. db connection errors)
		//BadUserIDException if the user is not in the DB
		this.updateStatusBar("Item(s) checked out");
		
	}

	/**
	 * This method just confirms that call number is valid. 
	 * @param callNumber
	 * @param copyNo 
	 * @return
	 * @throws BadCallNumberException
	 * @throws NotCheckedInException
	 */
	public String confirmOkToCheckOut(String callNumber, int copyNo) throws SQLException, BadCallNumberException, NotCheckedInException {
		stmt = con.createStatement();
		String statement = "Select Status from bookcopy where callNumber = '" + callNumber + "'";
		ResultSet rs = stmt.executeQuery(statement); 
		System.out.println(callNumber);
		if(!rs.next()){
			throw new BadCallNumberException("Book not found in system");
		}
		
		String statement2 = "Select Status from bookcopy where callnumber = '"+ callNumber +"' and copyNo = " + copyNo;

		rs = stmt.executeQuery(statement2); 
		
		String status = "";
		if(rs.next()){
			status = rs.getString(1);
		}
		else{
			throw new BadCallNumberException("Copy of book does not exist");
			
		}
		if(!status.equals("in")){
			throw new NotCheckedInException(status);
		}
		else{
			statement = "Select title from book where callnumber = '" + callNumber + "'";
			rs = stmt.executeQuery(statement);
			if(rs.next()){
				return rs.getString("title");
			}
			else{
				throw new BadCallNumberException("Book not found");
			}
		}

		
	}

	
	/**
	 * this method changes a book's status to 'checked in' or something...
	 * @param callNumber - the call number to check in
	 * @param currentTimeMillis - the current time. 
	 * @throws SQLException - if the db operation failed. 
	 * @throws FineAssessedException - if it was checked in, but a fine was assessed
	 * @throws BadUserIDException 
	 * @throws UserCreationException 
	 * @throws ParseException 
	 */
	public void returnBook(String callNumber, long currentTimeMillis, int copyNo) throws SQLException, FineAssessedException, UserCreationException, BadUserIDException, ParseException{
		String statement;
		ResultSet rs;
		Date outDate;
		long timeOut;
		boolean late = false;

		Date currDate = new Date(System.currentTimeMillis());
		
		
		//Return 
		
		//Update inDate in Borrowing Table
		statement = "update borrowing set inDate='"+formatDate(currDate)+"' WHERE callNumber='" 
					+ callNumber + "' and copyNo ="+ copyNo;
	
		rs = sql(statement, SQLType.insert);
		
		
		// If book is on hold, notify
		//recipient and mark as on hold 
		//in BookCopy. Otherwise mark as in.
		
		statement = "select * from HoldRequest where callNumber='"+callNumber+"'";
		rs = sql(statement, SQLType.query);
		
		if(rs.next()){
			
			statement = "Update BookCopy set status='on-hold' where callNumber='" 
					+ callNumber + "' and copyNo ="+ copyNo;
			sql(statement, SQLType.query);
			
			
			
			
			
		}
		
		
		//process fine if needed
		
		statement = "SELECT * FROM BORROWING WHERE callNumber='" 
					+ callNumber + "' and copyNo ="+ copyNo;
		
		
		rs = sql(statement, SQLType.query);
		rs.next();
		
		
		int bid = rs.getInt("bid");
		User user = getUser(bid);

		
		System.out.println(bid);
		Date dueDate = rs.getDate("dueDate");
		Date inDate = rs.getDate("inDate");
		
		
		System.out.println(dueDate.toString()+ "   "+inDate.toString());
		
		long dDate = dueDate.getTime();
		long iDate = inDate.getTime();
		
		System.out.println(dDate +"     " +iDate);
		
		timeOut = iDate - dDate;
		System.out.println(timeOut);
		if(timeOut > 0) late = true;
		
		
		DecimalFormat df = new DecimalFormat ("##.##");
		df.setRoundingMode(RoundingMode.DOWN);
		
		if(late){
			
			statement = "insert into fine values(fid_counter.nextVal, '"
					+ df.format(timeOut*0.05/10000000) +"', '"
					+formatDate(currDate) +"', NULL, "
					+ rs.getInt("borid") +")";
			
			sql(statement, SQLType.insert);
			
			
			this.updateStatusBar("Book was returned. Fine issued: $" + df.format(timeOut*0.05/10000000));
			throw new FineAssessedException("Book was late, fine assessed", timeOut*0.05/10000000);
		}
		
		this.updateStatusBar("Book has been checked in and is on time!");
		
	}


public void processPayment(int fid, int bid, double paymentAmount, int creditCardNo) throws SQLException{
	
	
	
	
}

	//use BID of -1 for all checked out books
	private ResultSet getCheckedOuts(int BID) throws SQLException{
		stmt = con.createStatement();
		if(BID == -1){
			System.out.println("tets");
			return sql("select borrowing.borid, book.callNumber, book.title, borrower.bid, borrowing.outdate, borrower.type, borrower.name "
					+ "from borrowing, book, borrower where"
					+ " borrowing.bid = borrower.bid"
					+ " AND borrowing.callnumber = book.callnumber"
					+ " AND borrowing.indate is NULL",SQLType.query);
			
		}
		String statement = "Select * from borrowing where indate = null";
		return stmt.executeQuery(statement); 
		
		
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
		/*
		stmt = con.createStatement();
		String statement = "Select * from borrowing where outdate = '" + callNumber + "'";
		ResultSet rs = stmt.executeQuery(statement); 
		*/
		//select borrowing.borid, borrowing.outdate, borrower.type from borrowing, borrower where borrowing.bid = borrower.bid AND borrowing.indate = null ??
		ArrayList<String> slackers = new ArrayList<String>();
		DateFormat temp = new SimpleDateFormat("dd/MMM/YYYY");
		String toDaysDate = temp.format(System.currentTimeMillis());
		System.out.println(toDaysDate);
		ResultSet rs = getCheckedOuts(-1);
		

		while(rs.next()){
			String test = "";
			test += rs.getString("bid");
			test += " - ";
			test += rs.getString("callnumber");
			test += " - ";
			test += rs.getString("name");

			slackers.add(test);
			
			test = "";
			System.out.println("yep");
		}
		
		
		
		
		
		//TODO actually query the DB
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
	public String[][] getCheckOuts(String filterSubject) throws SQLException{
		if(filterSubject == null||filterSubject.equals("No Subject Filter")){
			filterSubject = "";
		}
		
		ResultSet rs;
		
		
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
	 * String["Call Number", "Title"]
	 * @param loggedInUser
	 * @return
	 * @throws SQLException 
	 */
	public String[][] getHoldRequests(User loggedInUser) throws SQLException {
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		String statement = "Select holdrequest.callnumber,"
				+ " book.title, book.mainAuthor from holdrequest, book, "
				+ "borrower WHERE holdrequest.bid = borrower.bid AND "
				+ "holdrequest.callnumber = book.callnumber AND "
				+ "holdrequest.bid = "+ loggedInUser.getBID();
		
		ResultSet rs = sql(statement, SQLType.query);
		
		while(rs.next()){
			System.out.println("yep");
			ArrayList<String> row = new ArrayList<String>();
			row.add(rs.getString("callnumber"));
			row.add(rs.getString("Title"));
			row.add(rs.getString("MainAuthor"));
			temp.add(row);
		}
		if(temp.size() == 0){
			return new String[0][3];
		}
		String[][] newData = new String[temp.size()][temp.get(0).size()];
		for(int outerCount = 0; outerCount<temp.size(); outerCount++){
			for(int innerCount = 0; innerCount<temp.get(0).size(); innerCount++){
				newData[outerCount][innerCount] = temp.get(outerCount).get(innerCount);
			}
		}
		
		return newData;/*
		
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

		
		return data;*/
	}

	
	/**
	 * String ["Call Number", "Title", "Checked Out", "Due", ""]
	 * @param loggedInUser
	 * @return
	 * @throws SQLException 
	 */
	public String[][] getLoanedBooks(User loggedInUser) throws SQLException {
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		String statement = "select borrowing.callnumber,"
				+ " book.title, borrowing.outdate from borrowing,"
				+ " book where borrowing.callnumber = book.callnumber"
				+ " AND borrowing.indate is null AND borrowing.bid = " + loggedInUser.getBID();
		ResultSet rs = sql(statement, SQLType.query);
		
		
		while(rs.next()){
			System.out.println("yep");
			ArrayList<String> row = new ArrayList<String>();
			row.add(rs.getString("Callnumber"));
			row.add(rs.getString("Title"));
			row.add(rs.getString("outdate"));
			row.add(rs.getString("outdate")); //TODO calculate due date
			row.add("");
			temp.add(row);
		}
		if(temp.size() == 0){
			return new String[0][5];
		}
		String[][] newData = new String[temp.size()][temp.get(0).size()];
		for(int outerCount = 0; outerCount<temp.size(); outerCount++){
			for(int innerCount = 0; innerCount<temp.get(0).size(); innerCount++){
				newData[outerCount][innerCount] = temp.get(outerCount).get(innerCount);
			}
		}
		
		return newData;/*
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

		return data;*/
	}
	
	/**
	 * String ["Fine ID", "Title", "Checked Out", "Returned", "Fine Amount"]
	 * @param loggedInUser
	 * @return
	 * @throws SQLException 
	 */

	public String[][] getOutstandingFines(User loggedInUser) throws SQLException {
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		
		String statement = "Select fine.fid, book.title, "
				+ "borrowing.outdate, borrowing.indate,"
				+ " fine.amount from fine, book, borrowing "
				+ "where fine.borid = borrowing.borid AND "
				+ "borrowing.callnumber = book.callnumber "
				+ "AND fine.paiddate is null AND"
				+ " borrowing.bid = " + loggedInUser.getBID();
		System.out.println(loggedInUser.getBID());
			ResultSet rs = sql(statement, SQLType.query);
			
			while(rs.next()){
				System.out.println("yep");
				ArrayList<String> row = new ArrayList<String>();
				row.add(rs.getString("FID"));
				row.add(rs.getString("Title"));
				row.add(rs.getString("outdate"));
				row.add(rs.getString("indate"));
				row.add(rs.getString("Amount"));
				temp.add(row);
			}
			if(temp.size() == 0){
				return new String[0][5];
			}
			String[][] newData = new String[temp.size()][temp.get(0).size()];
			for(int outerCount = 0; outerCount<temp.size(); outerCount++){
				for(int innerCount = 0; innerCount<temp.get(0).size(); innerCount++){
					newData[outerCount][innerCount] = temp.get(outerCount).get(innerCount);
				}
			}
			
			return newData;/*
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
		return date;*/
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
public ResultSet sql(String statement, SQLType type) throws SQLException{
		
		try {
			Statement stmt = con.createStatement();
			ResultSet rs;
			
			if(type == SQLType.query){
			rs = stmt.executeQuery(statement);
			return rs;
			
			}else if(type == SQLType.insert){
				stmt.executeUpdate(statement);
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

	public String formatDate(Date oldDate) throws ParseException{
		DateFormat temp = new SimpleDateFormat("dd/MMM/YYYY");
		String newDate = temp.format(oldDate);
		//String oldString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(oldDate);
		//Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(oldString);
		//System.out.println(newDate);
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
	

	/****
	 * Calls System.exit(), but let's us close
	 * anything that needs to be closed in the model (in case we need to gracefully kill the driver)
	 * @param reasonCode - same as System.exit's parameter.
	 */
	public void exit(int reasonCode){
		System.out.println("Controller Exit called");
		System.exit(reasonCode);
	}

	public String placeHoldRequest(String callNumber, int BID) throws SQLException, ParseException{
		int copyNo = getFirstInCallNo(callNumber);
		String dublicateStatement = "Select * from HoldRequest where callnumber = '" + callNumber + "' AND bid = " + BID;
		ResultSet rs = sql(dublicateStatement,SQLType.query);
		if(rs.next()){
			return "Hold Request Already Present";
		}
		
		if(copyNo == -1){
			String statement = "INSERT INTO HOLDREQUEST VALUES(hid_counter.nextVal, " + BID + ", '" + callNumber + "', " + "NULL)";
			sql(statement, SQLType.query);
			System.out.println(statement);
			return "Hold request created";
		}
		else{
			
			Date currDate = new Date(System.currentTimeMillis());
			
			String statement = "INSERT INTO HOLDREQUEST VALUES(hid_counter.nextVal, " + BID + ", '" + callNumber + "', '" + formatDate(currDate) + "')";
			System.out.println(statement);
			changeToHold(callNumber, copyNo);
			sql(statement, SQLType.query);
			System.out.println("test??");
			return "Copy " + copyNo + " Placed on hold";
		}

	}

	private void changeToHold(String callNumber, int copyNo) throws SQLException {
		Statement stmt = con.createStatement();
		ResultSet rs;
		
		String statement = "Update bookcopy set status='on-hold' where callnumber = '"+callNumber+"' AND copyno=" + copyNo;
		System.out.println(statement);
		stmt.executeUpdate(statement);  
		System.out.println("Not my problem");
	}

	private int getFirstInCallNo(String callNumber) throws SQLException {
		String statement = "Select copyno from bookcopy where status = 'in' AND "
				+ "callnumber = '" + callNumber + "'";
		System.out.println(statement);
		ResultSet rs = sql(statement, SQLType.query);
		if(rs.next()){
			return rs.getInt(1);
		}
		else{
			return -1;
		}
		
	}

}

