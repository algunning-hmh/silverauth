package com.silverauth.exception;


@SuppressWarnings("serial")
public class SilverAuthException extends RuntimeException {

	private String key;

	public SilverAuthException(String key, String message) {
		super(message);
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
