package com.ecloud.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.core.env.Environment;

@WebFilter(filterName = "accessFilter", urlPatterns = "/*")
public class AccessFilter implements Filter {
	
	protected Environment env;

	public AccessFilter(Environment env) {
		this.env = env;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		System.out.println("===============AccessFilter destroy=============");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("===============AccessFilter init=============");

	}
	

}
