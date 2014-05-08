package com.silverauth.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.silverauth.dao.AuthorityDao;
import com.silverauth.dao.ClientDao;
import com.silverauth.dao.UserDao;
import com.silverauth.domain.Authority;
import com.silverauth.domain.User;
import com.silverauth.exception.InvalidClientException;
import com.silverauth.exception.UserAlreadyExistsException;

@Service
public class UserService {
	
	@Autowired
	public AuthorityDao authorityDao;
	
	@Autowired
	public UserDao userDao;
	
	@Autowired
	public ClientDao clientDao;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public void create(User user) throws UserAlreadyExistsException, InvalidClientException {
		
		List<User> users = userDao.findByUsernameAndClientId(user.getUsername(), user.getClientId());
		
		if (users.size() > 0)
			throw new UserAlreadyExistsException("invalid_user","Username already exists for this application.");
		
		String encodedPassword = encoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		user.setEnabled(true);
		
		userDao.create(user);
		
		Authority authority = new Authority();
		
		authority.setAuthority("role.user");
		authority.setUser(user);
		authority.setClientId(user.getClientId());
		
		authorityDao.create(authority);
	}
}
