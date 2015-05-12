/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.PageId;

public class SubmitNewServlet extends SubmitServlet {

    private static final long serialVersionUID = -8415391685212281716L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.setResponse(resp);
        final SubmitNewController controller;
        controller = new SubmitNewController();
        final HttpSession session = req.getSession();
        controller.setSession(session);
        controller.startDraft();
        final String title = req.getParameter("title");
        controller.setTitle(title);
        final String content = req.getParameter("content");
        controller.setContent(content);
        final String authorName = req.getParameter("authorName");
        controller.setAuthorName(authorName);
        controller.setParent(null);
        for (int i = 0; i < 5; i++) {
            final String option = req.getParameter("option" + i);
            if (option != null) {
                controller.addOption(option);
            }
        }
        final String authorId = req.getParameter("authorId");
        controller.setAuthorId(authorId);
        if (controller.isTitleBlank() == true)
            this.redirectFromBlankTitle();
        else if (controller.isTitleTooLong() == true)
            this.redirectFromTooLongTitle();
        else if (controller.isContentBlank() == true)
            this.redirectFromBlankContent();
        else if (controller.isContentTooLong() == true)
            this.redirectFromTooLongContent();
        else if (controller.isAnyOptionTooLong() == true)
            this.redirectFromTooLongOption();
        else if (controller.isAuthorNameBlank() == true)
            this.redirectFromBlankAuthorName();
        else if (controller.isAuthorNameTooLong() == true)
            this.redirectFromTooLongAuthorName();
        else {
            controller.buildNewStory();
            final PageId id = controller.getId();
            resp.sendRedirect("/read?p=" + id);
        }
    }

    @Override
    String getUrl() { return "/new"; }

    @Override
    protected void redirectFromBlankAuthorName() {
        final String url = "/new?error=blankAuthorName";
        this.redirect(url);
    }

    @Override
    protected void redirectFromBlankContent() {
        final String url = "/new?error=blankContent";
        this.redirect(url);
    }

    private void redirectFromBlankTitle() {
        final String url = "/new?error=blankTitle";
        this.redirect(url);
    }

    @Override
    protected void redirectFromTooLongAuthorName() {
        final String url = "/new?error=authorNameTooLong";
        this.redirect(url);
    }

    @Override
    protected void redirectFromTooLongContent() {
        final String url = "/new?error=contentTooLong";
        this.redirect(url);
    }

    @Override
    protected void redirectFromTooLongOption() {
        final String url = "/new?error=optionTooLong";
        this.redirect(url);
    }

    private void redirectFromTooLongTitle() {
        final String url = "/new?error=titleTooLong";
        this.redirect(url);
    }
}
