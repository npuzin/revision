package com.nico.revision.rest;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.nico.revision.model.Session;
import com.nico.revision.model.User;



public class SessionService {
				
	
	public Session createSession(int userId) {
		
		EntityManager em = EMFactory.createEntityManager();
		em.getTransaction().begin();
		Session session = new Session();
		session.setCreationDate(new Timestamp(new Date().getTime()));
		session.setSessionId(UUID.randomUUID().toString());
		User u = new User();
		u.setId(userId);
		session.setUser(u);
		em.persist(session);
		em.getTransaction().commit();
		em.close();
		return session;
	}
	
	public Session getSession(String sessionId) {
		if (sessionId == null || sessionId.length() != 36) {
			return null;
		}
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		String cacheKey = "sessions/" + sessionId;
		Session session = (Session) cache.get(cacheKey);
		if (session != null) {
			return session;
		} else {
			EntityManager em = EMFactory.createEntityManager();
			session = em.find(Session.class, sessionId);
			if (session != null) {				
				cache.put(cacheKey, session, Expiration.byDeltaSeconds(300));
			}
			em.close();
			return session;
		}
	}
	
}
