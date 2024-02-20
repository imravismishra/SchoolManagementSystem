package com.cogent.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LogoutService implements LogoutHandler {

	@Autowired
	private TokenEntityRepository tokenEntityRepository;
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		final String authHeader = request.getHeader("Authorization");
		final String jwtToken;
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}
		jwtToken = authHeader.substring(7);
		
		TokenEntity storedToken = tokenEntityRepository.findByToken(jwtToken).orElse(null);
		if(storedToken!=null)
		{
			storedToken.setExpired(true);
			storedToken.setRevoked(true);
			tokenEntityRepository.save(storedToken);
		}

	}

}
