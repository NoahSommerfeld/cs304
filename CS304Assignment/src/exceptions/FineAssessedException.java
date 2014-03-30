package exceptions;

public class FineAssessedException extends Exception {

	private final double fineAmount;
	public FineAssessedException(String message, double amount){
		super(message);
		this.fineAmount = amount;
	}
	public double getFineAmount() {
		return fineAmount;
	}
	
	
}
