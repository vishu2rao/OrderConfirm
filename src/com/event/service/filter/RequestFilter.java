package com.event.service.filter;


/**
 * @author Vishu Rao
 * 
 */

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.event.service.controller.ServiceConfirm;



public class RequestFilter implements Filter {

	//private Logger LOG = Logger.getLogger(RequestFilter.class.getCanonicalName());
	
	/**
	 * Logger for the class.
	 */
	private Logger LOG = Logger.getLogger(ServiceConfirm.class
			.getCanonicalName());


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOG.info("Inside the init() method of RequestFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {

		LOG.info("\n\nInside the doFilter method of RequestFilter");

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpResponse.addHeader("Access-Control-Allow-Origin", "*");
		httpResponse.addHeader("Access-Control-Allow-Methods",
				"GET, POST, PUT, DELETE");
		
		if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
			httpResponse.addHeader("Access-Control-Allow-Credentials", "true");
		}

		try{
		chain.doFilter(request, response);
		} catch (Exception e){
			LOG.info("Exception occured : "+e);
			request.setAttribute("errorMessage", e);
		}	
	}

	@Override
	public void destroy() {
		LOG.info("Inside destroy() method of RequestFilter");
	}
}
