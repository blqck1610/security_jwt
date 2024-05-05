package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.JwtService;
import com.example.demo.user.Role;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service

public class AuthenticationService {
	
	@Autowired
	private  PasswordEncoder passwordEncoder;
	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Transactional 
	 public AuthenticationResponse register(RegisterRequest request) {
		var user = User.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.role(Role.ROLE_USER)
				.build();
		 userRepository.save(user);
		 String jwtToken = jwtService.generateToken(user);
		 return AuthenticationResponse.builder()
				 .token(jwtToken)
				 .build();
		
	}

	public AuthenticationResponse authenticate(AuthenticateRequest request) {
		
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getEmail(),
				request.getPassword()));
		var user = userRepository.findByEmail(request.getEmail())
				.orElseThrow();
		String jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				 .token(jwtToken)
				 .build();
		
		
	}

}
