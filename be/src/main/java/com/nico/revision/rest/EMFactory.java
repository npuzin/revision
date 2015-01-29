package com.nico.revision.rest;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.appengine.api.utils.SystemProperty;

public class EMFactory {

	public static EntityManagerFactory emf = null;
	

	private static EntityManagerFactory getEMF() {
		if (emf == null) {

			Map<String, String> properties = new HashMap<String, String>();
			
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
				
				properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.GoogleDriver");
				properties.put("javax.persistence.jdbc.url", "jdbc:google:mysql://npurevision:test2/toto?user=root");
				properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
			} else {
				
				// sql server database
				/*properties.put("javax.persistence.jdbc.driver", "com.microsoft.sqlserver.jdbc.SQLServerDriver");
				properties.put("javax.persistence.jdbc.url", "jdbc:sqlserver://localhost:1433;databaseName=JPA_EXAMPLE;integratedSecurity=false;");				
				properties.put("hibernate.dialect","org.hibernate.dialect.SQLServer2008Dialect");
				properties.put("hibernate.show_sql","true");
				properties.put("javax.persistence.jdbc.user","sa");
				properties.put("javax.persistence.jdbc.password","pass-1234");*/
				
				// google cloud sql database (mysql)
				properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
				properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
				properties.put("javax.persistence.jdbc.url","jdbc:mysql://173.194.251.164:3306/toto");				
				properties.put("hibernate.show_sql","true");
				properties.put("javax.persistence.jdbc.user","root");
				properties.put("javax.persistence.jdbc.password","passwordd");
				
			}

			emf = Persistence.createEntityManagerFactory("test", properties);
		}
		return emf;
	}

	public static EntityManager createEntityManager() {
		
		return getEMF().createEntityManager();
	}
}
