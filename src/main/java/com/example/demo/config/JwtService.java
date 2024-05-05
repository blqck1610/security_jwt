package com.example.demo.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static  String SECRECT_KEY = "8E231F4F12542B1D1CE59B01573B30209AD370CB90CA4BA5019380D2C5A61466";
	public String extractUsername(String jwtToken) {
		
		return extractClaims(jwtToken, Claims::getSubject);
	}
	public String generateToken( UserDetails userDetails) {
		
		return generateToken(new HashMap<String, Object>(), userDetails);
	}
	
	
	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		
		return Jwts.builder()
				.claims(extraClaims)
				.subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000*60*24))
				.signWith(getKey())
				.compact();
	}
	
	private <T>T extractClaims(String jwtToken, Function<Claims, T > claimsResolver){
		Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String jwtToken) {
		return Jwts.parser()
				.verifyWith(getKey())
				.build()
				.parseSignedClaims(jwtToken)
				.getPayload();
	}

	private SecretKey getKey() {
		byte[] key = Decoders.BASE64.decode(SECRECT_KEY);
		return Keys.hmacShaKeyFor(key);
	}
	
	public Boolean isTokenValid(String jwtToken, UserDetails userDetails) {
		
		return (extractUsername(jwtToken).equals(userDetails.getUsername()) && !isExpired(jwtToken));
	}
	private boolean isExpired(String jwtToken) {
		return extractExpired(jwtToken).before(new Date());
	}
	private Date extractExpired(String jwtToken) {
		// TODO Auto-generated method stub
		return extractClaims(jwtToken, Claims::getExpiration);
	}

}
