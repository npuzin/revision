package com.nico.revision.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.nico.revision.model.User;


@Path("/rest")
public class UserService {
				
	
	@Context org.jboss.resteasy.spi.HttpResponse response;
	
	@Path("/users")
	@GET
	@Produces("application/json")
	public List<User> getUsers() throws Exception {
						
		response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");				
		
		EntityManager em = EMFactory.createEntityManager();
		em.getTransaction().begin();
		List<User> users = em
		        .createQuery("FROM User", User.class)
		        .getResultList();
		em.getTransaction().commit();
		em.close();
		
		users.add(new User("hardcoded email"));
		return users;
	}
	
}
