/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class SubmitRewriteServlet extends SubmitServlet {
    private static final long serialVersionUID = 6369008865421800462L;

    private String authorId;
    private String authorName;
    private String content;
    private List<String> options;
    private String pageNumber;
    private HttpServletRequest req;
    private HttpSession session;

    public String getAuthorId() {
        return authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public HttpSession getSession() {
        return session;
    }

    @Override
    final public String getUrl() {
        return "/rewrite?p=" + pageNumber;
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setOptions(final List<String> options) {
        this.options = options;
    }

    public void setPageNumber(final String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setSession(final HttpSession session) {
        this.session = session;
    }

    private void handleReq(final User myUser) throws IOException {
        session = req.getSession();
        pageNumber = req.getParameter("pageNumber");
        content = req.getParameter("content");
        authorName = req.getParameter("authorName");
        options = new ArrayList<String>();
        for (int i = 0; i < 5; i++) {
            final String option = req.getParameter("option" + i);
            options.add(option);
        }
        authorId = req.getParameter("authorId");

        processRewrite(myUser);
    }

    @Override
    protected final void doPost(final HttpServletRequest req,
            final HttpServletResponse resp)
                    throws ServletException, IOException {
        this.req = req;
        setResponse(resp);
        final User myUser = User.getMyUser();
        handleReq(myUser);
    }

    void processRewrite(final User myUser) throws IOException {
        final SubmitRewriteController controller;
        controller = new SubmitRewriteController();
        controller.setSession(session);
        controller.startDraft();
        controller.setPageNumber(pageNumber);
        controller.setContent(content);
        controller.setAuthorName(authorName);
        final StoryPage parent = StoryPage.getParentOf(new PageId(pageNumber));
        controller.setParent(parent);
        for (final String option : options) {
            if (option != null) {
                controller.addOption(option);
            }
        }
        controller.setAuthorId(authorId);
        if (controller.isContentBlank()) {
            redirectFromBlankContent();
        } else if (controller.isContentTooLong()) {
            redirectFromTooLongContent();
        } else if (controller.isAnyOptionTooLong()) {
            redirectFromTooLongOption();
        } else if (controller.isAuthorNameBlank()) {
            redirectFromBlankAuthorName();
        } else if (controller.isAuthorNameTooLong()) {
            redirectFromTooLongAuthorName();
        } else {
            controller.buildNewPage(myUser);
            final PageId id = controller.getId();
            final HttpServletResponse resp = getResponse();
            resp.sendRedirect("/read?p=" + id);
        }
    }
}
