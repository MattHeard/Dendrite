package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.deuteriumlabs.dendrite.model.StoryPage;

public class StoryPageTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testBlankStoryPageHasNoBeginning() {
        final StoryPage storyPage = new StoryPage();
        assertEquals(storyPage.getBeginning(), null);
    }

    @Test
    public void testExceptionDeterminingWhetherBlankPageIsFirst() {
        final StoryPage storyPage = new StoryPage();
        exception.expect(NullPointerException.class);
        storyPage.determineWhetherFirstPg();
    }
}