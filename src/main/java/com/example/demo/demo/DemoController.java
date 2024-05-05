package com.example.demo.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
public class DemoController {
	
	@GetMapping(value = "/demoController")
	public ResponseEntity<String> sayHello(){
		return ResponseEntity.ok("Say Hello from endpoint");
	}
	
}
