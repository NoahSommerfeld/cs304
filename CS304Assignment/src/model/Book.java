package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private ArrayList<String> authors;
	private ArrayList<String> subjects;
	
	public Book(String callNumber, String ISBN, String title,
			String mainAuthor, String publisher, int year,
			int copyNo, BorrowStatus status, ArrayList<String> authors, ArrayList<String> subjects) throws BookCreationException{
	validate(callNumber, ISBN, title, mainAuthor, 
			publisher, year, copyNo, status, authors, subjects);
	
	
	this.setCallNumber(callNumber);
	this.setISBN(ISBN);
	this.setTitle(title);
	this.setPublisher(publisher);
	this.setYear(year);
	this.setCopyNo(copyNo);
	this.setStatus(status);
	this.setMainAuthor(mainAuthor);
	this.authors = authors;
	this.subjects = subjects;

	
	
}
	
	public Book(String callNumber, String ISBN, String title,
			String mainAuthor, String publisher, String year,
			String copyNo, BorrowStatus status, ArrayList<String> authors, ArrayList<String> subjects) throws BookCreationException{
	
		
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
				publisher, newYear, newCopyNo, status, authors, subjects);
	this.setCallNumber(callNumber);
	this.setISBN(ISBN);
	this.setTitle(title);
	this.setPublisher(publisher);
	this.setYear(newYear);
	this.setCopyNo(newCopyNo);
	this.setStatus(status);
	this.setMainAuthor(mainAuthor);
	this.authors = authors;
	this.subjects = subjects;
	
}

private void validate(String callNumber, String ISBN, String title,
		String mainAuthor, String publisher, int year,
		int copyNo, BorrowStatus status, ArrayList<String> authors2, ArrayList<String> subjects2) throws BookCreationException{
	
	
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

public ArrayList<String> getSubjects() {
	return subjects;
}


public ArrayList<String> getAuthors() {
	return authors;
}





}
