/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.User;

public class SubmitNewServlet extends SubmitServlet {
    private static final long serialVersionUID = -8415391685212281716L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setResponse(resp);
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
            redirectFromBlankTitle();
        else if (controller.isTitleTooLong() == true)
            redirectFromTooLongTitle();
        else if (controller.isContentBlank() == true)
            redirectFromBlankContent();
        else if (controller.isContentTooLong() == true)
            redirectFromTooLongContent();
        else if (controller.isAnyOptionTooLong() == true)
            redirectFromTooLongOption();
        else if (controller.isAuthorNameBlank() == true)
            redirectFromBlankAuthorName();
        else if (controller.isAuthorNameTooLong() == true)
            redirectFromTooLongAuthorName();
        else {
            final User myUser = User.getMyUser();
            controller.buildNewStory(myUser);
            final PageId id = controller.getId();
            resp.sendRedirect("/read?p=" + id);
        }
    }

    @Override
    public String getUrl() { return "/new"; }

    @Override
    protected void redirectFromBlankAuthorName() {
        redirect("/new?error=blankAuthorName");
    }

    @Override
    protected void redirectFromBlankContent() {
        redirect("/new?error=blankContent");
    }

    private void redirectFromBlankTitle() {
        redirect("/new?error=blankTitle");
    }

    @Override
    protected void redirectFromTooLongAuthorName() {
        redirect("/new?error=authorNameTooLong");
    }

    @Override
    protected void redirectFromTooLongContent() {
        redirect("/new?error=contentTooLong");
    }

    @Override
    protected void redirectFromTooLongOption() {
        redirect("/new?error=optionTooLong");
    }

    private void redirectFromTooLongTitle() {
        redirect("/new?error=titleTooLong");
    }
}
