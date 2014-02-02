package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitNewStoryServlet extends HttpServlet {

	private static final long serialVersionUID = -8415391685212281716L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final SubmitNewStoryController controller;
		controller = new SubmitNewStoryController();
		
		final String title = req.getParameter("title");
		controller.setTitle(title);
		
		final String content = req.getParameter("content");
		controller.setContent(content);
		
		for (int i = 0; i < 5; i++) {
			final String option = req.getParameter("option" + i);
			if (option != null)
				controller.addOption(option);
		}
		
		controller.buildNewStory();
		
		final PageId id = controller.getId();
		
		resp.sendRedirect("/read.jsp?p=" + id);
	}

	
}
