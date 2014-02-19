package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public abstract class SubmitServlet extends HttpServlet {

	private static final long serialVersionUID = -352599017961664562L;
	private HttpServletResponse response;

	protected HttpServletResponse getResponse() {
		return this.response;
	}

	abstract String getUrl();

	protected void redirect(String url) {
		final HttpServletResponse response = this.getResponse();
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Find out what circumstances lead here.
			e.printStackTrace();
		}
	}
	
	protected void redirectFromBlankAuthorName() {
		String url = getUrl();
		url += "&error=blankAuthorName";
		this.redirect(url);
	}

	protected void redirectFromBlankContent() {
		String url = getUrl();
		url += "&error=blankContent";
		this.redirect(url);
	}

	protected void redirectFromTooLongAuthorName() {
		String url = getUrl();
		url += "&error=authorNameTooLong";
		this.redirect(url);
	}

	protected void redirectFromTooLongContent() {
		String url = getUrl();
		url += "&error=contentTooLong";
		this.redirect(url);
	}
	
	protected void redirectFromTooLongOption() {
		String url = getUrl();
		url += "&error=optionTooLong";
		this.redirect(url);
	}
	
	protected void setResponse(final HttpServletResponse response) {
		this.response = response;
	}
}
