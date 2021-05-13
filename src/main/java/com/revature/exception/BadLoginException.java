package com.revature.exception;

public class BadLoginException extends Exception {

	public BadLoginException() {
	}

	public BadLoginException(String message) {
		super(message);
	}

	public BadLoginException(Throwable cause) {
		super(cause);
	}

	public BadLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
