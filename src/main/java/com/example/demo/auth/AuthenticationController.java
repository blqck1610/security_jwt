package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/auth")

public class AuthenticationController {
	@Autowired
	private  AuthenticationService authService;

	@PostMapping(value = "/register")
	public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
	return ResponseEntity.ok(authService.register(request));
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticateRequest request){
	return ResponseEntity.ok(authService.authenticate(request));
	}
}

