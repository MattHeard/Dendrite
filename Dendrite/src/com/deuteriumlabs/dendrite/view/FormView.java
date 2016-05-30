/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;

public abstract class FormView extends View {

    private String error;
    private HttpSession session;

    public HttpSession getSession() {
        return session;
    }

    public boolean isAnOptionTooLong() {
        final String error = getError();
        return ("optionTooLong".equals(error));
    }

    public boolean isAuthorNameBlank() {
        final String error = getError();
        return ("blankAuthorName".equals(error));
    }

    public boolean isAuthorNameTooLong() {
        final String error = getError();
        return ("authorNameTooLong".equals(error));
    }

    public boolean isContentBlank() {
        final String error = getError();
        return ("blankContent".equals(error));
    }

    public boolean isContentTooLong() {
        final String error = getError();
        return ("contentTooLong".equals(error));
    }

    public boolean isDraftPending() {
        if (isThereAnError()) {
            final HttpSession session = getSession();
            final Boolean isDraftPendingAttribute;
            isDraftPendingAttribute = (Boolean) session
                    .getAttribute("isDraftPending");
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

    public boolean isTitleBlank() {
        final String error = getError();
        return ("blankTitle".equals(error));
    }

    public boolean isTitleTooLong() {
        final String error = getError();
        return ("titleTooLong".equals(error));
    }

    public void prepareDraftContent() {
        final PageContext pageContext = getPageContext();
        final HttpSession session = getSession();
        final String content = (String) session.getAttribute("content");
        pageContext.setAttribute("draftContent", content);
    }

    public void prepareDraftOption(final int optionNumber) {
        final PageContext pageContext = getPageContext();
        final HttpSession session = getSession();
        final String attributeName = "option" + optionNumber;
        final String option = (String) session.getAttribute(attributeName);
        if (option != null) {
            pageContext.setAttribute("draftOption", option);
        }
    }

    public void prepareDraftTitle() {
        final PageContext pageContext = getPageContext();
        final HttpSession session = getSession();
        final String title = (String) session.getAttribute("title");
        pageContext.setAttribute("draftTitle", title);
    }

    public void setError(final String error) {
        this.error = error;
    }

    public void setSession(final HttpSession session) {
        this.session = session;
    }

    private String getError() {
        return error;
    }

    private boolean isThereAnError() {
        return ((error != null) && (error.equals("") == false));
    }

}
