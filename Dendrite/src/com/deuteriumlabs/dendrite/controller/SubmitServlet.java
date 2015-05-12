/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

public abstract class SubmitServlet extends DendriteServlet {

    private static final long serialVersionUID = -352599017961664562L;

    abstract String getUrl();

    protected void redirectFromBlankAuthorName() {
        String url = getUrl();
        url += "&error=blankAuthorName";
        this.redirect(url);
    }

    protected void redirectFromBlankContent() {
        String url = getUrl();
        url += "&error=blankContent";
        this.redirect(url);
    }

    protected void redirectFromTooLongAuthorName() {
        String url = getUrl();
        url += "&error=authorNameTooLong";
        this.redirect(url);
    }

    protected void redirectFromTooLongContent() {
        String url = getUrl();
        url += "&error=contentTooLong";
        this.redirect(url);
    }

    protected void redirectFromTooLongOption() {
        String url = getUrl();
        url += "&error=optionTooLong";
        this.redirect(url);
    }
}
