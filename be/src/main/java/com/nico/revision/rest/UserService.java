package com.nico.revision.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpResponse;

import com.nico.revision.model.Session;
import com.nico.revision.model.User;


@Path("/rest")
public class UserService {
				
		
	
	@Path("/users")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<User> getUsers() throws Exception {
												
		EntityManager em = EMFactory.createEntityManager();	
		List<User> users = em.createQuery("FROM User", User.class).getResultList();		
		em.close();
				
		return users; 
	}
	
	@Path("/login")
	@POST
	@Consumes("application/json; charset=UTF-8")	
	@Produces("application/json; charset=UTF-8")
	public Session login(@Context HttpResponse response, User user) throws Exception {
		
		EntityManager em = EMFactory.createEntityManager();	
		User userInDb = em.createQuery("from User where email=:email", User.class).setParameter("email", user.getEmail()).getSingleResult();
		em.close();
		if (userInDb == null) {
			return null;
		} else {
			
			SessionService srv = new SessionService();
			Session session = srv.createSession(userInDb.getId());					
			return session; 
		}
		 
	}
	
}
