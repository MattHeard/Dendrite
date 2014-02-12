package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitNewStoryServlet extends HttpServlet {

	private static final long serialVersionUID = -8415391685212281716L;
	
	private static void redirect(final HttpServletResponse response,
			final String url) {
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Find out what circumstances lead here.
			e.printStackTrace();
		}
	}

	private HttpServletResponse response;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final SubmitNewStoryController controller;
		controller = new SubmitNewStoryController();
		
		final String title = req.getParameter("title");
		controller.setTitle(title);
		final String content = req.getParameter("content");
		controller.setContent(content);
		final String authorName = req.getParameter("authorName");
		controller.setAuthorName(authorName);
		
		final boolean isTitleValid = controller.isTitleValid();
		final boolean isBodyValid = controller.isContentValid();
		final boolean isAuthorNameValid = controller.isAuthorNameValid();
		if (isTitleValid == false)
			this.redirectFromInvalidTitle();
		else if (isBodyValid == false)
			this.redirectFromInvalidBody();
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
			
			controller.buildNewStory();
			
			final PageId id = controller.getId();
			
			resp.sendRedirect("/read.jsp?p=" + id);
		}
	}

	private HttpServletResponse getResponse() {
		return this.response;
	}

	private void redirectFromInvalidBody() {
		final HttpServletResponse response = this.getResponse();
		final String url = "/new.jsp?error=blankContent";
		redirect(response, url);
	}

	private void redirectFromInvalidTitle() {
		final HttpServletResponse response = this.getResponse();
		final String url = "/new.jsp?error=blankTitle";
		redirect(response, url);
	}

	private void redirectFromInvalidAuthorName() {
		final HttpServletResponse response = this.getResponse();
		final String url = "/new.jsp?error=blankAuthor";
		redirect(response, url);
	}

	private void setResponse(final HttpServletResponse response) {
		this.response = response;
	}
}
