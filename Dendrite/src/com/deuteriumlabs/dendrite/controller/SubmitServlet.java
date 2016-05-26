/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

public abstract class SubmitServlet extends DendriteServlet {
    private static final long serialVersionUID = -352599017961664562L;

    abstract public String getUrl();

    protected void redirectFromBlankAuthorName() {
        redirect(getUrl() + "&error=blankAuthorName");
    }

    protected void redirectFromBlankContent() {
        redirect(getUrl() + "&error=blankContent");
    }

    protected void redirectFromTooLongAuthorName() {
        redirect(getUrl() + "&error=authorNameTooLong");
    }

    protected void redirectFromTooLongContent() {
        redirect(getUrl() + "&error=contentTooLong");
    }

    protected void redirectFromTooLongOption() {
        redirect(getUrl() + "&error=optionTooLong");
    }
}
