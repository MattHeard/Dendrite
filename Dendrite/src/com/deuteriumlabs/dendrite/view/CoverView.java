/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import javax.servlet.jsp.PageContext;

public class CoverView extends View {
    public CoverView(final PageContext pageContext) {
        this.pageContext = pageContext;
    }

    public String[] getTags() {
        final String[] tags = { "ABSURD", "ACTION", "ADVENTURE", "COMEDY",
                "CRIME", "DRAMA", "EROTIC", "FANFIC", "FANTASY", "HISTORICAL",
                "HORROR", "INSPIRATIONAL", "MYSTERY", "POLITICAL", "REAL-LIFE",
                "RELIGIOUS", "ROMANCE", "SCIFI", "SPAM", "THRILLER", "WESTERN",
                "YOUNG-ADULT" };
        return tags;
    }

    @Override
    public String getUrl() {
        return "/cover";
    }

    @Override
    public void initialise() {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("webPageTitle", "Dendrite");
    }

    public void prepareTagName(final String tag) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("tagName", tag);
    }
}
