package model;

import java.util.Date;

import exceptions.BookCreationException;
import exceptions.UserCreationException;

public class Book {
	
	private String callNumber;
	private String ISBN;
	private String title;
	private String mainAuthor;
	private String publisher;
	private int year;
	private int copyNo;
	private BorrowStatus status;
	
	public Book(String callNumber, String ISBN, String title,
			String mainAuthor, String publisher, int year,
			int copyNo, BorrowStatus status) throws BookCreationException{
	validate(callNumber, ISBN, title, mainAuthor, 
			publisher, year, copyNo, status);
	this.setCallNumber(callNumber);
	this.setISBN(ISBN);
	this.setTitle(title);
	this.setPublisher(publisher);
	this.setYear(year);
	this.setCopyNo(copyNo);
	this.setStatus(status);
	this.setMainAuthor(mainAuthor);

	
	
}
	
	public Book(String callNumber, String ISBN, String title,
			String mainAuthor, String publisher, String year,
			String copyNo, BorrowStatus status) throws BookCreationException{
	
		
		int newYear;
		try{
			//TODO use undepreciated method
			newYear = Integer.parseInt(year);
		}
		catch(IllegalArgumentException ia){
			throw new BookCreationException("Year formatting wrong: " + year);
		}
		
		int newCopyNo;
		try{
			newCopyNo = Integer.parseInt(copyNo);
		}
		catch(NumberFormatException nfe){
			throw new BookCreationException("Copy Number formatted wrong: " + copyNo);
		}
		
		
	validate(callNumber, ISBN, title, mainAuthor, 
				publisher, newYear, newCopyNo, status);
	this.setCallNumber(callNumber);
	this.setISBN(ISBN);
	this.setTitle(title);
	this.setPublisher(publisher);
	this.setYear(newYear);
	this.setCopyNo(newCopyNo);
	this.setStatus(status);
	this.setMainAuthor(mainAuthor);
	
	
}

private void validate(String callNumber, String ISBN, String title,
		String mainAuthor, String publisher, int year,
		int copyNo, BorrowStatus status) throws BookCreationException{
	
	
	if(callNumber == null){ //TODO do the checks here. 
		throw new BookCreationException("Null BID");
	}
	
	
}

public String getCallNumber() {
	return callNumber;
}

private void setCallNumber(String callNumber) {
	this.callNumber = callNumber;
}

public String getISBN() {
	return ISBN;
}

private void setISBN(String iSBN) {
	ISBN = iSBN;
}

public String getTitle() {
	return title;
}

private void setTitle(String title) {
	this.title = title;
}

public String getMainAuthor() {
	return mainAuthor;
}

private void setMainAuthor(String mainAuthor) {
	this.mainAuthor = mainAuthor;
}

public String getPublisher() {
	return publisher;
}

private void setPublisher(String publisher) {
	this.publisher = publisher;
}

public int getYear() {
	return year;
}

private void setYear(int year) {
	this.year = year;
}

public int getCopyNo() {
	return copyNo;
}

public void setCopyNo(int copyNo) {
	this.copyNo = copyNo;
}

public BorrowStatus getStatus() {
	return status;
}

public void setStatus(BorrowStatus status) {
	this.status = status;
}



}
