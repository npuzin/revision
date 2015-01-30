package com.nico.revision.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.spi.HttpResponse;

import com.nico.revision.model.Fiche;


@Path("/rest")
public class FicheService {
						
	
	@Path("/matiere/{matiereId}/fiches")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> getFiches(@Context HttpResponse response, @PathParam("matiereId") Integer matiereId) throws Exception {
						
		response.getOutputHeaders().putSingle("Access-Control-Allow-Origin", "*");				
		
		EntityManager em = EMFactory.createEntityManager();		
		List<Fiche> fiches = em.createQuery("FROM Fiche where matiereId=:matiereId", Fiche.class)
				.setParameter("matiereId", matiereId)
				.getResultList();		
		em.close();
				
		return fiches;
	}
	
}
