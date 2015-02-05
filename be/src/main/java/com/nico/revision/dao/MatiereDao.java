package com.nico.revision.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.nico.revision.model.Matiere;
import com.nico.revision.model.User;

public class MatiereDao {

	public static Matiere getMatiereFromRS(ResultSet rs) throws Exception {
		Matiere matiere = new Matiere();
		User user = new User();
		user.setId(rs.getInt("user_id"));
		matiere.setColor(rs.getString("color"));
		matiere.setId(rs.getInt("id"));
		matiere.setName(rs.getString("name"));
		matiere.setUser(user);
		return matiere;
	}
	
	public static Matiere getMatiereById(Connection conn, Matiere matiere) throws Exception {
		PreparedStatement query = conn.prepareStatement("select id,user_id,name,color from matieres where id=? and user_id=?");
		query.setInt(1, matiere.getId());
		query.setInt(2, matiere.getUser().getId());
		ResultSet rs = query.executeQuery();
		if (rs.next()) {
			return getMatiereFromRS(rs);
		} else {
			return null;
		}
	}
	
	public static List<Matiere> getMatieres(Connection conn, User user ) throws Exception {
		PreparedStatement query = conn.prepareStatement("select id,user_id,name,color from matieres where user_id=?");
		query.setInt(1, user.getId());
		ResultSet rs = query.executeQuery();
		List<Matiere> matieres = new ArrayList<Matiere>();
		while (rs.next()) {
			matieres.add(getMatiereFromRS(rs));
		} 
		return matieres;
	}
	
	public static void deleteMatiere(Connection conn, Matiere matiere) throws Exception {
		 
		PreparedStatement query = conn.prepareStatement("delete matieres where id=? and user_id=?");		
		query.setInt(1, matiere.getId());
		query.setInt(2, matiere.getUser().getId());
		
		query.executeUpdate();
	}

	public static void insertMatiere(Connection conn, Matiere matiere) throws Exception {
	
		PreparedStatement query = conn.prepareStatement("insert into matieres (name,color,user_id) values (?,?,?)");
		query.setString(1, matiere.getName());
		query.setString(2, matiere.getColor());
		query.setInt(3, matiere.getUser().getId());		
		
		query.executeUpdate();
	}

	public static void updateMatiere(Connection conn, Matiere matiere) throws Exception {
		
		PreparedStatement query = conn.prepareStatement("update matieres set name=?,color=? where id=? and user_id=?");
		query.setString(1, matiere.getName());
		query.setString(2, matiere.getColor());
		query.setInt(3, matiere.getId());
		query.setInt(4, matiere.getUser().getId());
		
		query.executeUpdate();
		
	}
}
