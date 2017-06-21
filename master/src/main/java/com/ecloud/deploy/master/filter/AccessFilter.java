package com.ecloud.deploy.master.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.ecloud.deploy.master.common.MessageCache;

@WebFilter
public class AccessFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("===============AccessFilter init=============");
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("===============AccessFilter doFilter=============");
				
		if(MessageCache.isMaster){
			chain.doFilter(request, response);
		}else{
			PrintWriter out = response.getWriter();
			out.println("Current node status is standby.");
			out.flush();
			out.close();
		}
		
	}

	@Override
	public void destroy() {
		System.out.println("===============AccessFilter destroy=============");
		
	}

}
