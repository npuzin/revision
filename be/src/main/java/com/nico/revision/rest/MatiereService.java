package com.nico.revision.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpResponse;

import com.nico.revision.model.Matiere;


@Path("/rest")
public class MatiereService {
				
		
	@Path("/matieres")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> getMatieres(@QueryParam("userId") Integer userId) throws Exception {
												
		
		EntityManager em = EMFactory.createEntityManager();		
		List<Matiere> matieres = em.createQuery("FROM Matiere where userId=:userId", Matiere.class)
				.setParameter("userId", userId)
				.getResultList();		
		em.close();
				
		return matieres;
	}
	
	@Path("/matiere/{matiereId}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Matiere getMatiere(@PathParam("matiereId") Integer matiereId, @QueryParam("userId") Integer userId) throws Exception {
												
		
		EntityManager em = EMFactory.createEntityManager();		
		Matiere matiere = em.createQuery("FROM Matiere where userId=:userId and id=:id", Matiere.class)
				.setParameter("userId", userId)
				.setParameter("id", matiereId)
				.getSingleResult();		
		em.close();
				
		return matiere;
	}
	
	@Path("/matiere/update")
	@POST
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> updateMatiere(Matiere matiere) throws Exception {
												
		
		EntityManager em = EMFactory.createEntityManager();	
		em.getTransaction().begin();
		em.merge(matiere);
		em.getTransaction().commit();
		em.close();
				
		return getMatieres(matiere.getUserId());
	}
	
	@Path("/matiere/add")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> addMatiere(@QueryParam("userId") Integer userId, Matiere matiere) throws Exception {
											
		
		EntityManager em = EMFactory.createEntityManager();				
		matiere.setUserId(userId);
		em.getTransaction().begin();
		em.persist(matiere);
		em.getTransaction().commit();		
		em.close();
				
		return this.getMatieres(userId);
	}
	
	
	@Path("/matiere/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> deleteMatiere(@QueryParam("userId") Integer userId, Matiere matiere) throws Exception {
									
		
		EntityManager em = EMFactory.createEntityManager();				
		matiere.setUserId(userId);
		em.getTransaction().begin();
		em.createQuery("delete from Matiere where id=:id").setParameter("id", matiere.getId()).executeUpdate();
		em.getTransaction().commit();		
		em.close();
				
		return this.getMatieres(userId);
	}	
	
	
}
