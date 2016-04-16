package com.deuteriumlabs.dendrite.unittest;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.deuteriumlabs.dendrite.model.StoryPage;

public class StoryPageTest {
    @Test
    public void testBlankStoryPage() {
        final StoryPage storyPage = new StoryPage();
        assertEquals(storyPage.getBeginning(), null);
    }
}