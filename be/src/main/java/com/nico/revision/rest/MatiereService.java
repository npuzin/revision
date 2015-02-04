package com.nico.revision.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import com.nico.revision.model.Matiere;
import com.nico.revision.model.Session;


@Path("/rest")
public class MatiereService {
				
		
	@Path("/matieres")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> getMatieres(@Context HttpServletRequest request) throws Exception {
							
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();		
		List<Matiere> matieres = em.createQuery("FROM Matiere where user=:user", Matiere.class)
				.setParameter("user", session.getUser())
				.getResultList();		
		em.close();
				
		return matieres;
	}
	
	@Path("/matiere/{matiereId}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Matiere getMatiere(@Context HttpServletRequest request, @PathParam("matiereId") Integer matiereId) throws Exception {
												
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();		
		Matiere matiere = em.createQuery("FROM Matiere where user=:user and id=:id", Matiere.class)
				.setParameter("user", session.getUser())
				.setParameter("id", matiereId)
				.getSingleResult();		
		em.close();
				
		return matiere;
	}
	
	@Path("/matiere/update")
	@POST
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> updateMatiere(@Context HttpServletRequest request, Matiere matiere) throws Exception {
												
		
		EntityManager em = EMFactory.createEntityManager();	
		em.getTransaction().begin();
		em.merge(matiere);
		em.getTransaction().commit();
		em.close();
				
		return getMatieres(request);
	}
	
	@Path("/matiere/add")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> addMatiere(@Context HttpServletRequest request, Matiere matiere) throws Exception {
											
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();				
		matiere.setUser(session.getUser());
		em.getTransaction().begin();
		em.persist(matiere);
		em.getTransaction().commit();		
		em.close();
				
		return this.getMatieres(request);
	}
	
	
	@Path("/matiere/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> deleteMatiere(@Context HttpServletRequest request, Matiere matiere) throws Exception {
									
		Session session = (Session) request.getAttribute("session");
		
		EntityManager em = EMFactory.createEntityManager();				
		matiere.setUser(session.getUser());
		em.getTransaction().begin();
		em.createQuery("delete from Matiere where id=:id").setParameter("id", matiere.getId()).executeUpdate();
		em.getTransaction().commit();		
		em.close();
				
		return this.getMatieres(request);
	}	
	
	
}
