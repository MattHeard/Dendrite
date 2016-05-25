package com.deuteriumlabs.dendrite.view;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.servlet.jsp.PageContext;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.deuteriumlabs.dendrite.model.DatastoreQuery;
import com.deuteriumlabs.dendrite.queries.Store;
import com.google.appengine.api.datastore.PreparedQuery;

public class ContentsViewTest {

    @Test
    public void testGetUrl() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testInitialise() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetTags() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testContentsView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetBodyMainTitle() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetLinks() {
        final ContentsView view = new ContentsView();
        final Store store = Mockito.mock(Store.class);
        final PreparedQuery preparedQuery = Mockito.mock(PreparedQuery.class);
        Mockito.doAnswer(new Answer<PreparedQuery>() {
            @Override
            public PreparedQuery answer(InvocationOnMock invocation)
                    throws Throwable {
                return preparedQuery;
            }
        }).when(store).prepare(Mockito.any(DatastoreQuery.class));
        final DatastoreQuery query = Mockito.mock(DatastoreQuery.class);
        assertNull(view.getLinks(store, query));
    }

    @Test
    public void testGetNextPageNumber() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetPrevPageNumber() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUrls() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsFirstPage() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsLastPage() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsPastTheLastPg() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareLastPageLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareNextPageLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPreparePrevPageLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareRomanPgNum() {
        final ContentsView view = new ContentsView();
        final PageContext pageContext = Mockito.mock(PageContext.class);
        view.setPageContext(pageContext);
        view.prepareRomanPgNum();
        Mockito.verify(pageContext).setAttribute("romanPgNum", "i");
    }

    @Test
    public void testPrepareTag() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetContentsPageNumber() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetContentsPageNumberFromRequest() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetAuthorLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetMyUserId() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetMyUserName() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUserAlignment() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUserFontColour() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUserFontSize() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUserFontType() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUserSpacing() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUserTheme() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsUserLoggedIn() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testView() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testAreThereNewNotifications() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCountNewNotifications() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetLoginLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetLogoutLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetPageContext() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetRequest() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetWebPageTitle() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testHasAnotherFooterLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareNextFooterLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareThemeClasses() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareUserHeader() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetPageContext() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testSetRequest() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareThemeClass() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareNumNotifications() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareFmtDimension() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareUpperCaseFmtVal() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareMetaDesc() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetMetaDesc() {
        fail("Not yet implemented"); // TODO
    }

}
