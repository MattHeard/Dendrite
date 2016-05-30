/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class ContactView extends View {

    @Override
    public String getUrl() {
        return "/contact";
    }

    @Override
    public void initialise() {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite - Contact us");
        super.initialise();
    }

    @Override
    protected String getMetaDesc() {
        return "Contact Dendrite. We're here to answer any questions you might"
                + " have.";
    }
}
