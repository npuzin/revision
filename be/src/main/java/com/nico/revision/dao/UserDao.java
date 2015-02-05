package com.nico.revision.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import com.nico.revision.model.User;

public class UserDao {

	
	public static User getUserFromRS(ResultSet rs) throws Exception {
		User user = new User();
		user.setId(rs.getInt("id"));				
		user.setEmail(rs.getString("email"));
		return user;
	}
	
	public static User getUserByEmail(Connection conn, User user) throws Exception {
		PreparedStatement query = conn.prepareStatement("select id,email from users where email=?");
		query.setString(1, user.getEmail());
		ResultSet rs = query.executeQuery();
		if (rs.next()) {
			return getUserFromRS(rs);
		} else {
			return null;
		}
	}

	public static List<User> getUsers(Connection conn) {
		
		return null;
	}
	
}
