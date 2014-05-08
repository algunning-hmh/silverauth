package com.silverauth.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

import com.silverauth.util.JsonMessage;

public class SilverAuthBasicAuthenticationEntryPoint extends BasicAuthenticationEntryPoint  {
	
	public SilverAuthBasicAuthenticationEntryPoint() {
		this.setRealmName("silver-auth");
	}

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
		 response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
         PrintWriter writer = response.getWriter();
         writer.println(JsonMessage.generate("invalid_client", "ClientId/secret is incorrect."));
	}

	
}
