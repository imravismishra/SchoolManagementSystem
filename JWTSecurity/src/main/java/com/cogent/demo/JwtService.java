package com.cogent.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.apache.catalina.startup.ListenerCreateRule.OptionalListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${secret_key}")
	private String secret_key;

	@Autowired
	private TokenEntityRepository tokenEntityRepository;

	public String extractUserEmail(String jwtToken) {
		// TODO Auto-generated method stub
		return extractClaims(jwtToken, Claims::getSubject);
	}

	public <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(jwtToken);
		return claimsResolver.apply(claims);

	}

	private Claims extractAllClaims(String jwtToken) {
		return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(jwtToken).getPayload();

	}

	private SecretKey getSignInKey() {

		byte[] keyBytes = Decoders.BASE64.decode(secret_key);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
		String userEmail = userDetails.getUsername();
		return (userEmail.equals(extractUserEmail(jwtToken)) && !isTokenExpired(jwtToken));
	}

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().claims(extraClaims).subject(userDetails.getUsername())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60)).signWith(getSignInKey()).compact();
	}

	private boolean isTokenExpired(String jwtToken) {

		return extractExpiration(jwtToken).before(new Date());
	}

	public Date extractExpiration(String jwtToken) {
		return extractClaims(jwtToken, Claims::getExpiration);
	}

}
