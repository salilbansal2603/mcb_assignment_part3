package com.nagarro.userLogin.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nagarro.userLogin.entity.User;
import com.nagarro.userLogin.exception.RecordNotFoundException;
import com.nagarro.userLogin.exception.RetriesExceededException;
import com.nagarro.userLogin.repository.UsersRepository;
import com.nagarro.userLogin.service.UsersService;

@Service
public class UsersServiceImpl implements UsersService, UserDetailsService {

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	LoginAttemptsService loginAttemptsService;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		if (loginAttemptsService.isAccountLocked(username))
			throw new RetriesExceededException("Account is locked since you have exceeded the number of retries.");
		User user = usersRepository.findByUserName(username).orElseThrow(
				() -> new RecordNotFoundException("There is no account present with this username. Please try again."));

		return UserDetailsImpl.build(user);
	}

	@Override
	public void clearRetries() {
		List<User> users = usersRepository.findAllByIsAccountLocked(true);
		for (User user : users) {
			loginAttemptsService.unlockAccount(user);
		}
	}

}
