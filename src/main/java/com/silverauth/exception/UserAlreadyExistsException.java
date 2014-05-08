package com.silverauth.exception;

@SuppressWarnings("serial")
public class UserAlreadyExistsException extends SilverAuthException{

	public UserAlreadyExistsException(String key, String message) {
		super(key, message);
	}	
}
