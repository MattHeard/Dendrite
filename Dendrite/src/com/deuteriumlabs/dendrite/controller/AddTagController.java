/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import com.deuteriumlabs.dendrite.model.AuthorTagNotification;
import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public class AddTagController {

    private PageId pageId;
    private String tag;

    public boolean addTag(final User myUser) {
        final StoryPage page = getStoryPage();
        final boolean isTagAdded = page.addTag(tag);
        if (isTagAdded) {
            page.update();
            final boolean isPageAuthorCurrentUser = isPageAuthorCurrentUser();
            if (isPageAuthorCurrentUser == false) {
                notifyAuthor(myUser);
            }
            return true;
        } else {
            return false;
        }
    }

    public void setPageId(final PageId pageId) {
        this.pageId = pageId;
    }

    public void setTag(final String tag) {
        this.tag = tag;
    }

    private String getAuthorId() {
        final StoryPage page = getStoryPage();
        return page.getAuthorId();
    }

    private StoryPage getStoryPage() {
        final StoryPage page = new StoryPage();
        page.setId(pageId);
        page.read();
        return page;
    }

    private String getTaggerId() {
        return User.getMyUserId();
    }

    private String getTaggerName(final User myUser) {
        if (User.isMyUserLoggedIn()) {
            return myUser.getDefaultPenName();
        } else {
            return null;
        }
    }

    private boolean isPageAuthorCurrentUser() {
        if (User.isMyUserLoggedIn()) {
            final String myUserId = User.getMyUserId();
            final String authorId = getAuthorId();
            return myUserId.equals(authorId);
        } else {
            return false;
        }
    }

    private void notifyAuthor(final User myUser) {
        final AuthorTagNotification notification = new AuthorTagNotification();
        notification.setPageId(pageId);
        notification.setTaggerId(getTaggerId());
        notification.setTaggerName(getTaggerName(myUser));
        notification.setTag(tag);
        notification.setRecipientId(getAuthorId());
        notification.create();
    }
}
