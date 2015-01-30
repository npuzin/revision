package com.nico.revision.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpResponse;

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
	
}
