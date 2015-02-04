package com.nico.revision.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.nico.revision.model.Fiche;
import com.nico.revision.model.Matiere;
import com.nico.revision.model.Session;


@Path("/rest")
public class FicheService {
						
	
	@Path("/matiere/{matiereId}/fiches")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> getFiches(@Context HttpServletRequest request, @PathParam("matiereId") Integer matiereId) throws Exception {
		
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();		
		List<Fiche> fiches = em.createQuery("FROM Fiche where matiere=:matiere and matiere.user=:user", Fiche.class)
				.setParameter("matiere", new Matiere(matiereId))
				.setParameter("user", session.getUser())
				.getResultList();		
		em.close();
				
		return fiches; 
	}
	
	@Path("/fiche/update")
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> updateFiche(@Context HttpServletRequest request, Fiche fiche) throws Exception {
		
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();				
		Fiche ficheFromDb = em.find(Fiche.class, fiche.getGuid());
		checkAccess(ficheFromDb, session);
		ficheFromDb.setName(fiche.getName());
		em.getTransaction().begin();
		em.merge(ficheFromDb);
		em.getTransaction().commit();
		em.close();
				
		return getFiches(request, fiche.getMatiere().getId()); 
	}
	
	@Path("/fiche/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> deleteFiche(@Context HttpServletRequest request, Fiche fiche) throws Exception {
		
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();				
		Fiche ficheFromDb = em.find(Fiche.class, fiche.getGuid());
		checkAccess(ficheFromDb, session);
		
		em.getTransaction().begin();
		em.remove(ficheFromDb);
		em.getTransaction().commit();		
		em.close();
				
		return this.getFiches(request, fiche.getMatiere().getId());
	}	
	
	@Path("/fiche/add")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public Fiche addFiche(@Context HttpServletRequest request, Fiche fiche) throws Exception {
									
		Session session = (Session) request.getAttribute("session");
		
		checkAccess(fiche, session);
		
		EntityManager em = EMFactory.createEntityManager();				
		fiche.setGuid(UUID.randomUUID().toString());
		em.getTransaction().begin();
		em.persist(fiche);
		em.getTransaction().commit();		
		em.close();
				
		return fiche;
	}
	
	private void checkAccess(Matiere matiere, Session session) throws Exception {
		if (matiere.getUser().getId() != session.getUser().getId()) {
			throw new Exception("forbidden") ;
		}
	}
	
	private void checkAccess(Fiche fiche, Session session) throws Exception {
		if (fiche.getMatiere().getUser().getId() != session.getUser().getId()) {
			throw new Exception("forbidden") ;
		}
	}
	
	@Path("/fiche/{guid}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Fiche getFiche(@Context HttpServletRequest request, @PathParam("guid") String guid) throws Exception {
				
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();				
		Fiche fiche = em.createQuery("from Fiche where guid=:guid and matiere.user=:user", Fiche.class)
				.setParameter("guid", guid)
				.setParameter("user", session.getUser()).getSingleResult();
		em.close();
		
		return fiche;
	}
	
	@Path("/fiche")
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Fiche saveFiche(@Context HttpServletRequest request, Fiche fiche) throws Exception {
									
		EntityManager em = EMFactory.createEntityManager();				
		em.getTransaction().begin();
		em.merge(fiche);
		em.getTransaction().commit();
		em.close();
				
		return fiche;
	}
}
