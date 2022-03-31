package com.nagarro.userLogin.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.nagarro.userLogin.service.impl.LoginAttemptsService;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	@Autowired
	private LoginAttemptsService loginAttemptsService;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		String username = event.getAuthentication().getName();
		loginAttemptsService.loginFailed(username);
	}

}