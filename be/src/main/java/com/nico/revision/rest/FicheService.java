package com.nico.revision.rest;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.nico.revision.model.Fiche;


@Path("/rest")
public class FicheService {
						
	
	@Path("/matiere/{matiereId}/fiches")
	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> getFiches(@PathParam("matiereId") Integer matiereId) throws Exception {
												
		
		EntityManager em = EMFactory.createEntityManager();		
		List<Fiche> fiches = em.createQuery("select new com.nico.revision.model.Fiche(guid,matiereId,name) FROM Fiche where matiereId=:matiereId", Fiche.class)
				.setParameter("matiereId", matiereId)
				.getResultList();		
		em.close();
				
		return fiches; 
	}
	
	@Path("/fiche/update")
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> updateFiche(Fiche fiche) throws Exception {
												
		
		EntityManager em = EMFactory.createEntityManager();		
		em.getTransaction().begin();
		em.merge(fiche);
		em.getTransaction().commit();
		em.close();
				
		return getFiches(fiche.getMatiereId()); 
	}
	
	@Path("/fiche/delete")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> deleteFiche(Fiche fiche) throws Exception {
									
		int matiereId = fiche.getMatiereId();
		EntityManager em = EMFactory.createEntityManager();				
		em.getTransaction().begin();
		em.createQuery("delete from Fiche where guid=:guid").setParameter("guid", fiche.getGuid()).executeUpdate();
		em.getTransaction().commit();		
		em.close();
				
		return this.getFiches(matiereId);
	}	
	
	@Path("/fiche/add")
	@POST
	@Consumes("application/json")
	@Produces("application/json; charset=UTF-8")
	public List<Fiche> addFiche(Fiche fiche) throws Exception {
									
		EntityManager em = EMFactory.createEntityManager();				
		fiche.setGuid(UUID.randomUUID().toString());
		em.getTransaction().begin();
		em.persist(fiche);
		em.getTransaction().commit();		
		em.close();
				
		return this.getFiches(fiche.getMatiereId());
	}
	
	@Path("/fiche/{guid}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public Fiche getFiche(@PathParam("guid") String guid) throws Exception {
									
		EntityManager em = EMFactory.createEntityManager();				
		Fiche fiche = em.find(Fiche.class, guid);
		em.close();
				
		return fiche;
	}
	
	@Path("/fiche")
	@POST
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Fiche saveFiche(Fiche fiche) throws Exception {
									
		EntityManager em = EMFactory.createEntityManager();				
		em.getTransaction().begin();
		em.merge(fiche);
		em.getTransaction().commit();
		em.close();
				
		return fiche;
	}
}
