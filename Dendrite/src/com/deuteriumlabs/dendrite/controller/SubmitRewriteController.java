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
    public void buildNewPage(final User myUser) {
        super.buildNewPage(myUser);
        recalculateStoryQuality();
    }

    public void setPageNumber(final String pageNumber) {
        int pageNumberValue;
        try {
            pageNumberValue = Integer.parseInt(pageNumber);
        } catch (final NumberFormatException e) {
            pageNumberValue = 0;
        }
        if (pageNumberValue > 0) {
            this.pageNumber = pageNumberValue;
        } else {
            this.pageNumber = 0;
        }
    }

    private List<String> getAuthorsOfAltPgs() {
        final List<String> authorIds = new ArrayList<String>();
        final PageId pageId = new PageId();
        final int num = pageNumber;
        pageId.setNumber(num);
        final List<StoryPage> allVersions = StoryPage.getAllVersions(pageId);
        for (final StoryPage version : allVersions) {
            final String authorId = version.getAuthorId();

            if (authorId == null) {
                break;
            } else {
                final boolean isDuplicate = authorIds.contains(authorId);
                final String newAuthorId = getAuthorId();
                final boolean isNewAuthor = authorId.equals(newAuthorId);
                if ((isDuplicate == false) && (isNewAuthor == false)) {
                    authorIds.add(authorId);
                }
            }
        }
        return authorIds;
    }

    private PageId getBeginning() {
        final StoryPage alternative = new StoryPage();
        final PageId id = new PageId();
        final int number = pageNumber;
        id.setNumber(number);
        id.setVersion("a");
        alternative.setId(id);
        alternative.read();
        final PageId alternativeBeginning = alternative.getBeginning();
        final int altBeginningNumber = alternativeBeginning.getNumber();
        if (altBeginningNumber != number) {
            return alternativeBeginning;
        } else {
            return getId();
        }
    }

    private String getNextVersion() {
        final int count = StoryPage.countVersions(pageNumber);
        return StoryPage.convertNumberToVersion(count + 1);
    }

    private void notifyAuthorsOfAltPgs() {
        final List<String> altPgAuthorIds = getAuthorsOfAltPgs();
        for (final String altPgAuthorId : altPgAuthorIds) {
            final PgRewriteNotification notification;
            notification = new PgRewriteNotification();
            final PageId rewritePgId = getId();
            notification.setPgId(rewritePgId);
            final String rewriteAuthorId = getAuthorId();
            notification.setRewriteAuthorId(rewriteAuthorId);
            notification.setRecipientId(altPgAuthorId);
            notification.create();
        }
    }

    private void notifyFollowers(final User myUser) {
        if (myUser != null) {
            final List<String> followerIds = myUser.getFollowers();
            final List<String> altPgAuthorIds = getAuthorsOfAltPgs();
            if (followerIds != null) {
                for (final String followerId : followerIds) {
                    if (altPgAuthorIds.contains(followerId) == false) {
                        final FollowerRewriteNotification notification;
                        notification = new FollowerRewriteNotification();
                        notification.setPgId(getId());
                        notification.setAuthorId(getAuthorId());
                        notification.setAuthorName(getAuthorName());
                        notification.setRecipientId(followerId);
                        notification.create();
                    }
                }
            }
        }
    }

    private void setNextPageId() {
        final PageId id = new PageId();
        final int number = pageNumber;
        id.setNumber(number);
        final String version = getNextVersion();
        id.setVersion(version);
        setPageId(id);
    }

    @Override
    protected void setNewPageId() {
        setNextPageId();
    }

    @Override
    void buildStoryPage(final User myUser) {
        final StoryPage page = new StoryPage();
        addStoryPageValues(page);
        final PageId beginning = getBeginning();
        page.setBeginning(beginning);
        page.setParent(getParent());

        if (page.isInStore() == false) {
            page.create();
        }

        notifyAuthorsOfAltPgs();
        notifyFollowers(myUser);
    }
}
