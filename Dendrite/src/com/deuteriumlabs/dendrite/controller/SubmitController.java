/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;

public abstract class SubmitController {
    static final int MAX_EXPONENT = 30;

    private StoryPage parent;
    private HttpSession session;

    protected String authorId;
    protected String authorName;
    protected String content;
    protected PageId id;
    protected List<String> options;

    public SubmitController() {
        options = new ArrayList<String>();
    }

    public void addOption(final String option) {
        final List<String> options = getOptions();
        final int size = options.size();
        if (size < 5) {
            options.add(option);
            final HttpSession session = getSession();
            final String sessionAttributeName = "option" + size;
            session.setAttribute(sessionAttributeName, option);
        }
    }

    public void buildNewPage(final User myUser) {
        setNewPageId();
        buildStoryPage(myUser);
        buildStoryOptions();
        forgetDraft();
    }

    public PageId getId() {
        return id;
    }

    public boolean isAnyOptionTooLong() {
        final List<String> options = getOptions();
        for (final String option : options) {
            final int length = option.length();
            if (length > 80) {
                return true;
            }
        }
        return false;
    }

    public boolean isAuthorNameBlank() {
        final String authorName = getAuthorName();
        return ((authorName == null) || authorName.equals(""));
    }

    public boolean isAuthorNameTooLong() {
        final String authorName = getAuthorName();
        final int length = authorName.length();
        return (length > 100);
    }

    public boolean isContentBlank() {
        final String content = getContent();
        return ((content == null) || content.equals(""));
    }

    public boolean isContentTooLong() {
        final String content = getContent();
        final int length = content.length();
        return (length > 5000);
    }

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
        final HttpSession session = getSession();
        session.setAttribute("authorId", authorId);
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
        final HttpSession session = getSession();
        session.setAttribute("authorName", authorName);
    }

    public void setContent(final String content) {
        this.content = content;
        final HttpSession session = getSession();
        session.setAttribute("content", content);
    }

    public void setParent(final StoryPage parent) {
        this.parent = parent;
    }

    public void setPendingDraft(final boolean isPendingDraft) {
        final HttpSession session = getSession();
        session.setAttribute("isDraftPending", isPendingDraft);
    }

    public void setSession(final HttpSession session) {
        this.session = session;
    }

    public void startDraft() {
        setPendingDraft(true);
    }

    private PageId findUnallocatedPageId() {
        final PageId id = new PageId();
        final int number = findUnallocatedPageNumber();
        id.setNumber(number);
        final String version = "a";
        id.setVersion(version);
        return id;
    }

    protected void addStoryPageValues(final StoryPage page) {
        final PageId id = getId();
        page.setId(id);
        final String text = getContent();
        page.setText(text);
        final String authorName = getAuthorName();
        page.setAuthorName(authorName);
        final String authorId = getAuthorId();
        page.setAuthorId(authorId);
        final StoryPage parent = getParent();
        page.setParent(parent);
    }

    protected void buildStoryOptions() {
        final List<String> optionTexts = getOptions();
        int linkIndex = 0;
        int size = optionTexts.size();
        if (size > 5) {
            size = 5;
        }
        for (int i = 0; i < size; i++) {
            final String text = optionTexts.get(i);
            if ((text == null) || (text.length() == 0)) {
                continue;
            }
            final StoryOption option = new StoryOption();
            final PageId source = getId();
            option.setSource(source);
            option.setListIndex(linkIndex);
            option.setText(text);
            final boolean isInStore = option.isInStore();
            if (isInStore == false) {
                option.create();
            }
            linkIndex++;
        }
    }

    protected void forgetDraft() {
        setPendingDraft(false);
    }

    protected String getAuthorId() {
        return authorId;
    }

    protected String getAuthorName() {
        return authorName;
    }

    protected String getContent() {
        return content;
    }

    protected List<String> getOptions() {
        return options;
    }

    protected StoryPage getParent() {
        return parent;
    }

    protected HttpSession getSession() {
        return session;
    }

    protected void recalculateStoryQuality() {
        final PageId pgId = getId();
        final StoryPage pg = new StoryPage();
        pg.setId(pgId);
        pg.read();
        final StoryBeginning beginning = new StoryBeginning();
        final PageId beginningId = pg.getBeginning();
        beginning.setPageNumber(beginningId.getNumber());
        beginning.read();
        beginning.recalculateQuality();
        beginning.update();
    }

    protected void setNewPageId() {
        final PageId id = findUnallocatedPageId();
        setPageId(id);
    }

    protected void setPageId(final PageId id) {
        this.id = id;
    }

    abstract void buildStoryPage(final User myUser);

    int findUnallocatedPageNumber() {
        int exponent = 0;
        while (exponent <= MAX_EXPONENT) {
            final int randomNumber = generateRandomNumber(exponent);
            final boolean isUnallocated = isUnallocated(randomNumber);
            if (isUnallocated) {
                return randomNumber;
            } else if (exponent != MAX_EXPONENT) {
                exponent++;
            }
        }
        return -1; // Dead code
    }

    int generateRandomNumber(final int exponent) {
        final int POWER_BASE = 2;
        final double upper = Math.pow(POWER_BASE, exponent);
        final Random generator = new Random();
        final double randomDouble = generator.nextDouble();
        final double newNumber = (upper * randomDouble) + 1;
        return (int) newNumber;
    }

    boolean isUnallocated(final int candidate) {
        final PageId id = new PageId();
        id.setNumber(candidate);
        final String version = "a";
        id.setVersion(version);
        final StoryPage page = new StoryPage();
        page.setId(id);
        final boolean isInStore = page.isInStore();
        return (isInStore == false);
    }
}
