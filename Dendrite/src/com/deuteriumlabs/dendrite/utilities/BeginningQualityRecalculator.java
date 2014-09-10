package com.deuteriumlabs.dendrite.utilities;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.deuteriumlabs.dendrite.model.StoryBeginning;

public class BeginningQualityRecalculator extends HttpServlet {

	private static final long serialVersionUID = 6079538980419092487L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.getWriter().println("Beginning quality recalculator");
		resp.getWriter().println("==============================");
		resp.getWriter().println();

		final List<StoryBeginning> allBeginnings;
		allBeginnings = StoryBeginning.getBeginnings(0, 1000);

		int count = 0;
		for (final StoryBeginning b : allBeginnings) {
			resp.getWriter().println("p" + b.getPageNumber());
			b.recalculateQuality();
			b.update();
			resp.getWriter().println("quality: " + b.getQuality());
			resp.getWriter().println();

			count++;
		}

		resp.getWriter().println(count + " stories recalculated.");
	}
}
