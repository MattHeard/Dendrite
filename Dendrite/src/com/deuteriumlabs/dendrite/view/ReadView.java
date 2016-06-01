/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.PageId;
import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryOption;
import com.deuteriumlabs.dendrite.model.StoryPage;
import com.deuteriumlabs.dendrite.model.User;
import com.google.appengine.api.datastore.Text;

public class ReadView extends View {

    public enum Format {
        BOLD, BOLD_ITALIC, ITALIC, NONE
    }

    public static String getAvatarDesc(final int id) {
        return User.getAvatarDesc(id);
    }

    private User author;
    private StoryBeginning beginning;
    private StoryPage page;
    private PageId pageId;

    public ReadView() {
    }

    public List<PageId> getAncestry() {
        return page.getAncestry();
    }

    public int getAuthorAvatarId() {
        final User user = getAuthor();
        return user.getAvatarId();
    }

    public String getAuthorId() {
        return page.getAuthorId();
    }

    public String getAuthorName() {
        return page.getAuthorName();
    }

    public String getChance() {
        return page.getChance();
    }

    public String getFirstUrl() {
        final PageId firstPageId = page.getBeginning();
        return "read?p=" + firstPageId;
    }

    public int getNumberOfOptions() {
        return StoryOption.countOptions(pageId);
    }

    public int getNumLovingUsers() {
        return page.getNumLovingUsers();
    }

    public String getNumLovingUsersString() {
        final int numLovingUsers = getNumLovingUsers();
        if (numLovingUsers > 0) {
            return Integer.toString(numLovingUsers);
        } else {
            return "";
        }
    }

    public String getOptionLink(final int index) {
        final StoryOption option = getOptionByIndex(index);
        final int target = option.getTarget();
        if (target == 0) {
            final PageId source = getPageId();
            final String from = source.toString();
            return "/write?from=" + from + "&linkIndex=" + index;
        } else {
            return "/read?p=" + target;
        }
    }

    public String getOptionText(final int index) {
        final StoryOption option = getOptionByIndex(index);
        return option.getText();
    }

    public PageId getPageId() {
        return pageId;
    }

    public String getPageNumber() {
        final int number = pageId.getNumber();
        return Integer.toString(number);
    }

    public String getPageText() {
        final Text text = page.getText();
        if (text != null) {
            return text.getValue();
        } else {
            return null;
        }
    }

    public List<String> getParagraphs() {
        final String text = getPageText();
        final List<String> list = new ArrayList<String>();
        if (text != null) {
            final String[] array = text.split("\n");
            for (String paragraph : array) {
                paragraph = paragraph.replaceAll("[\n\r]", "");
                if (paragraph.length() > 0) {
                    list.add(paragraph);
                }
            }
        }
        return list;
    }

    public String getParentId() {
        final List<PageId> ancestry = getAncestry();
        if (ancestry.size() > 1) {
            final PageId parentId = ancestry.get(ancestry.size() - 2);
            return parentId.toString();
        } else {
            return null;
        }
    }

    public List<String> getTags() {
        final List<String> tags = page.getTags();
        return tags;
    }

    public String getTitle() {
        final StoryBeginning beginning = getBeginning();
        return beginning.getTitle();
    }

    @Override
    public String getUrl() {
        return "/read?p=" + pageId.toString();
    }

    public boolean isAuthorAnonymous() {
        final String authorId = page.getAuthorId();
        return (authorId == null);
    }

    public boolean isAvatarAvailable() {
        final User user = getAuthor();
        return user.isAvatarAvailable();
    }

    public boolean isBeginning() {
        final int currPageNumber = pageId.getNumber();
        final StoryBeginning beginning = getBeginning();
        final int beginningPageNumber = beginning.getPageNumber();
        return (currPageNumber == beginningPageNumber);
    }

    public boolean isLoved() {
        final String userId = View.getMyUserId();
        return page.isLovedBy(userId);
    }

    public boolean isNotLoved() {
        final boolean isLoved = isLoved();
        return (isLoved == false);
    }

    public boolean isOptionWritten(final int index) {
        final StoryOption option = getOptionByIndex(index);
        return option.isConnected();
    }

    public boolean isPageInStore() {
        return page.isInStore();
    }

    public boolean isShowingFirstPage() {
        return (pageId.getNumber() == 1);
    }

    public void prepareIsNotLoved() {
        final boolean isNotLoved = isNotLoved();
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("isNotLoved", isNotLoved);
    }

    public void prepareMyUserId() {
        final String userId = View.getMyUserId();
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("myUserId", userId);
    }

    public void prepareNextPageNum() {
        final PageContext pageContext = getPageContext();
        final PageId id = getPageId();
        final int currPageNum = id.getNumber();
        final int nextPageNum = currPageNum + 1;
        pageContext.setAttribute("nextPageNum", nextPageNum);
    }

    public void prepareNumLovers() {
        final String num = getNumLovingUsersString();
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("numLovers", num);
    }

    public void prepareParentId() {
        final String id = getParentId();
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("parentId", id);
    }

    public void preparePageId() {
        final PageId id = getPageId();
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("pageId", id.toString());
    }

    public void preparePrevPageNum() {
        final PageContext pageContext = getPageContext();
        final PageId id = getPageId();
        final int currPageNum = id.getNumber();
        final int prevPageNum = currPageNum - 1;
        pageContext.setAttribute("prevPageNum", prevPageNum);
    }

    public void prepareTag(final String tag) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("tagClass", tag.toLowerCase());
        pageContext.setAttribute("tagName", tag.toUpperCase());
    }

    public void prepareTagName(final String tag) {
        final PageContext pageContext = getPageContext();
        pageContext.setAttribute("tagName", tag);
    }

    public void setPageId(final String idString) {
        final PageId id = getSpecificPageId(idString);
        setPageId(id);
    }

    private User getAuthor() {
        if (author == null) {
            final String userId = page.getAuthorId();
            author = new User();
            author.setId(userId);
            author.read();
        }
        return author;
    }

    private StoryBeginning getBeginning() {
        return beginning;
    }

    private StoryOption getOptionByIndex(final int index) {
        final StoryOption option = new StoryOption();
        option.setSource(pageId);
        option.setListIndex(index);
        option.read();
        return option;
    }

    private PageId getSpecificPageId(final String string) {
        final PageId id = new PageId(string);
        String version = id.getVersion();
        if (version == null) {
            final Random generator = new Random();
            version = StoryPage.getRandomVersion(id, generator);
            id.setVersion(version);
        }
        return id;
    }

    private void setBeginning(final StoryBeginning beginning) {
        this.beginning = beginning;
    }

    private void setPage(final StoryPage page) {
        this.page = page;
    }

    private void setPageId(final PageId id) {
        pageId = id;
        final StoryPage page = new StoryPage();
        page.setId(id);
        page.read();
        setPage(page);
        final PageId beginningId = page.getBeginning();
        final StoryBeginning beginning = new StoryBeginning();
        final int pageNumber = beginningId.getNumber();
        beginning.setPageNumber(pageNumber);
        beginning.read();
        setBeginning(beginning);
    }

    @Override
    protected String getMetaDesc() {
        return page.getLongSummary();
    }
}
