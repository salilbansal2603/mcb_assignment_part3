package com.nagarro.userLogin.exception;

public class RetriesExceededException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RetriesExceededException(String message) {
		super(message);
	}

}
