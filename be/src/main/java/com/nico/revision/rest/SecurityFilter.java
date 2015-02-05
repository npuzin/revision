package com.nico.revision.rest;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nico.revision.model.Session;


public class SecurityFilter implements Filter {

	
	public void init(FilterConfig filterConfig) throws ServletException {
    }

	public void destroy() {
    }
	
    private boolean authenticate(HttpServletRequest request, HttpServletResponse response)  {
    	
    	try {
	    	String sessionId = request.getHeader("Session-Id");
			if (sessionId == null) {
				return false;
			} else {
	    		SessionService srv = new SessionService();
	    		Session session = srv.getSession(sessionId);
	    		 
	    		request.setAttribute("session", session);
	        	return (session != null);
			}
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		return false;
    	}
    }
    
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
    throws IOException, ServletException {
    	    	    	
    	HttpServletRequest req = (HttpServletRequest) request;	
		HttpServletResponse resp = (HttpServletResponse) response;
		boolean isPublicUrl = req.getPathInfo().equals("/login") || req.getPathInfo().equals("/speed") ;
		
		if (isPublicUrl || authenticate(req, resp)) {
			filterChain.doFilter(request, response);
		} else {
			resp.sendError(403);
		}
	
    	
    }
	
}