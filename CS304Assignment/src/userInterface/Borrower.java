package userInterface;

import java.util.Date;

import model.BorrowerType;

public class Borrower {
	
	private String BID;
	private String password;
	private String name;
	private int phone;
	private String emailAddress;
	private int sinOrStNo;
	private Date expiryDate;
	private BorrowerType type;
	
public Borrower(String BID, String password, String name, int phone, 
		String emailAddress, int sinOrStNo, Date expiryDate, BorrowerType type){
	
}

public String getBID() {
	return BID;
}


public String getPassword() {
	return password;
}

public String getName() {
	return name;
}


public int getPhone() {
	return phone;
}


public String getEmailAddress() {
	return emailAddress;
}


public int getSinOrStNo() {
	return sinOrStNo;
}


public Date getExpiryDate() {
	return expiryDate;
}


public BorrowerType getType() {
	return type;
}

}
