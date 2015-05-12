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

public abstract class SubmitController {

    static final int MAX_EXPONENT = 30;
    protected String authorId;
    protected String authorName;
    protected String content;
    protected PageId id;
    protected List<String> options;
    private HttpSession session;
    private StoryPage parent;

    public SubmitController() {
        this.options = new ArrayList<String>();
    }

    public void addOption(final String option) {
        final List<String> options = this.getOptions();
        final int size = options.size();
        if (size < 5) {
            options.add(option);
            final HttpSession session = this.getSession();
            final String sessionAttributeName = "option" + size;
            session.setAttribute(sessionAttributeName, option);
        }
    }

    /**
     * 
     */
    protected void addStoryPageValues(final StoryPage page) {
        final PageId id = this.getId();
        page.setId(id);
        final String text = this.getContent();
        page.setText(text);
        final String authorName = this.getAuthorName();
        page.setAuthorName(authorName);
        final String authorId = this.getAuthorId();
        page.setAuthorId(authorId);
        final StoryPage parent = this.getParent();
        page.setParent(parent);
    }

    /**
     * @return
     */
    protected StoryPage getParent() {
        return this.parent;
    }
    
    protected void recalculateStoryQuality() {
    	final PageId pgId = this.getId();
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

    public void buildNewPage() {
        this.setNewPageId();
        this.buildStoryPage();
        this.buildStoryOptions();
        this.forgetDraft();
    }

    protected void buildStoryOptions() {
        final List<String> optionTexts = this.getOptions();
        int linkIndex = 0;
        int size = optionTexts.size();
        if (size > 5) {
        	size = 5;
        }
		for (int i = 0; i < size; i++) {
            final String text = optionTexts.get(i);
            if (text == null || text.length() == 0) {
                continue;
            }
            final StoryOption option = new StoryOption();
            final PageId source = this.getId();
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

    abstract void buildStoryPage();

    private PageId findUnallocatedPageId() {
        final PageId id = new PageId();
        final int number = this.findUnallocatedPageNumber();
        id.setNumber(number);
        final String version = "a";
        id.setVersion(version);
        return id;
    }

    int findUnallocatedPageNumber() {
        int exponent = 0;
        while (exponent <= MAX_EXPONENT) {
            int randomNumber = generateRandomNumber(exponent);
            final boolean isUnallocated = isUnallocated(randomNumber);
            if (isUnallocated)
                return randomNumber;
            else if (exponent != MAX_EXPONENT)
                exponent++;
        }
        return -1; // Theoretically unreachable.
    }

    protected void forgetDraft() {
        this.setPendingDraft(false);
    }

    int generateRandomNumber(final int exponent) {
        final int POWER_BASE = 2;
        double upper = Math.pow(POWER_BASE, exponent);
        Random generator = new Random();
        double randomDouble = generator.nextDouble();
        double newNumber = (upper * randomDouble) + 1;
        return (int) newNumber;
    }

    protected String getAuthorId() {
        return this.authorId;
    }

    protected String getAuthorName() {
        return this.authorName;
    }

    protected String getContent() {
        return this.content;
    }

    public PageId getId() {
        return this.id;
    }

    protected List<String> getOptions() {
        return this.options;
    }

    protected HttpSession getSession() {
        return this.session;
    }

    public boolean isAnyOptionTooLong() {
        final List<String> options = this.getOptions();
        for (final String option : options) {
            final int length = option.length();
            if (length > 80)
                return true;
        }
        return false;
    }

    public boolean isAuthorNameBlank() {
        final String authorName = this.getAuthorName();
        return (authorName == null || authorName.equals(""));
    }

    public boolean isAuthorNameTooLong() {
        final String authorName = this.getAuthorName();
        final int length = authorName.length();
        return (length > 100);
    }

    public boolean isContentBlank() {
        final String content = this.getContent();
        return (content == null || content.equals(""));
    }

    public boolean isContentTooLong() {
        final String content = this.getContent();
        final int length = content.length();
        return (length > 5000);
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

    public void setAuthorId(final String authorId) {
        this.authorId = authorId;
        final HttpSession session = this.getSession();
        session.setAttribute("authorId", authorId);
    }

    public void setAuthorName(final String authorName) {
        this.authorName = authorName;
        final HttpSession session = this.getSession();
        session.setAttribute("authorName", authorName);
    }

    public void setContent(final String content) {
        this.content = content;
        final HttpSession session = this.getSession();
        session.setAttribute("content", content);
    }

    protected void setNewPageId() {
        final PageId id = this.findUnallocatedPageId();
        this.setPageId(id);
    }

    protected void setPageId(final PageId id) {
        this.id = id;
    }

    public void setPendingDraft(final boolean isPendingDraft) {
        final HttpSession session = this.getSession();
        session.setAttribute("isDraftPending", isPendingDraft);
    }

    public void setSession(final HttpSession session) {	
        this.session = session;
    }

    public void startDraft() {
        this.setPendingDraft(true);
    }

    public void setParent(final StoryPage parent) {
        this.parent = parent;
    }
}
