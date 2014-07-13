package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles HTTP requests and responses for Dendrite servlets.
 */
public class DendriteServlet extends HttpServlet {

    /**
     * Serialises the class state.
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = -8346778276358190966L;

    /**
     * The HTTP response.
     */
    private HttpServletResponse response;

    /**
     * Returns the HTTP response.
     * @return the HTTP response
     */
    protected final HttpServletResponse getResponse() {
        return this.response;
    }

    /**
     * Sets the HTTP response to redirect the client to the given URL.
     * @param url The redirect target
     */
    protected final void redirect(final String url) {
        try {
            this.getResponse().sendRedirect(url);
        } catch (IOException e) {
            System.err.println("DendriteServlet.redirect failed.");
        }
    }

    /**
     * Sets the HTTP response.
     * @param r the HTTP response
     */
    protected final void setResponse(final HttpServletResponse r) {
        this.response = r;
    }

}
