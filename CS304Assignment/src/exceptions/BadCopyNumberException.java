package exceptions;

public class BadCopyNumberException extends Exception {

	private final int firstFreeCopyNo;
	public BadCopyNumberException(String message, int firstFreeCopyNo){
		super(message);
		this.firstFreeCopyNo = firstFreeCopyNo;
	}
	public int getFirstFreeCopyNo() {
		return firstFreeCopyNo;
	}
	
	
}
