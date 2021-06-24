package CustomExceptions;

public class NoAuthFieldException extends RuntimeException{

	String message;

	public String getMessage() {
		return message;
	} 

	public void setMessage(String message) {
		this.message = message;
	}

	public NoAuthFieldException(String message) {
		this.message  = message;
	}
	
	
}
