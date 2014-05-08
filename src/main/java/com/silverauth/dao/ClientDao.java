package com.silverauth.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.silverauth.domain.Authority;
import com.silverauth.domain.Client;

@Repository
public class ClientDao {

	@PersistenceContext
    private EntityManager em;
	
	public void create(Authority authority) {	
		em.persist(authority);
	}
	
	public List<Client> findById(String clientId) {
		 TypedQuery<Client> query =
			      em.createQuery("FROM Client c Where c.clientId = '" + clientId + "'", Client.class);
		 
		 return query.getResultList();
	}
}
