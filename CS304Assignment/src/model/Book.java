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
	this.callNumber = callNumber;
	this.ISBN = ISBN;
	this.title = title;
	this.publisher = publisher;
	this.year = year;
	this.copyNo = copyNo;
	this.status = status;

	
	
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
	this.callNumber = callNumber;
	this.ISBN = ISBN;
	this.title = title;
	this.publisher = publisher;
	this.year = newYear;
	this.copyNo = newCopyNo;
	this.status = status;
	
	
}

private void validate(String callNumber, String ISBN, String title,
		String mainAuthor, String publisher, int year,
		int copyNo, BorrowStatus status) throws BookCreationException{
	
	
	if(callNumber == null){ //TODO do the checks here. 
		throw new BookCreationException("Null BID");
	}
	
	
}



}
