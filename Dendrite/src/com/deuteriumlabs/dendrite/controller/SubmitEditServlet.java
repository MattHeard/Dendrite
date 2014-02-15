package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitEditServlet extends HttpServlet {

	private static final long serialVersionUID = 6369008865421800462L;
	private String pageNumber;
	private HttpServletResponse response;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final EditPageController controller = new EditPageController();
		
		final String pageNumber = req.getParameter("pageNumber");
		this.setPageNumber(pageNumber);
		controller.setPageNumber(pageNumber);
		
		final String content = req.getParameter("content");
		controller.setContent(content);
		
		final String authorName = req.getParameter("authorName");
		controller.setAuthorName(authorName);
		
		final boolean isContentValid = controller.isContentValid();
		final boolean isAuthorNameValid = controller.isAuthorNameValid();
		if (isContentValid == false)
			this.redirectFromInvalidContent();
		else if (isAuthorNameValid == false)
			this.redirectFromInvalidAuthorName();
		else {
			for (int i = 0; i < 5; i++) {
				final String option = req.getParameter("option" + i);
				if (option != null)
					controller.addOption(option);
			}
			
			final String authorId = req.getParameter("authorId");
			controller.setAuthorId(authorId);
			
			controller.buildNewPage();
			
			final PageId id = controller.getId();
			
			resp.sendRedirect("/read.jsp?p=" + id);
		}
	}

	private String getEditUrl() {
		final String p = this.getPageNumber();
		return "/edit.jsp?p=" + p;
	}

	private String getPageNumber() {
		return this.pageNumber;
	}

	private HttpServletResponse getResponse() {
		return this.response;
	}

	private void redirect(String url) {
		final HttpServletResponse response = this.getResponse();
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Find out what circumstances lead here.
			e.printStackTrace();
		}
	}

	private void redirectFromInvalidAuthorName() {
		String url = getEditUrl();
		url += "&error=blankAuthor";
		this.redirect(url);
	}

	private void redirectFromInvalidContent() {
		String url = getEditUrl();
		url += "&error=blankContent";
		this.redirect(url);
	}

	private void setPageNumber(final String pageNumber) {
		this.pageNumber = pageNumber;
	}

	private void setResponse(final HttpServletResponse response) {
		this.response = response;
	}
}
