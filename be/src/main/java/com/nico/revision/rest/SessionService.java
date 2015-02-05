package com.nico.revision.rest;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.nico.revision.dao.SessionDao;
import com.nico.revision.model.Session;
import com.nico.revision.model.User;



public class SessionService {
				
	
	public Session createSession(int userId) throws Exception {
				
		Connection conn = ConnectionFactory.getNewConnection();
		Session session = new Session();
		session.setCreationDate(new Timestamp(new Date().getTime()));
		session.setSessionId(UUID.randomUUID().toString());
		User u = new User();
		u.setId(userId);
		session.setUser(u);
		SessionDao.insertSession(conn, session);
		conn.close();
		return session;
	}
	
	public Session getSession(String sessionId) throws Exception {
		if (sessionId == null || sessionId.length() != 36) {
			return null;
		}
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		String cacheKey = "sessions/" + sessionId;
		Session session = (Session) cache.get(cacheKey);
		if (session != null) {
			return session;
		} else {
			Connection conn = ConnectionFactory.getNewConnection();
			session = SessionDao.getSession(conn, sessionId);
			if (session != null) {				
				cache.put(cacheKey, session, Expiration.byDeltaSeconds(300));
			}
			conn.close();
			return session;
		}
	}
	
}
