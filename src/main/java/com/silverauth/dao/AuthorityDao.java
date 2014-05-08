package com.silverauth.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.silverauth.domain.Authority;

@Repository
public class AuthorityDao {

	@PersistenceContext
    private EntityManager em;
	
	public void create(Authority authority) {	
		em.persist(authority);
	}
}
