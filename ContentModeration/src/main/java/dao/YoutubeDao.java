package dao;

public class YoutubeDao {

	String User;
	String Comment;
	Double Toxicity;
	public String getUser() {
		return User;
	}
	public void setUser(String user) {
		User = user;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	@Override
	public String toString() {
		return "{\r\n" + 
				"    \"User\": \" "+ User +" \",\r\n" + 
				"    \"Comment\": \""+Comment+"\"\r\n" + 
				"    \"Toxicity\": "+Toxicity+"\r\n" + 
				"}";
	}
	public Double getToxicity() {
		return Toxicity; 
	}
	public void setToxicity(Double toxicity) {
		Toxicity = toxicity;
	}
	

	
	
}
