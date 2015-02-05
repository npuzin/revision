package com.nico.revision.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;

public class ConnectionFactory {

	public static Connection getNewConnection() throws Exception {
		
		String url; 
		Properties properties = new Properties(); 
		properties.put("javax.persistence.jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			url = "jdbc:google:mysql://npurevision:test2/revision?user=root";
		} else {
			url = "jdbc:sqlserver://localhost:1433;databaseName=revision;integratedSecurity=false;user=sa;password=pass-1234";
		}
		Connection conn = DriverManager.getConnection(url,properties);
		return conn;
	}
}
