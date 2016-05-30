/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class AltView extends View {

    public class Alt {
        public String authorAvatarDesc;
        public int authorAvatarId;
        public String authorId;
        public String authorName;
        public int numLovers;
        public String pgId;
        public String summary;
        public List<String> tags;
    }

    private static final String URL = "/alt";
    private static final String WEB_PG_TITLE_ATTR_NAME = "webPageTitle";

    private static final String WEB_PG_TITLE_ATTR_VAL = "Dendrite - Alternatives";

    private int pgNum;

    public List<Alt> getAlts() {
        final List<Alt> alts = new ArrayList<Alt>();
        final int pgNum = getPgNum();
        final int count = StoryPage.countVersions(pgNum);

        for (int i = 0; i < count; i++) {
            final String version = StoryPage.convertNumberToVersion(i + 1);
            final PageId id = new PageId();
            id.setNumber(pgNum);
            id.setVersion(version);
            final Alt alt = getAlt(id);
            alts.add(alt);
        }

        return alts;
    }

    public int getPgNum() {
        return pgNum;
    }

    @Override
    public String getUrl() {
        if (pgNum > 0) {
            return URL + "?p=" + pgNum;
        } else {
            return URL;
        }
    }

    @Override
    public void initialise() {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute(WEB_PG_TITLE_ATTR_NAME, WEB_PG_TITLE_ATTR_VAL);
        extractPgNumFromRequest();
        super.initialise();
    }

    public boolean isPgNumInvalid() {
        return (pgNum == 0);
    }

    public void prepareAlt(final Alt alt) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("pgId", alt.pgId);
        pageContext.setAttribute("numLovers", alt.numLovers);
        pageContext.setAttribute("summary", alt.summary);
        pageContext.setAttribute("authorId", alt.authorId);
        pageContext.setAttribute("authorName", alt.authorName);
        pageContext.setAttribute("authorAvatarId", alt.authorAvatarId);
        pageContext.setAttribute("authorAvatarDesc", alt.authorAvatarDesc);
    }

    public void prepareTag(final String tag) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("tagClass", tag.toLowerCase());
        pageContext.setAttribute("tagName", tag.toUpperCase());
    }

    private void extractPgNumFromRequest() {
        final HttpServletRequest req = getRequest();
        final String p = req.getParameter("p");
        final PageId id = new PageId(p);
        final int num = id.getNumber();
        setPgNum(num);
    }

    private Alt getAlt(final PageId id) {
        final Alt alt = new Alt();
        alt.pgId = id.toString();

        final StoryPage page = new StoryPage();
        page.setId(id);
        page.read();
        alt.numLovers = page.getNumLovingUsers();
        alt.summary = page.getLongSummary();
        alt.tags = page.getTags();
        alt.authorId = page.getAuthorId();
        alt.authorName = page.getAuthorName();

        if (alt.authorId != null) {
            final User author = new User();
            author.setId(alt.authorId);
            author.read();
            alt.authorAvatarId = author.getAvatarId();
            alt.authorAvatarDesc = User.getAvatarDesc(alt.authorAvatarId);
        } else {
            alt.authorAvatarId = 0;
            alt.authorAvatarDesc = User.getAvatarDesc(0);
        }

        return alt;
    }

    private void setPgNum(final int num) {
        pgNum = num;
    }

    @Override
    protected String getMetaDesc() {
        return "Select from all of the alternative versions of "
                + (isPgNumInvalid() ? "a page." : "page " + pgNum + ".");
    }
}