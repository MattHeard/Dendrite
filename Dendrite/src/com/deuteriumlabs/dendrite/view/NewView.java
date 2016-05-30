/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public class NewView extends FormView {

    @Override
    public String getUrl() {
        return "/new";
    }

    @Override
    public void initialise() {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite - New Story");
        final HttpServletRequest request = getRequest();
        final String error = request.getParameter("error");
        setError(error);

        super.initialise();
    }

    @Override
    protected String getMetaDesc() {
        return "Write your own adventure and share it with friends.";
    }
}
