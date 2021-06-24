package rohan.projects.ContentModeration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

import CustomExceptions.YoutubeException;
import dao.YoutubeDao;
import db.UserOperations;

@RestController
public class Controller {
	
	@PostMapping(value="/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> welcome(String email, String password)
	{
		UserOperations op = new UserOperations();
		ResponseEntity<String> response;
		response = op.authenticateUser(email, password);
		return response;
	} 

	@PostMapping(value="/login/youtube", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> youtubeFetchCommentsUsingVideoID(String vid) throws GoogleJsonResponseException, GeneralSecurityException, IOException
	{
		try {
		ArrayList<YoutubeDao> yd = new ArrayList<>();
		yd = YoutubeClass.fetchCommentsUsingVid(vid, 10);
		
		for(int i=0;i<10;i++)
		{
		//Analyze Toxicity
		PerspectiveAPI pa = new PerspectiveAPI();
		yd.get(i).setToxicity(pa.calToxicity(yd.get(i).getComment()));
		}
		return new ResponseEntity<String>(yd.toString(),HttpStatus.ACCEPTED);
	
		}
		catch(Exception e)
		{
			throw new YoutubeException("Check the Video ID again");
		}
	
	
}
}
	