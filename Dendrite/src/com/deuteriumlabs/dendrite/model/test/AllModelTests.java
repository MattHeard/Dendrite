/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PageIdTest.class, StoryBeginningTest.class,
    StoryOptionTest.class, StoryPageTest.class, UserTest.class })
public class AllModelTests { }
