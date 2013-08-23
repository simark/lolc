package ca.polymtl.lol.grammar;

public class LOLVisitException extends Exception {
	public LOLVisitException() {
		super();
	}
	
	public LOLVisitException(String message) {
		super(message);
	}
	
	public LOLVisitException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public LOLVisitException(Throwable cause) {
		super(cause);
	}
}
