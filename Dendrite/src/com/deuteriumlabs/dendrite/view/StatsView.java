package com.deuteriumlabs.dendrite.view;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

import com.deuteriumlabs.dendrite.model.StoryBeginning;
import com.deuteriumlabs.dendrite.model.StoryPage;

public class StatsView extends View {

	private static final String URL = "/stats";
	private static final String WEB_PG_TITLE_ATTR_NAME = "webPageTitle";
	private static final String WEB_PG_TITLE_ATTR_VAL = "Dendrite - Stats";
	private static final String NUM_PGS_ATTR_NAME = "numPgs";
	private static final String NUM_STORIES_ATTR_NAME = "numStories";
	private static final String NUM_PGS_PER_STORY_ATTR_NAME = "numPgsPerStory";
	private HashMap<String, Object> cache;

	@Override
	String getUrl() {
		return URL;
	}
	
	public StatsView() {
		this.initCache();
	}

	private void initCache() {
		this.cache = new HashMap<String, Object>();
	}

	@Override
	public void initialise() {
		final PageContext pageContext = this.getPageContext();
		pageContext.setAttribute(WEB_PG_TITLE_ATTR_NAME, WEB_PG_TITLE_ATTR_VAL);
		super.initialise();
	}
	
	public void prepareNumPgs() {
		final PageContext pageContext = this.getPageContext();
		String name = NUM_PGS_ATTR_NAME;
		int val = this.getNumPgs();
		pageContext.setAttribute(name, val);
	}

	private int getNumPgs() {
		int numPgs;
		final String key = NUM_PGS_ATTR_NAME;
		if (cache.containsKey(key)) {
			numPgs = (Integer) cache.get(key);
		} else {
			numPgs = StoryPage.countAllPgs();
			final int val = numPgs;
			cache.put(key, val);
		}
		return numPgs;
	}
	
	public void prepareNumStories() {
		final PageContext pageContext = this.getPageContext();
		String name = NUM_STORIES_ATTR_NAME;
		int val = this.getNumStories();
		pageContext.setAttribute(name, val);
	}

	private int getNumStories() {
		int numStories;
		final String key = NUM_STORIES_ATTR_NAME;
		if (cache.containsKey(key)) {
			numStories = (Integer) cache.get(key);
		} else {
			numStories = StoryBeginning.countAllStories();
			final int val = numStories;
			cache.put(key, val);
		}
		return numStories;
	}
	
	public void prepareNumPgsPerStory() {
		final PageContext pageContext = this.getPageContext();
		String name = NUM_PGS_PER_STORY_ATTR_NAME;
		String val = this.getNumPgsPerStory();
		pageContext.setAttribute(name, val);
	}

	private String getNumPgsPerStory() {
		final double numPgs = (double) this.getNumPgs();
		final double numStories = (double) this.getNumStories();
		final double numPgsPerStory = numPgs / numStories;
		return String.format("%.2g%n", numPgsPerStory);
	}
}
