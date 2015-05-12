/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

public abstract class FormView extends View {

    private String error;
    private HttpSession session;

    private String getError() {
        return this.error;
    }

    private boolean isThereAnError() {
        return (error != null && error.equals("") == false);
    }

    public boolean isAnOptionTooLong() {
        final String error = this.getError();
        return ("optionTooLong".equals(error));
    }

    public boolean isAuthorNameBlank() {
        final String error = this.getError();
        return ("blankAuthorName".equals(error));
    }

    public boolean isAuthorNameTooLong() {
        final String error = this.getError();
        return ("authorNameTooLong".equals(error));
    }

    public boolean isContentBlank() {
        final String error = this.getError();
        return ("blankContent".equals(error));
    }

    public boolean isContentTooLong() {
        final String error = this.getError();
        return ("contentTooLong".equals(error));
    }

    public boolean isTitleBlank() {
        final String error = this.getError();
        return ("blankTitle".equals(error));
    }

    public boolean isTitleTooLong() {
        final String error = this.getError();
        return ("titleTooLong".equals(error));
    }

    public void setError(final String error) {
        this.error = error;
    }

    public void setSession(final HttpSession session) {
        this.session = session;
    }

    public HttpSession getSession() {
        return this.session;
    }

    public boolean isDraftPending() {
        if (isThereAnError()) {
            final HttpSession session = this.getSession();
            final Boolean isDraftPendingAttribute;
            isDraftPendingAttribute = (Boolean) session.getAttribute("isDraftPending");
            final boolean isDraftPending;
            if (isDraftPendingAttribute == null) {
                isDraftPending = false;
            } else {
                isDraftPending = isDraftPendingAttribute.booleanValue();
            }
            return isDraftPending;
        }
        return false;
    }

    public void prepareDraftTitle() {
        final PageContext pageContext = this.getPageContext();
        final HttpSession session = this.getSession();
        final String title = (String) session.getAttribute("title");
        pageContext.setAttribute("draftTitle", title);
    }

    public void prepareDraftContent() {
        final PageContext pageContext = this.getPageContext();
        final HttpSession session = this.getSession();
        final String content = (String) session.getAttribute("content");
        pageContext.setAttribute("draftContent", content);
    }

    public void prepareDraftOption(final int optionNumber) {
        final PageContext pageContext = this.getPageContext();
        final HttpSession session = this.getSession();
        final String attributeName = "option" + optionNumber;
        final String option = (String) session.getAttribute(attributeName);
        if (option != null) {
            pageContext.setAttribute("draftOption", option);
        }
    }

}
