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


public class SecurityFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain)
    throws IOException, ServletException {
    	    	
    	HttpServletRequest req = (HttpServletRequest) request;
    	
    	if (req.getMethod() == "OPTIONS" || req.getPathInfo().startsWith("/login")) {
    		filterChain.doFilter(request, response);
    	} else {
    	
    		String userId = req.getHeader("User-Id");
        	HttpServletResponse resp = (HttpServletResponse) response;
    		if (userId != null) {	    			        	
	        	filterChain.doFilter(request, response);
    		} else {
    			resp.sendError(403);
    		}
    	}
    	
    }

    public void destroy() {
    }
}