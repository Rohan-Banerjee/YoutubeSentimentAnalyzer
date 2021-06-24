package CustomExceptions;

public class YoutubeException extends RuntimeException{

	String Message;
	String status = "Failed";
	public YoutubeException(String message)
	{
		Message = message;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
