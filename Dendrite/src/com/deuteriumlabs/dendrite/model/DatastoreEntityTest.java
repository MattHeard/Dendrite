package com.deuteriumlabs.dendrite.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class DatastoreEntityTest {
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
    
    @Test
    public void testConstructorWithEntity() {
        final Entity entity = null;
        assertNotNull(new DatastoreEntity(entity));
    }
    
    @Test
    public void testConstructorWithKind() {
        final String kind = "testKind";
        assertNotNull(new DatastoreEntity(kind));
    }
    
    @Test
    public void testConstructorWithKindAndKey() {
        final String kind = "testKind";
        final String key = "testKey";
        assertNotNull(new DatastoreEntity(kind, key));
    }
    
    @Test
    public void testGetKey() {
        final DatastoreEntity entity = buildTestEntity();
        assertNotNull(entity.getKey());
    }

    @Test
    public void testHasProperty() {
        final DatastoreEntity entity = buildTestEntity();
        final String propertyName = "testPropertyName";
        final String propertyValue = "testPropertyValue";
        entity.setProperty(propertyName, propertyValue);
        assertTrue(entity.hasProperty(propertyName));
    }
    
    @Test
    public void testSetProperty() {
        final DatastoreEntity entity = buildTestEntity();
        final String propertyName = "testPropertyName";
        assertFalse(entity.hasProperty(propertyName));
        final String propertyValue = "testPropertyValue";
        entity.setProperty(propertyName, propertyValue);
        assertTrue(entity.hasProperty(propertyName));
    }
    
    @Test
    public void testGetProperty() {
        final DatastoreEntity entity = buildTestEntity();
        final String propertyName = "testPropertyName";
        final String propertyValue = "testPropertyValue";
        entity.setProperty(propertyName, propertyValue);
        assertEquals(propertyValue, entity.getProperty(propertyName));
    }
    
    @Test
    public void testPutInStore() {
        final DatastoreEntity entity = buildTestEntity();
        entity.putInStore();
    }
    
    @Test
    public void testFromPreparedQuery() {
        final String kind = "testKind";
        final DatastoreQuery query = new DatastoreQuery(kind);
        final PreparedQuery preparedQuery =
                DatastoreServiceFactory.getDatastoreService().prepare(query.get());
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        final List<DatastoreEntity> entities =
                DatastoreEntity.fromPreparedQuery(preparedQuery, fetchOptions);
        assertNotNull(entities);
    }
    
    private DatastoreEntity buildTestEntity() {
        final String kind = "testKind";
        final DatastoreEntity entity = new DatastoreEntity(kind);
        return entity;
    }
}
