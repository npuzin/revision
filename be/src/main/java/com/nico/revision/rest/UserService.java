package com.nico.revision.rest;

import java.sql.Connection;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpResponse;

import com.nico.revision.dao.UserDao;
import com.nico.revision.model.Session;
import com.nico.revision.model.User;


@Path("/rest")
public class UserService {
				
		
	
	@Path("/users")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<User> getUsers() throws Exception {
												
		Connection conn = ConnectionFactory.getNewConnection();	
		List<User> users = UserDao.getUsers(conn);	
		conn.close();
				
		return users; 
	}
	
	@Path("/login")
	@POST
	@Consumes("application/json; charset=UTF-8")	
	@Produces("application/json; charset=UTF-8")
	public Session login(@Context HttpResponse response, User user) throws Exception {
		
		Connection conn = ConnectionFactory.getNewConnection();	
		User userInDb = UserDao.getUserByEmail(conn, user);		
		conn.close();
		if (userInDb == null) {
			return null;
		} else {
			
			SessionService srv = new SessionService();
			Session session = srv.createSession(userInDb.getId());					
			return session; 
		}
		 
	}
	
}
