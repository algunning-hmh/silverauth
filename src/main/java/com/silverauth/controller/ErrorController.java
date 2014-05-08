package com.silverauth.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.silverauth.exception.SilverAuthException;
import com.silverauth.util.JsonMessage;

@Controller
@ControllerAdvice
public class ErrorController {

	// 500 - Internal Server Error
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<String> handleException(Exception e) {
	
		return new ResponseEntity<String>("Erro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); // ResourceMessage.SOMETHING_WENT_WRONG + "\n" + e.getMessage() + "\n Host:" + System.getProperty("RDS_HOSTNAME");
	}
	
	@ExceptionHandler(SilverAuthException.class)
	@ResponseBody
	public ResponseEntity<String> handleException(SilverAuthException e) {

		String json = JsonMessage.generate(e.getKey(), e.getMessage());
		
		return new ResponseEntity<String>(json, HttpStatus.BAD_REQUEST);
	}
	
	// 404 - Page not found
	@RequestMapping("error/urlNotFound")
	@ResponseBody
	public ResponseEntity<String> handleException(HttpServletResponse response) throws Exception {
		if (response.getStatus() == 404)
			return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<String>("Unexpected error.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
