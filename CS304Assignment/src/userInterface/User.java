package userInterface;

import java.util.Date;

import exceptions.UserCreationException;
import model.UserType;

public class User {
	
	private int	bid;
	private String password;
	private String name;
	private Long phone;
	private String emailAddress;
	private Long sinOrStNo;
	private Date expiryDate;
	private UserType type;
	private String address;
	
	public User(int bid, String address, String password, String name, Long phone, 
			String emailAddress, Long sinOrStNo, Date expiryDate, UserType type) throws UserCreationException{
	validate(address, password, name, phone, 
		emailAddress, sinOrStNo, expiryDate, type);
	this.bid = bid;
	this.address = address;
	this.password = password;
	this.name = name;
	this.phone = phone;
	this.emailAddress = emailAddress;
	this.sinOrStNo = sinOrStNo;
	this.expiryDate = expiryDate;
	this.type = type;
	
	
}
	
	public User(String address, String password, String name, String phone, 
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
		
	validate(address, password, name, phoneNumber, 
		emailAddress, sin, tempDate, type);
	this.address = address;
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
	
	
	if(BID == null){ //TODO do the checks here. 
		throw new UserCreationException("Null BID");
	}
	
	
}


public String getAddress() {
	return address;
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
