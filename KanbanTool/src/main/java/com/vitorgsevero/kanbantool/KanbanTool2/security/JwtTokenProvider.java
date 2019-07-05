package com.vitorgsevero.kanbantool.KanbanTool2.security;

import static com.vitorgsevero.kanbantool.KanbanTool2.security.SecurityConstants.EXPIRATION_TIME;
import static com.vitorgsevero.kanbantool.KanbanTool2.security.SecurityConstants.SECRET;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.vitorgsevero.kanbantool.KanbanTool2.domain.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	//generate the token
	
	public String generateToken(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		Date now = new Date(System.currentTimeMillis());
		
		Date expiryDate = new Date(now.getTime()+EXPIRATION_TIME);
		
		String userId = Long.toString(user.getId());
		
		Map<String, Object> claimsMap = new HashMap<>();
		
		claimsMap.put("id", (Long.toString(user.getId())));
		claimsMap.put("username", user.getUsername());
		claimsMap.put("fullname", user.getFullName());
		
		return Jwts.builder()
				.setSubject(userId)
				.setClaims(claimsMap)
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, SECRET)
				.compact();
		
	}
	
	
	//validate the token
	
	//get the user id from token
	
	
}
