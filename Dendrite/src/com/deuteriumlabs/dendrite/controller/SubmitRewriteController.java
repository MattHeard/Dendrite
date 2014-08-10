package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.PgRewriteNotification;
import com.deuteriumlabs.dendrite.model.StoryPage;


public class SubmitRewriteController extends SubmitController {

    protected int pageNumber;

    @Override
    void buildStoryPage() {
        final StoryPage page = new StoryPage();
        this.addStoryPageValues(page);
        final PageId beginning = this.getBeginning();
        page.setBeginning(beginning);
        page.setParent(this.getParent());
        final boolean isInStore = page.isInStore();
        if (isInStore == false) {
            page.create();
        }
        this.notifyAboutExtension();
    }

    private void notifyAboutExtension() {
    	List<String> recipientIds = this.getNotificationRecipients();
    	for (final String recipientId : recipientIds) {
    		final PgRewriteNotification notification;
    		notification = new PgRewriteNotification();
    		final PageId rewritePgId = this.getId();
    		notification.setPgId(rewritePgId);
    		final String rewriteAuthorId = this.getAuthorId();
    		notification.setRewriteAuthorId(rewriteAuthorId);
    		notification.setRecipientId(recipientId);
    		notification.create();
    	}
	}

	private List<String> getNotificationRecipients() {
		final List<String> authorIds = new ArrayList<String>();
		final PageId pageId = new PageId();
		final int num = this.getPageNumber();
		pageId.setNumber(num);
		final List<StoryPage> allVersions = StoryPage.getAllVersions(pageId);
		for (final StoryPage version : allVersions) {
			final String authorId = version.getAuthorId();
			boolean isDuplicate = authorIds.contains(authorId);
			String newAuthorId = this.getAuthorId();
			boolean isNewAuthor = authorId.equals(newAuthorId);
			if (isDuplicate == false && isNewAuthor == false) {
				authorIds.add(authorId);
			}
		}
		return authorIds;
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

    private int getPageNumber() {
        return this.pageNumber;
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
