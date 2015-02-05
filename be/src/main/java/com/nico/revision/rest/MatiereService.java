package com.nico.revision.rest;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.nico.revision.dao.MatiereDao;
import com.nico.revision.model.Matiere;
import com.nico.revision.model.Session;


@Path("/rest")
public class MatiereService {
				
		
	@Path("/matieres")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> getMatieres(@Context HttpServletRequest request) throws Exception {
							
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();		
		List<Matiere> matieres = MatiereDao.getMatieres(conn, session.getUser());	
		conn.close();				
		return matieres;
	}
	
	@Path("/matiere/{matiereId}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Matiere getMatiere(@Context HttpServletRequest request, @PathParam("matiereId") Integer matiereId) throws Exception {
												
		Session session = (Session) request.getAttribute("session");		
		Connection conn = ConnectionFactory.getNewConnection();
		Matiere matiere = new Matiere();
		matiere.setId(matiereId);
		matiere.setUser(session.getUser());
		matiere = MatiereDao.getMatiereById(conn, matiere);		
		conn.close(); 
				
		return matiere;
	}
	
	@Path("/matiere/update")
	@POST
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> updateMatiere(@Context HttpServletRequest request, Matiere matiere) throws Exception {
												
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();		
		matiere.setUser(session.getUser());
		MatiereDao.updateMatiere(conn, matiere);	 	
		conn.close();
				
		return getMatieres(request);
	}
	
	@Path("/matiere/add")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> addMatiere(@Context HttpServletRequest request, Matiere matiere) throws Exception {
											
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();		
		matiere.setUser(session.getUser());
		MatiereDao.insertMatiere(conn, matiere);		
		conn.close();
				
		return this.getMatieres(request);
	}
	
	
	@Path("/matiere/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> deleteMatiere(@Context HttpServletRequest request, Matiere matiere) throws Exception {
									
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();		
		matiere.setUser(session.getUser());
		MatiereDao.deleteMatiere(conn, matiere);		
		conn.close();
				
		return this.getMatieres(request);
	}	
	
	
}
