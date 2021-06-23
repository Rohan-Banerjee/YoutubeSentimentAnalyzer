package rohan.projects.ContentModeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import CustomExceptions.InvalidHeaderFieldException;
import CustomExceptions.NoAuthFieldException;
import utils.Jwt;

@Component
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		if(request.getHeader("Auth")==null)
		{
			throw new NoAuthFieldException("No auth key");
		}
		else {
		String head = request.getHeader("Auth");
		Jwt j = new Jwt();
		if(j.verifyToken(head).equals("wrong"))
				{
					throw new InvalidHeaderFieldException("Wrong key");
				}
			return true;
		}
	}
	 

	
}
