package rohan.projects.ContentModeration;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import CustomExceptions.InvalidHeaderFieldException;
import CustomExceptions.NoAuthFieldException;

@RestControllerAdvice
public class InvalidExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<InvalidHeaderFieldException> handleInvalidFieldException(InvalidHeaderFieldException exception)
	{
		InvalidHeaderFieldException ihfe = new InvalidHeaderFieldException("Wrong auth token");
		return new ResponseEntity<InvalidHeaderFieldException>(ihfe, HttpStatus.PRECONDITION_FAILED); 
	}
	
	@ExceptionHandler
	public ResponseEntity<NoAuthFieldException> handleNoAuthField(NoAuthFieldException exception)
	{
		NoAuthFieldException ihfe = new NoAuthFieldException("No Auth Field");
		return new ResponseEntity<NoAuthFieldException>(ihfe, HttpStatus.PRECONDITION_FAILED); 
	} 
}
