package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitNewStoryServlet extends HttpServlet {

	private static final long serialVersionUID = -8415391685212281716L;
	private HttpServletResponse response;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.setResponse(resp);
		final SubmitNewStoryController controller;
		controller = new SubmitNewStoryController();
		
		final String title = req.getParameter("title");
		controller.setTitle(title);
		final boolean isTitleValid = controller.isTitleValid();
		if (isTitleValid == false)
			this.redirectFromInvalidTitle();
		else {	
			final String content = req.getParameter("content");
			controller.setContent(content);
			
			for (int i = 0; i < 5; i++) {
				final String option = req.getParameter("option" + i);
				if (option != null)
					controller.addOption(option);
			}
			
			final String authorId = req.getParameter("authorId");
			controller.setAuthorId(authorId);
			
			final String authorName = req.getParameter("authorName");
			controller.setAuthorName(authorName);
			
			controller.buildNewStory();
			
			final PageId id = controller.getId();
			
			resp.sendRedirect("/read.jsp?p=" + id);
		}
	}

	private HttpServletResponse getResponse() {
		return this.response;
	}

	private void redirectFromInvalidTitle() {
		final HttpServletResponse response = this.getResponse();
		final String url = "/new.jsp?error=blankTitle";
		try {
			response.sendRedirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void setResponse(final HttpServletResponse response) {
		this.response = response;
	}

	
}
