package model;

import java.util.Date;

import exceptions.UserCreationException;

public class User {
	

	private String password;
	private String name;
	private Long phone;
	private String emailAddress;
	private Long sinOrStNo;
	private Date expiryDate;
	private UserType type;
	private String address;
	private int BID;
	
	public User(int BID, String address, String password, String name, Long phone, 
			String emailAddress, Long sinOrStNo, Date expiryDate, UserType type) throws UserCreationException{
	validate(BID, address, password, name, phone, 
		emailAddress, sinOrStNo, expiryDate, type);
	this.address = address;
	this.password = password;
	this.name = name;
	this.phone = phone;
	this.emailAddress = emailAddress;
	this.sinOrStNo = sinOrStNo;
	this.expiryDate = expiryDate;
	this.type = type;
	this.setBID(BID);
	
	
}
	
	public User(String BID, String address, String password, String name, String phone, 
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
		
		int bid;
		try{
			bid = Integer.parseInt(BID);
		}catch(IllegalArgumentException e){
			throw new UserCreationException("BID not formatted correctly");
		}
		
	validate(bid, address, password, name, phoneNumber, 
		emailAddress, sin, tempDate, type);
	this.address = address;
	this.password = password;
	this.name = name;
	this.phone = phoneNumber;
	this.emailAddress = emailAddress;
	this.sinOrStNo = sin;
	this.expiryDate = tempDate;
	this.type = type;
	this.BID = bid;
	
	
}

private void validate(int BID, String address, String password, String name, Long phone, 
		String emailAddress, Long sinOrStNo, Date expiryDate, UserType type) throws UserCreationException{
	
	
	if(sinOrStNo == null){ //TODO do the checks here. 
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

public int getBID() {
	return BID;
}

public void setBID(int bID) {
	BID = bID;
}

}
