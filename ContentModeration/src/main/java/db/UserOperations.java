package db;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;

import utils.Jwt;


public class UserOperations {


	
	public ResponseEntity<String> authenticateUser(String email, String password)
	{
		try {
		Connection c = new Connection();
		MongoDatabase db = c.getConnection();
		MongoCollection<Document> collection = db.getCollection("test");

		Document document = collection.find(Filters.eq("email", email)).first();
		if(document.getString("password").equals(password))
		{
			//Return JWT Token
			Jwt j = new Jwt();
			String token = j.createJWT();
			Document d = new Document();
			d.append("status", "success");
			d.append("email", email);
			d.append("auth", token);
			return new ResponseEntity<String>(d.toJson(),HttpStatus.ACCEPTED);	
		}
		else
		{
			Document d = new Document();
			d.append("status", "failed");
			d.append("Error", "Passwords dont match");
			return new ResponseEntity<String>(d.toJson(),HttpStatus.UNAUTHORIZED);
		}
		}
		catch(NullPointerException n)
		{
			Document d = new Document();
			d.append("status", "failed");
			d.append("Error", "Email not registered");
			return new ResponseEntity<String>(d.toJson(),HttpStatus.UNAUTHORIZED);
		}
		catch(Exception e)
		{
			Document d = new Document();
			d.append("status", "failed");
			d.append("Error", "Something Went wrong");
			return new ResponseEntity<String>(d.toJson(),HttpStatus.UNAUTHORIZED);
		}
	}
	
	public static void main(String args[])
	{
		UserOperations op = new UserOperations();
		op.authenticateUser("rohan.say7@gmail.co", "secret");
	}
}
