package com.deuteriumlabs.dendrite.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.dependencies.DatastoreQuery;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ModelTest {
    private class TestModel extends Model {
        @Override
        String getKindName() {
            return "testKind";
        }

        @Override
        DatastoreQuery getMatchingQuery() {
            return new DatastoreQuery(getKindName());
        }

        @Override
        void readPropertiesFromEntity(final DatastoreEntity entity) {
        }

        @Override
        void setPropertiesInEntity(final DatastoreEntity entity) {
        }
    }

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void testCreate() {
        final TestModel model = new TestModel();
        assertFalse(model.isInStore());
        model.create();
        assertTrue(model.isInStore());
    }

    @Test
    public void testDelete() {
        final TestModel model = new TestModel();
        model.create();
        assertTrue(model.isInStore());
        model.delete();
        assertFalse(model.isInStore());
    }

    @Test
    public void testNonPersistedModelIsNotInStore() {
        final TestModel model = new TestModel();
        assertFalse(model.isInStore());
    }

    @Test
    public void testPersistedModelIsInStore() {
        final TestModel model = new TestModel();
        model.create();
        assertTrue(model.isInStore());
    }
}
