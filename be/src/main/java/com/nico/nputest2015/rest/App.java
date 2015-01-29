package com.nico.nputest2015.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

 
public class App extends Application {
	HashSet<Object> singletons = new HashSet<Object>();
  
	public  App() {		
		singletons.add(new UserService());
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
