package db;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;


public class Connection {

	public MongoDatabase getConnection()
	{
		MongoClient mongo = new MongoClient(new MongoClientURI("mongodb+srv://Rohan:Rb%40199701@cluster0.ze6ax.mongodb.net/test"));
		MongoDatabase db = mongo.getDatabase("test");
		return db;
	}
	public static void main(String args[])
	{
		Connection cc= new Connection();
		//System.out.println(cc.getConnection());
	}
}
