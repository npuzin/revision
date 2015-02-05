package com.nico.revision.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nico.revision.model.Fiche;
import com.nico.revision.model.Matiere;

public class FicheDao {

	public static Fiche getFicheFromRS(ResultSet rs) throws Exception {
		Fiche fiche = new Fiche();		
		Matiere matiere = new Matiere();
		matiere.setId(rs.getInt("matiere_id"));
		fiche.setGuid(rs.getString("guid"));
		fiche.setName(rs.getString("name"));
		fiche.setMatiere(matiere);
		fiche.setContent(rs.getString("content"));
		return fiche;
	}
	
	public static Fiche getFicheByGuid(Connection conn, Fiche fiche) throws Exception {
		PreparedStatement query = conn.prepareStatement("select f.guid,f.matiere_id,f.name, f.content from fiches f inner join matieres m on m.id = f.matiere_id where f.guid=? and m.user_id=?");
		query.setString(1, fiche.getGuid());
		query.setInt(2, fiche.getMatiere().getUser().getId());
		ResultSet rs = query.executeQuery();
		if (rs.next()) {
			return getFicheFromRS(rs);
		} else {
			return null;
		}
	}
	
	public static List<Fiche> getFiches(Connection conn, Matiere matiere) throws Exception {
		PreparedStatement query = conn.prepareStatement("select guid,matiere_id,name, null as content from fiches where matiere_id=? order by name");
		query.setInt(1, matiere.getId());
		ResultSet rs = query.executeQuery();
		List<Fiche> fiches = new ArrayList<Fiche>();
		while (rs.next()) {
			fiches.add(getFicheFromRS(rs));
		} 
		return fiches;
	}

	public static void updateFicheContent(Connection conn, Fiche fiche) throws Exception {
		
		PreparedStatement query = conn.prepareStatement("update fiches set content=? where guid = ?");		
		query.setString(1, fiche.getContent());
		query.setString(2, fiche.getGuid());		
		query.executeUpdate();
	}

	public static void addFiche(Connection conn, Fiche fiche) throws Exception {
				
		PreparedStatement query = conn.prepareStatement("insert into fiches (guid,name,matiere_id) values(?,?,?)");		
		query.setString(1, fiche.getGuid());
		query.setString(2, fiche.getName());
		query.setInt(3, fiche.getMatiere().getId());
		query.executeUpdate();
	}

	public static void deleteFiche(Connection conn, Fiche fiche) throws Exception {
		Fiche f = getFicheByGuid(conn, fiche);
			
		PreparedStatement query = conn.prepareStatement("delete from fiches where guid=?");		
		query.setString(1, f.getGuid());
		query.executeUpdate();
	}

	public static Fiche updateFiche(Connection conn, Fiche fiche) throws Exception {
		Fiche f = getFicheByGuid(conn, fiche);
		f.setName(fiche.getName());
		
		PreparedStatement query = conn.prepareStatement("update fiches set name=? where guid=?");
		query.setString(1, f.getName());
		query.setString(2, f.getGuid());
		query.executeUpdate();
		return f;
	}
}
