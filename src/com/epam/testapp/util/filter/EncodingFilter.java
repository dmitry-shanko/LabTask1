package com.epam.testapp.util.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

import com.epam.testapp.util.GlobalConstants;

public class EncodingFilter implements Filter 
{
	private String encoding;
	private static final Logger log = Logger.getLogger(EncodingFilter.class);

	public void init(FilterConfig fConfig) throws ServletException 
	{
		encoding = fConfig.getInitParameter(GlobalConstants.ENCODING_PARAM.getContent());
	}

	public void destroy() 
	{
		encoding = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
		String encodingRequest = request.getCharacterEncoding();
		if (null != encoding && !encoding.equalsIgnoreCase(encodingRequest)) 
		{
			request.setCharacterEncoding(encoding);
		}
		try
		{
			chain.doFilter(request, response);
		}
		catch (Exception e)
		{
			log.error(e);
			e.printStackTrace();
			throw e;
		}
	}

}
