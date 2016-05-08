package com.deuteriumlabs.dendrite.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ModelTest {
    private final LocalServiceTestHelper helper =
            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    
    @Before
    public void setUp() {
      helper.setUp();
    }

    @After
    public void tearDown() {
      helper.tearDown();
    }
    
    private class TestModel extends Model {
        @Override
        String getKindName() {
            return "testKind";
        }

        @Override
        Query getMatchingQuery() {
            return new Query(getKindName());
        }

        @Override
        void readPropertiesFromEntity(DatastoreEntity entity) { }

        @Override
        void setPropertiesInEntity(DatastoreEntity entity) { }
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
    public void testPersistedModelIsInStore() {
        final TestModel model = new TestModel();
        model.create();
        assertTrue(model.isInStore());
    }
    
    @Test
    public void testNonPersistedModelIsNotInStore() {
        final TestModel model = new TestModel();
        assertFalse(model.isInStore());
    }
}
