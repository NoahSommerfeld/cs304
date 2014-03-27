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
	
	
	
	
}
