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


	public ResponseEntity<String> createUser(String email, String password, String confirmPassword)
	{
		if (!(password.equals(confirmPassword)))
		{
			Document d = new Document();
			d.append("status", "failed");
			d.append("Error", "Passwords dont match");
			return new ResponseEntity<>(d.toJson(),HttpStatus.BAD_REQUEST);
		}
		try {
			Connection c = new Connection();
			MongoDatabase db =  c.getConnection();
			MongoCollection<Document> collection = db.getCollection("test");
			FindIterable<Document> document = collection.find(Filters.eq("email",email));
			if(document.first()==null)
			{
				Document newDocument = new Document(); 
				newDocument.append("email", email);
				newDocument.append("password", password);
				collection.insertOne(newDocument);
				newDocument.remove("password");
				newDocument.append("msg", "User created successfully");
				return new ResponseEntity<>(newDocument.toJson(),HttpStatus.ACCEPTED);
			}
			else
			{
				System.out.println(document.first().toJson());
				Document d = new Document();
				d.append("status", "failed");
				d.append("Error", "User Email Already registered");
				return new ResponseEntity<>(d.toJson(),HttpStatus.CONFLICT);
			}
		}
		catch(Exception e)
		{
			Document d = new Document();
			d.append("status", "failed");
			d.append("Error", "Unknown error");
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
		}

	}
	
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
