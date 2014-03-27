import java.sql.*;

public class TEST{
  public static void main(String[] args) throws SQLException{


  DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
   Connection con = DriverManager.getConnection(
  "jdbc:oracle:thin:@dbhost.ugrad.cs.ubc.ca:1522:ug", "ora_x4q8", "a20292124");
  
  Statement stmt = con.createStatement();
  //int rowCount = stmt.executeUpdate("INSERT INTO branch VALUES (30, 'RICHMOND MAIN2', " + 
	//			    "'18122 No. 5 Road', 'Richmond', 5252738)");
				    

    ResultSet rs = stmt.executeQuery("SELECT branch_id, branch_name FROM branch");
    
  rs.next();
    System.out.println(rs.getString("branch_name"));
    //}
    System.out.println(rs.getString("branch_name"));
    

    System.out.println("Hello world");
    con.close();
  }
}