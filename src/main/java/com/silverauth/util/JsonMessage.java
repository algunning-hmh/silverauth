package com.silverauth.util;


public class JsonMessage {

	private static String templateError = "{\"error\":" + "\"{error}\"" + ",\"error_description\":" + "\"{error_description}\"}";
	
	public static String generate(String key, String description) {	
		return templateError.replace("{error}", key).replace("{error_description}", description);
	}
}
