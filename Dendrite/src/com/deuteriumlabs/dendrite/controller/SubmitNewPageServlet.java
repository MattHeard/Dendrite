package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitNewPageServlet extends HttpServlet {

	private static final long serialVersionUID = -1895973678482433819L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final SubmitNewPageController controller;
		controller = new SubmitNewPageController();
		
		final String from = req.getParameter("from");
		controller.setFrom(from);
		
		final String linkIndex = req.getParameter("linkIndex");
		controller.setLinkIndex(linkIndex);
		
		final String content = req.getParameter("content");
		controller.setContent(content);
		
		for (int i = 0; i < 5; i++) {
			final String option = req.getParameter("option" + i);
			if (option != null)
				controller.addOption(option);
		}
		
		controller.buildNewPage();
		
		controller.connectIncomingOption();
		
		final PageId id = controller.getId();
		
		resp.sendRedirect("/read.jsp?p=" + id);
	}

	
}
