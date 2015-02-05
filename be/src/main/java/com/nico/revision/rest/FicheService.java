package com.nico.revision.rest;

import java.sql.Connection;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.nico.revision.dao.FicheDao;
import com.nico.revision.dao.MatiereDao;
import com.nico.revision.model.Fiche;
import com.nico.revision.model.Matiere;
import com.nico.revision.model.Session;


@Path("/rest")
public class FicheService {
						
	
	@Path("/matiere/{matiereId}/fiches")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Matiere getFiches(@Context HttpServletRequest request, @PathParam("matiereId") Integer matiereId) throws Exception {
				
		Session session = (Session) request.getAttribute("session");
		
		Matiere matiere = new Matiere();
		matiere.setId(matiereId);
		matiere.setUser(session.getUser());
		Connection conn = ConnectionFactory.getNewConnection();
		matiere = MatiereDao.getMatiereById(conn, matiere);
		matiere.setFiches(FicheDao.getFiches(conn, matiere));
		conn.close();		
		
		return matiere;
	}
	
	@Path("/fiche/update")
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Matiere updateFiche(@Context HttpServletRequest request, Fiche fiche) throws Exception {
		
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();				
		fiche.getMatiere().setUser(session.getUser());				
		FicheDao.updateFiche(conn, fiche);		
		conn.close();
				
		return getFiches(request, fiche.getMatiere().getId()); 
	}
	
	@Path("/fiche/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public Matiere deleteFiche(@Context HttpServletRequest request, Fiche fiche) throws Exception {
				
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();				
		fiche.getMatiere().setUser(session.getUser());
		FicheDao.deleteFiche(conn, fiche);		
		conn.close();
				
		return this.getFiches(request, fiche.getMatiere().getId());
	}	
	
	@Path("/fiche/add")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public Fiche addFiche(@Context HttpServletRequest request, Fiche fiche) throws Exception {
														
		
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();
		fiche.setGuid(UUID.randomUUID().toString());
		fiche.getMatiere().setUser(session.getUser());			
		FicheDao.addFiche(conn, fiche);		
		conn.close();
				
		return fiche;
	}
	
	/*private void checkAccess(List<Fiche> fiches, User user) throws Exception {
		
		for (Fiche fiche : fiches) {
			if (fiche.getMatiere().getUser().getId() != user.getId()) {
				throw new Exception("forbidden") ;
			}
		}
	}
	
	private void checkAccess(Matiere matiere, User user) throws Exception {
		if (matiere.getUser().getId() != user.getId()) {
			throw new Exception("forbidden") ;
		}
	}
	
	private void checkAccess(Fiche fiche, User user) throws Exception {
		if (fiche.getMatiere().getUser().getId() != user.getId()) {
			throw new Exception("forbidden") ;
		}
	}*/
	
	@Path("/fiche/{guid}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Fiche getFiche(@Context HttpServletRequest request, @PathParam("guid") String guid) throws Exception {
						
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();
		Fiche fiche = new Fiche();
		fiche.setGuid(guid);
		Matiere matiere = new Matiere();
		matiere.setUser(session.getUser());
		fiche.setMatiere(matiere);
		fiche = FicheDao.getFicheByGuid(conn, fiche);
		conn.close();
		
		return fiche;
	}
	
	@Path("/fiche")
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Fiche updateFicheContent(@Context HttpServletRequest request, Fiche fiche) throws Exception {
								
		Session session = (Session) request.getAttribute("session");
		
		Connection conn = ConnectionFactory.getNewConnection();
		fiche.getMatiere().setUser(session.getUser());
		Fiche f = FicheDao.getFicheByGuid(conn, fiche);
		f.setContent(fiche.getContent());
		FicheDao.updateFicheContent(conn, f);
		conn.close();
		
		return fiche;
	}
}
