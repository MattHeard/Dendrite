package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitNewPageServlet extends HttpServlet {

	private static final long serialVersionUID = -1895973678482433819L;
	private String from;
	private String linkIndex;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final SubmitNewPageController controller;
		controller = new SubmitNewPageController();
		
		final String from = req.getParameter("from");
		this.setFrom(from);
		controller.setFrom(from);
		
		final String linkIndex = req.getParameter("linkIndex");
		this.setLinkIndex(linkIndex);
		controller.setLinkIndex(linkIndex);
		
		final String content = req.getParameter("content");
		controller.setContent(content);
		final boolean isBodyValid = controller.isContentValid();
		if (isBodyValid == false)
			this.redirectFromInvalidBody();
		else {
			for (int i = 0; i < 5; i++) {
				final String option = req.getParameter("option" + i);
				if (option != null)
					controller.addOption(option);
			}
			
			final String authorId = req.getParameter("authorId");
			controller.setAuthorId(authorId);
			
			final String authorName = req.getParameter("authorName");
			controller.setAuthorName(authorName);
			
			controller.buildNewPage();
			
			controller.connectIncomingOption();
			
			final PageId id = controller.getId();
			
			resp.sendRedirect("/read.jsp?p=" + id);
		}
	}

	private void setLinkIndex(final String linkIndex) {
		this.linkIndex = linkIndex;
	}

	private void setFrom(final String from) {
		this.from = from;
	}

	private void redirectFromInvalidBody() {
		final HttpServletResponse response = this.getResponse();
		final String from = this.getFrom();
		String url = "/write.jsp?from=" + from;
		final String linkIndex = this.getLinkIndex();
		url += "&linkIndex=" + linkIndex;
		url += "&error=blankContent";
		redirect(response, url);
	}

	private String getLinkIndex() {
		return this.linkIndex;
	}

	private String getFrom() {
		return this.from;
	}

	private HttpServletResponse getResponse() {
		return this.response;
	}
	
	private HttpServletResponse response;
	
	private static void redirect(final HttpServletResponse response,
			final String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Find out what circumstances lead here.
			e.printStackTrace();
		}
	}

	private void setResponse(final HttpServletResponse response) {
		this.response = response;
	}
}
