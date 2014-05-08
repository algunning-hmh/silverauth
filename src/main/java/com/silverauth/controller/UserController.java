package com.silverauth.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.silverauth.domain.User;
import com.silverauth.exception.InvalidClientException;
import com.silverauth.exception.UserAlreadyExistsException;
import com.silverauth.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	public UserService userService;
	
	private Gson gson = new GsonBuilder().create();

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public @ResponseBody void create(Principal principal, @RequestBody String json) throws UserAlreadyExistsException, InvalidClientException {
		
		User user = gson.fromJson(json, User.class);
		
		user.setClientId(principal.getName());
		
		userService.create(user);
	}
}
