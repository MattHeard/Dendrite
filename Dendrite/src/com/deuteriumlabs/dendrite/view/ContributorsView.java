/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class ContributorsView extends View {

    @Override
    public String getUrl() {
        return "/contributors";
    }

    @Override
    public void initialise() {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite - Contributors");
    }
}
