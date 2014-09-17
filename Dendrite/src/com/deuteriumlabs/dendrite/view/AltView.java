package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class AltView extends View {

	private static final String URL = "/alt";
	private static final String WEB_PG_TITLE_ATTR_NAME = "webPageTitle";
	private static final String WEB_PG_TITLE_ATTR_VAL = "Dendrite - Alternatives";

	private int pgNum;

	private void extractPgNumFromRequest() {
		final HttpServletRequest req = this.getRequest();
		final String p = req.getParameter("p");
		final PageId id = new PageId(p);
		int num = id.getNumber();
		setPgNum(num);
	}

	@Override
	String getUrl() {
		if (pgNum > 0) {
			return URL + "?p=" + pgNum;
		} else {
			return URL;
		}
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute(WEB_PG_TITLE_ATTR_NAME, WEB_PG_TITLE_ATTR_VAL);
		this.extractPgNumFromRequest();
	}

	private void setPgNum(final int num) {
		this.pgNum = num;
	}

	public int getPgNum() {
		return this.pgNum;
	}

	public class Alt {
		public String pgId;
		public int numLovers;
		public String summary;
		public List<String> tags;
		public String authorId;
		public String authorName;
		public int authorAvatarId;
	}

	public List<Alt> getAlts() {
		final List<Alt> alts = new ArrayList<Alt>();
		final int pgNum = this.getPgNum();
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

	private Alt getAlt(final PageId id) {
		final Alt alt = new Alt();
		alt.pgId = id.toString();

		final StoryPage pg = new StoryPage();
		pg.setId(id);
		pg.read();
		alt.numLovers = pg.getNumLovingUsers();
		alt.summary = pg.getLongSummary();
		alt.tags = pg.getTags();
		alt.authorId = pg.getAuthorId();
		alt.authorName = pg.getAuthorName();

		final User author = new User();
		author.setId(alt.authorId);
		author.read();
		alt.authorAvatarId = author.getAvatarId();
		return alt;
	}

	public void prepareAlt(final Alt alt) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("pgId", alt.pgId);
		pageContext.setAttribute("numLovers", alt.numLovers);
		pageContext.setAttribute("summary", alt.summary);
		pageContext.setAttribute("authorId", alt.authorId);
		pageContext.setAttribute("authorName", alt.authorName);
		pageContext.setAttribute("authorAvatarId", alt.authorAvatarId);
	}

	public void prepareTag(final String tag) {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute("tagClass", tag.toLowerCase());
		pageContext.setAttribute("tagName", tag.toUpperCase());
	}

	public boolean isPgNumInvalid() {
		return (pgNum == 0);
	}
}