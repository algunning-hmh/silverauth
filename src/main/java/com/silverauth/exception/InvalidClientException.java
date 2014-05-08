package com.silverauth.exception;

@SuppressWarnings("serial")
public class InvalidClientException extends SilverAuthException{

	public InvalidClientException(String key, String message) {
		super(key, message);
	}	
}
