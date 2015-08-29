/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.model.FollowerRewriteNotification;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.PgRewriteNotification;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class SubmitRewriteController extends SubmitController {

	protected int pageNumber;

	@Override
	public void buildNewPage() {
		super.buildNewPage();
		this.recalculateStoryQuality();
	}

	@Override
	void buildStoryPage() {
		final StoryPage page = new StoryPage();
		this.addStoryPageValues(page);
		final PageId beginning = this.getBeginning();
		page.setBeginning(beginning);
		page.setParent(this.getParent());

		if (page.isInStore() == false) {
			page.create();
		}

		this.notifyAuthorsOfAltPgs();
		this.notifyFollowers();
	}

	private PageId getBeginning() {
		final StoryPage alternative = new StoryPage();
		final PageId id = new PageId();
		final int number = this.getPageNumber();
		id.setNumber(number);
		id.setVersion("a");
		alternative.setId(id);
		alternative.read();
		final PageId alternativeBeginning = alternative.getBeginning();
		final int altBeginningNumber = alternativeBeginning.getNumber();
		if (altBeginningNumber != number) {
			return alternativeBeginning;
		} else {
			return this.getId();
		}
	}

	private String getNextVersion() {
		final int pageNumber = this.getPageNumber();
		final int count = StoryPage.countVersions(pageNumber);
		return StoryPage.convertNumberToVersion(count + 1);
	}

	private List<String> getAuthorsOfAltPgs() {
		final List<String> authorIds = new ArrayList<String>();
		final PageId pageId = new PageId();
		final int num = this.getPageNumber();
		pageId.setNumber(num);
		final List<StoryPage> allVersions = StoryPage.getAllVersions(pageId);
		for (final StoryPage version : allVersions) {
			final String authorId = version.getAuthorId();

			if (authorId == null) {
				break;
			} else {
				boolean isDuplicate = authorIds.contains(authorId);
				String newAuthorId = this.getAuthorId();
				boolean isNewAuthor = authorId.equals(newAuthorId);
				if (isDuplicate == false && isNewAuthor == false) {
					authorIds.add(authorId);
				}
			}
		}
		return authorIds;
	}

	private int getPageNumber() {
		return this.pageNumber;
	}

	private void notifyAuthorsOfAltPgs() {
		List<String> altPgAuthorIds = this.getAuthorsOfAltPgs();
		for (final String altPgAuthorId : altPgAuthorIds) {
			final PgRewriteNotification notification;
			notification = new PgRewriteNotification();
			final PageId rewritePgId = this.getId();
			notification.setPgId(rewritePgId);
			final String rewriteAuthorId = this.getAuthorId();
			notification.setRewriteAuthorId(rewriteAuthorId);
			notification.setRecipientId(altPgAuthorId);
			notification.create();
		}
	}

	private void notifyFollowers() {
		final User myUser = User.getMyUser();
		if (myUser != null) {
			List<String> followerIds = myUser.getFollowers();
			List<String> altPgAuthorIds = this.getAuthorsOfAltPgs();
			if (followerIds != null) {
				for (final String followerId : followerIds) {
					if (altPgAuthorIds.contains(followerId) == false) {
						final FollowerRewriteNotification notification;
						notification = new FollowerRewriteNotification();
						notification.setPgId(this.getId());
						notification.setAuthorId(this.getAuthorId());
						notification.setAuthorName(this.getAuthorName());
						notification.setRecipientId(followerId);
						notification.create();
					}
				}
			}
		}
	}

	@Override
	protected void setNewPageId() {
		this.setNextPageId();
	}

	private void setNextPageId() {
		final PageId id = new PageId();
		final int number = this.getPageNumber();
		id.setNumber(number);
		final String version = this.getNextVersion();
		id.setVersion(version);
		this.setPageId(id);
	}

	public void setPageNumber(final String pageNumber) {
		int pageNumberValue;
		try {
			pageNumberValue = Integer.parseInt(pageNumber);
		} catch (NumberFormatException e) {
			pageNumberValue = 0;
		}
		if (pageNumberValue > 0)
			this.pageNumber = pageNumberValue;
		else
			this.pageNumber = 0;
	}
}
