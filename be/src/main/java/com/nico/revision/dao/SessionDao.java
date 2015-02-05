package com.nico.revision.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.nico.revision.model.Session;
import com.nico.revision.model.User;

public class SessionDao {

	
	public static Session getSessionFromRS(ResultSet rs) throws Exception {
		Session session = new Session();
		session.setUser(new User());
		session.setSessionId(rs.getString("session_id"));
		session.getUser().setId(rs.getInt("user_id"));
		session.setCreationDate(rs.getTimestamp("creation_date"));
		return session;
	}
	
	public static Session getSession(Connection conn, String sessionId ) throws Exception {
		PreparedStatement query = conn.prepareStatement("select session_id,user_id,creation_date from sessions where session_id=?");
		query.setString(1, sessionId);
		ResultSet rs = query.executeQuery();
		if (rs.next()) {
			return getSessionFromRS(rs);
		} else {
			return null;
		}
	}
	
	public static Session insertSession(Connection conn, Session session ) throws Exception {
		PreparedStatement query = conn.prepareStatement("insert into sessions (session_id,user_id,creation_date) values (?,?,?)");
		query.setString(1, session.getSessionId());
		query.setInt(2, session.getUser().getId());
		query.setTimestamp(3, session.getCreationDate());
		query.executeUpdate();
		return session;
	}
}
