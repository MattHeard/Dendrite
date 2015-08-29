/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AboutViewTest.class,
        AddTagControllerTest.class,
        AltViewTest.class,
        BibliographyViewTest.class,
        DeleteNotificationControllerTest.class })
public class AllTests {}
