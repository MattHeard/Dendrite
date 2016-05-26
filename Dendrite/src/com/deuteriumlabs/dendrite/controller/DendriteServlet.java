/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class DendriteServlet extends HttpServlet {
    private static final long serialVersionUID = -8346778276358190966L;
    
    private HttpServletResponse response;

    protected final HttpServletResponse getResponse() {
        return response;
    }

    protected final void redirect(final String url) {
        try {
            this.getResponse().sendRedirect(url);
        } catch (IOException e) {
            System.err.println("DendriteServlet.redirect failed.");
        }
    }

    protected final void setResponse(final HttpServletResponse r) {
        response = r;
    }
}
