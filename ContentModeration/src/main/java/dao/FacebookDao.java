package dao;

import java.util.ArrayList;
 
public class FacebookDao {
private String postId;
public String getPostId() {
	return postId;
}
public void setPostId(String postId) {
	this.postId = postId;
}
public String getPost() {
	return post;
}
public void setPost(String post) {
	this.post = post;
}
public String getEmotion() {
	return emotion;
}
public void setEmotion(String emotion) {
	this.emotion = emotion;
}
public String getIntent() {
	return intent;
}
public void setIntent(String intent) {
	this.intent = intent;
}
public String getSentiment() {
	return sentiment;
}
public void setSentiment(String sentiment) {
	this.sentiment = sentiment;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public ArrayList<String> getBadWords() {
	return badWords;
}
public void setBadWords(ArrayList<String> badWords) {
	this.badWords = badWords;
}
private String post;
private String emotion;
private String intent;
private String sentiment;
private String status;
private ArrayList<String> badWords;
private String review;
public String getReview() {
	return review;
}
public void setReview(String review) {
	this.review = review;
}
}
