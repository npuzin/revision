package com.nico.revision.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

 
public class App extends Application {
	HashSet<Object> singletons = new HashSet<Object>();
  
	public  App() {		
		singletons.add(new UserService());
		singletons.add(new MatiereService());
		singletons.add(new FicheService());
	}

	@Override
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>();
		return set;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}

}
