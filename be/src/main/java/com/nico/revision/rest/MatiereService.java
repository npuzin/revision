package com.nico.revision.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
	public List<Matiere> getMatieres(@Context HttpResponse response, @QueryParam("userId") Integer userId) throws Exception {
						
		response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");				
		
		EntityManager em = EMFactory.createEntityManager();		
		List<Matiere> matieres = em.createQuery("FROM Matiere where userId=:userId", Matiere.class)
				.setParameter("userId", userId)
				.getResultList();		
		em.close();
				
		return matieres;
	}
	
	@Path("/matiere/add")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Matiere> addMatiere(@Context HttpResponse response, @QueryParam("userId") Integer userId, Matiere matiere) throws Exception {
						
		response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");				
		
		EntityManager em = EMFactory.createEntityManager();		
		Matiere newMatiere = new Matiere();
		newMatiere.setName(matiere.getName());
		newMatiere.setColor(matiere.getColor());
		newMatiere.setUserId(userId);
		em.getTransaction().begin();
		em.persist(newMatiere);
		em.getTransaction().commit();		
		em.close();
				
		return this.getMatieres(response, userId);
	}
}
