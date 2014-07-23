package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class SubmitWriteServlet extends SubmitServlet {

    private static final long serialVersionUID = -1895973678482433819L;
    private String from;
    private String linkIndex;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        this.setResponse(resp);
        final SubmitWriteController controller;
        controller = new SubmitWriteController();
        final HttpSession session = req.getSession();
        controller.setSession(session);
        controller.startDraft();
        final String from = req.getParameter("from");
        this.setFrom(from);
        controller.setFrom(from);
        final String linkIndex = req.getParameter("linkIndex");
        this.setLinkIndex(linkIndex);
        controller.setLinkIndex(linkIndex);
        final String content = req.getParameter("content");
        controller.setContent(content);
        final String authorName = req.getParameter("authorName");
        controller.setAuthorName(authorName);
        final StoryPage parent = new StoryPage();
        parent.setId(new PageId(from));
        parent.read();
        controller.setParent(parent);
        for (int i = 0; i < 5; i++) {
            final String option = req.getParameter("option" + i);
            if (option != null)
                controller.addOption(option);
        }
        final String authorId = req.getParameter("authorId");
        controller.setAuthorId(authorId);
        if (controller.isContentBlank() == true)
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
            controller.buildNewPage();
            controller.connectIncomingOption();
            final PageId id = controller.getId();
            resp.sendRedirect("/read?p=" + id);
        }
    }

    private String getFrom() {
        return this.from;
    }

    private String getLinkIndex() {
        return this.linkIndex;
    }

    @Override
    String getUrl() {
        final String from = this.getFrom();
        String url = "/write?from=" + from;
        final String linkIndex = this.getLinkIndex();
        url += "&linkIndex=" + linkIndex;
        return url;
    }

    private void setFrom(final String from) {
        this.from = from;
    }

    private void setLinkIndex(final String linkIndex) {
        this.linkIndex = linkIndex;
    }
}
