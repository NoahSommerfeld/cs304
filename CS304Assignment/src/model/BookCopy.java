package model;

public class BookCopy {

	private final String callNo;
	private final int copyNo;
	
	public BookCopy(String callNo, int copyNo){
		this.callNo = callNo;
		this.copyNo = copyNo;
	}

	public int getCopyNo() {
		return copyNo;
	}

	public String getCallNo() {
		return callNo;
	}
}
