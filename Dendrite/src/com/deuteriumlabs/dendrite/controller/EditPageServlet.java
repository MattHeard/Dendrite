package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class EditPageServlet extends HttpServlet {

	private static final long serialVersionUID = 6369008865421800462L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final EditPageController controller = new EditPageController();
		
		final String pageNumber = req.getParameter("pageNumber");
		controller.setPageNumber(pageNumber);
		
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
		
		controller.buildNewPage();
		
		final PageId id = controller.getId();
		
		resp.sendRedirect("/read.jsp?p=" + id);
	}
}
