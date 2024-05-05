package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private  AuthenticationProvider authenticationProvider ;
	@Autowired
	private  JwtAuthenticationFilter jwtAuthenticationFilter;
	
	private static String[] PUBLIC_PATH = {"/api/v1/auth/**"};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(
				(authz) -> authz.requestMatchers(PUBLIC_PATH).permitAll()
				.anyRequest().authenticated()
				
				)
			.csrf(
					(csrf) -> csrf.disable()
					)
			.sessionManagement(
					(sesion) -> sesion.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
					)
			
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		;
		
		return http.build();
	}
}
