package com.nico.revision.rest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;

public class ConnectionFactory {

	public static Connection getNewConnection() throws Exception {
		
		String url; 
		Properties properties = new Properties(); 
		
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			Class.forName("com.mysql.jdbc.GoogleDriver");
			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
			url = "jdbc:google:mysql://npurevision:test2/revision?user=root";
		} else {
			//Class.forName("com.mysql.jdbc.GoogleDriver");
			//properties.put("javax.persistence.jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//url = "jdbc:sqlserver://localhost:1433;databaseName=revision;integratedSecurity=false;user=sa;password=pass-1234";
			Class.forName("com.mysql.jdbc.Driver");
			properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");			
			url = "jdbc:mysql://173.194.251.164:3306/revision?user=root&password=passwordd";			
		}
		
		Connection conn = DriverManager.getConnection(url,properties);
		return conn;
	}
}
