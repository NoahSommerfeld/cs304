package userInterface;

import java.util.Date;

import model.UserType;

public class User {
	
	private String BID;
	private String password;
	private String name;
	private Long phone;
	private String emailAddress;
	private Long sinOrStNo;
	private Date expiryDate;
	private UserType type;
	
	public User(String BID, String password, String name, Long phone, 
			String emailAddress, Long sinOrStNo, Date expiryDate, UserType type) throws UserCreationException{
	validate(BID, password, name, phone, 
		emailAddress, sinOrStNo, expiryDate, type);
	this.BID = BID;
	this.password = password;
	this.name = name;
	this.phone = phone;
	this.emailAddress = emailAddress;
	this.sinOrStNo = sinOrStNo;
	this.expiryDate = expiryDate;
	this.type = type;
	
	
}
	
	public User(String BID, String password, String name, String phone, 
			String emailAddress, String sinOrStNo, String expiryDate, UserType type) throws UserCreationException{
	
		
		Date tempDate;
		try{
			//TODO use undepreciated method
		tempDate = new Date(Date.parse(expiryDate));
		}
		catch(IllegalArgumentException ia){
			throw new UserCreationException("Date formatting wrong");
		}
		
		Long phoneNumber;
		try{
		phoneNumber = Long.parseLong(phone);
		}
		catch(NumberFormatException nfe){
			throw new UserCreationException("Phone number formatted wrong: " + phone);
		}
		Long sin;
		try{
			sin = Long.parseLong(sinOrStNo);
		}
		catch(NumberFormatException nfe){
			throw new UserCreationException("Sin or Student number formatted wrong");
		}
		
	validate(BID, password, name, phoneNumber, 
		emailAddress, sin, tempDate, type);
	this.BID = BID;
	this.password = password;
	this.name = name;
	this.phone = phoneNumber;
	this.emailAddress = emailAddress;
	this.sinOrStNo = sin;
	this.expiryDate = tempDate;
	this.type = type;
	
	
}

private void validate(String BID, String password, String name, Long phone, 
		String emailAddress, Long sinOrStNo, Date expiryDate, UserType type) throws UserCreationException{
	
	
	if(BID == null){ //do the checks here. 
		throw new UserCreationException("Null BID");
	}
	
	
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


public Long getPhone() {
	return phone;
}


public String getEmailAddress() {
	return emailAddress;
}


public Long getSinOrStNo() {
	return sinOrStNo;
}


public Date getExpiryDate() {
	return expiryDate;
}


public UserType getType() {
	return type;
}

}
