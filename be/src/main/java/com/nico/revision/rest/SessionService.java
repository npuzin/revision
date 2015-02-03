package com.nico.revision.rest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpResponse;

import com.nico.revision.model.Session;
import com.nico.revision.model.User;


public class SessionService {
				
		
	
	public Session createSession(int userId) {
		
		EntityManager em = EMFactory.createEntityManager();
		em.getTransaction().begin();
		Session session = new Session();
		session.setCreationDate(new Timestamp(new Date().getTime()));
		session.setSessionId(UUID.randomUUID().toString());
		session.setUserId(userId);
		em.persist(session);
		em.getTransaction().commit();
		em.close();
		return session;
	}
	
	public Session getSession(String sessionId) {
		EntityManager em = EMFactory.createEntityManager();
		Session session = em.find(Session.class, sessionId);
		em.close();
		return session;
	}
	
}
