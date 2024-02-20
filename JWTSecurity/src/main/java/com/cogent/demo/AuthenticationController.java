package com.cogent.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private UserEntityRepository userEntityRepository;

	@Autowired
	private TokenEntityRepository tokenEntityRepository;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping(path = { "/register" })
	public String register(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("pass") String password) {

		UserEntity userEntity = UserEntity.builder().name(name).email(email).pass(passwordEncoder.encode(password))
				.roles(Role.USER).build();

		userEntityRepository.save(userEntity);

		String gJwtToken = jwtService.generateToken(userEntity);
		TokenEntity tokenEntity = TokenEntity.builder().expired(false).revoked(false).token(gJwtToken)
				.tokenType(TokenType.BEARER).userEntity(userEntity).build();

		tokenEntityRepository.save(tokenEntity);

		return gJwtToken;
	}

	@PostMapping(path = { "/authenticate" })
	public String authenticate(@RequestParam("email") String email, @RequestParam("pass") String password) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		UserEntity userEntity = userEntityRepository.findByEmail(email).orElseThrow();
		String gJwtToken = jwtService.generateToken(userEntity);

		// doubt
		List<TokenEntity> tokenEntities = tokenEntityRepository.findAllValidTokenByUserEntity(userEntity.getId());
		if (!tokenEntities.isEmpty()) {
			tokenEntities.stream().forEach((te) -> {
				te.setRevoked(true);
				te.setExpired(true);
			});
		}

		tokenEntityRepository.saveAll(tokenEntities);

		TokenEntity tokenEntity = TokenEntity.builder().expired(false).revoked(false).token(gJwtToken)
				.tokenType(TokenType.BEARER).userEntity(userEntity).build();

		tokenEntityRepository.save(tokenEntity);
		return gJwtToken;
	}

}
