package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class DendriteServlet extends HttpServlet {

	private static final long serialVersionUID = -8346778276358190966L;
	private HttpServletResponse response;

	protected HttpServletResponse getResponse() {
		return this.response;
	}

	protected void setResponse(final HttpServletResponse response) {
		this.response = response;
	}

	protected void redirect(String url) {
		final HttpServletResponse response = this.getResponse();
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Find out what circumstances lead here.
			e.printStackTrace();
		}
	}

}
