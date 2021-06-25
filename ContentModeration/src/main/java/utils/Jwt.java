package utils;

import java.util.Date;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class Jwt {

	public String createJWT()
	{
		try {
		    Algorithm algorithm = Algorithm.HMAC256("RohanBan");
		    String token = JWT.create().withExpiresAt(new Date(System.currentTimeMillis() + (5 * 60 * 1000)))
		        .withIssuer("auth0")
		        .sign(algorithm);
		    return token;
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
		}
		return null;
		
	}
	
	public String verifyToken(String token)
	{
		
		try {
		    Algorithm algorithm = Algorithm.HMAC256("RohanBan");
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer("auth0")
		        .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(token);
		    return token;
		} catch (JWTVerificationException exception){
		    //Invalid signature/claims
			return "wrong";
		}
	}


public static void main(String args[])
{
	Jwt jwt = new Jwt();
	String token = jwt.createJWT();
	System.out.println("Token is: "+token);
	System.out.println(jwt.verifyToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCIsImV4cCI6MTYyNDYxNzYyNX0.k2f4vv0yNJoUvEdrc-JboI3rcxG9NlWobRt2nDY6HAA"));
	
}
}