package userInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

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
	
	
}
