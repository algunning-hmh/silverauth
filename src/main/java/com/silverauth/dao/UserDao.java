package com.silverauth.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.silverauth.domain.User;

@Repository
public class UserDao {
	
	@PersistenceContext
    private EntityManager em;

	public void create(User user) {	
		em.persist(user);
	}
	
	public List<User> findByUsernameAndClientId(String username, String clientId) {
		 TypedQuery<User> query =
			      em.createQuery("FROM User u Where u.username = '" + username + "' and u.clientId = '" + clientId + "'", User.class);
		 
		 return query.getResultList();
	}
	
	public List<User> findByUsernameAndPasswordAndClientId(String username,String password, String clientId) {
		 TypedQuery<User> query =
			      em.createQuery("FROM User u Where u.username = '" + username + "' and u.clientId = '" + clientId + "' and" + "u.password = '" + password + "'", User.class);
		 
		 return query.getResultList();
	}
}
