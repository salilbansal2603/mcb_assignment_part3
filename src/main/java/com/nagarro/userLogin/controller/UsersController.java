package com.nagarro.userLogin.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.userLogin.exception.RecordNotFoundException;
import com.nagarro.userLogin.exception.RetriesExceededException;
import com.nagarro.userLogin.jwt.JwtUtils;
import com.nagarro.userLogin.service.UsersService;
import com.nagarro.userLogin.service.impl.UserDetailsImpl;

import dto.ErrorStatus;
import dto.JwtResponse;
import dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class UsersController {

	@Autowired
	private UsersService usersService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/user-login")
	@CrossOrigin
	private ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtUtils.generateJwtToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
					.collect(Collectors.toList());

			return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUser(), roles));
		} catch (RetriesExceededException e) {
			return new ResponseEntity<ErrorStatus>(new ErrorStatus(401, "MAX_RETRIES_EXCEEDED", e.getMessage()),
					HttpStatus.UNAUTHORIZED);
		} catch (RecordNotFoundException e) {
			return new ResponseEntity<ErrorStatus>(new ErrorStatus(404, "CREDENTIALS_INVALID", e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}

	// @Scheduled(cron = "0 0 0 * * ?")
	@Scheduled(cron = "0 0/5 * * * ?")
	public void clearRetries() {
		usersService.clearRetries();
	}
}
