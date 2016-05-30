package com.deuteriumlabs.dendrite.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.servlet.jsp.PageContext;

import org.junit.Test;
import org.mockito.Mockito;

import com.deuteriumlabs.dendrite.model.User;

public class CoverViewTest {

    @Test
    public void testAreThereNewNotifications() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testCountNewNotifications() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetAuthorLink() {
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
    public void testGetMetaDesc() {
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
    public void testGetPageContext() {
        final PageContext pageContext = Mockito.mock(PageContext.class);
        final CoverView view = new CoverView(pageContext);
        assertEquals(pageContext, view.getPageContext());
    }

    @Test
    public void testGetRequest() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testGetUrl() {
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
    public void testGetWebPageTitle() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testHasAnotherFooterLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testInitialise() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testIsUserLoggedIn() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareFmtDimension() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareMetaDesc() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareNextFooterLink() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareNumNotifications() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareTagName() {
        final PageContext pageContext = Mockito.mock(PageContext.class);
        final CoverView view = new CoverView(pageContext);
        final String tagName = "test";
        view.prepareTagName(tagName);
        Mockito.verify(pageContext).setAttribute("tagName", tagName);
    }

    @Test
    public void testPrepareThemeClass() {
        final PageContext pageContext = Mockito.mock(PageContext.class);
        final CoverView view = new CoverView(pageContext);
        final User user = Mockito.mock(User.class);
        Mockito.when(user.getTheme()).thenReturn("Dark");
        view.prepareThemeClass(user);
        Mockito.verify(pageContext).setAttribute("themeClass", "darkTheme");
    }

    @Test
    public void testPrepareThemeClasses() {
        fail("Not yet implemented"); // TODO
    }

    @Test
    public void testPrepareUpperCaseFmtVal() {
        final PageContext pageContext = Mockito.mock(PageContext.class);
        final CoverView view = new CoverView(pageContext);
        final String upperCaseFmtVal = "TEST";
        view.prepareUpperCaseFmtVal(upperCaseFmtVal);
        Mockito.verify(pageContext).setAttribute("upperFmtVal",
                upperCaseFmtVal);
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
    public void testView() {
        fail("Not yet implemented"); // TODO
    }

}
