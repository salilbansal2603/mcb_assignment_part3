package com.nagarro.userLogin.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.nagarro.userLogin.service.impl.LoginAttemptsService;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private LoginAttemptsService loginAttemptsService;

	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		String username = event.getAuthentication().getName();
		loginAttemptsService.loginSucceeded(username);
	}
}
