package com.nagarro.userLogin.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.userLogin.entity.User;
import com.nagarro.userLogin.exception.RecordNotFoundException;
import com.nagarro.userLogin.exception.RetriesExceededException;
import com.nagarro.userLogin.repository.UsersRepository;

@Service
public class LoginAttemptsService {

	private static final int MAX_ATTEMPTS_ALLOWED = 3;
	private static final long LOCK_DURATION = 24 * 60 * 60 * 1000;
	@Autowired
	UsersRepository userRepository;

	public void loginSucceeded(String key) {
		Optional<User> user = userRepository.findByUserName(key);
		user.ifPresent(u -> u.setRetryCount(0));
		userRepository.save(user.get());
	}

	public void loginFailed(String key) {
		Optional<User> userOpt = userRepository.findByUserName(key);

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			int currentFailAttempts = user.getRetryCount();
			if (currentFailAttempts >= MAX_ATTEMPTS_ALLOWED) {
				this.lockAccount(user);
				throw new RetriesExceededException("Account is locked since you have exceeded the number of retries.");
			} else {
				user.setRetryCount(currentFailAttempts + 1);
				userRepository.save(user);
				throw new RecordNotFoundException("The username and password are invalid. Please try again.");
			}
		} else {
			throw new RecordNotFoundException("There is no account present with this username. Please try again.");
		}
	}

	public boolean isAccountLocked(String key) {
		Optional<User> userOpt = userRepository.findByUserName(key);
		if (userOpt.isPresent()) {
			return userOpt.get().getIsAccountLocked();
		} else
			throw new RecordNotFoundException("There is no account present with this username. Please try again.");
	}

	public void lockAccount(User user) {

		user.setIsAccountLocked(true);
		user.setLockTime(new Date());
		userRepository.save(user);

	}

	public boolean unlockAccount(User user) {
		long lockTimeInMillis = user.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();

		if (lockTimeInMillis + LOCK_DURATION < currentTimeInMillis) {
			user.setIsAccountLocked(false);
			user.setRetryCount(0);
			user.setLockTime(null);

			try {
				userRepository.save(user);
				return true;
			} catch (Exception e) {
				// throw new
				// RequestProcessingFailedException(Constants.ATTEMPT_DB_UPDATE_FAILED);
			}
		}
		return false;
	}

}
