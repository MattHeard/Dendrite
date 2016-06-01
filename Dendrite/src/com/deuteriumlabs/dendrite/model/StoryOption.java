/* © 2013-2015 Deuterium Labs Limited */
package com.deuteriumlabs.dendrite.model;

import com.deuteriumlabs.dendrite.dependencies.DatastoreEntity;
import com.deuteriumlabs.dendrite.dependencies.DatastoreQuery;
import com.deuteriumlabs.dendrite.dependencies.Store;
import com.deuteriumlabs.dendrite.queries.SingleEntity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

public class StoryOption extends Model {
    private static final int INVALID_LIST_INDEX = -1;
    private static final int INVALID_TARGET_PAGE_NUMBER = 0;
    private static final String KIND_NAME = "StoryOption";
    private static final String LIST_INDEX_PROPERTY = "listIndex";
    private static final String SOURCE_NUMBER_PROPERTY = "sourceNumber";
    private static final String SOURCE_VERSION_PROPERTY = "sourceVersion";
    private static final String TARGET_PROPERTY = "target";
    private static final String TEXT_PROPERTY = "text";

    public static int countOptions(final PageId source) {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final Filter filter = getSourceFilter(source);
        query.setFilter(filter);
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        final FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        return preparedQuery.countEntities(fetchOptions);
    }

    private static Filter getListIndexFilter(final int listIndex) {
        final String propertyName = LIST_INDEX_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final int value = listIndex;
        return new FilterPredicate(propertyName, operator, value);
    }

    private static int getListIndexFromEntity(final DatastoreEntity entity) {
        final Long listIndex = (Long) entity.getProperty(LIST_INDEX_PROPERTY);
        return listIndex.intValue();
    }

    private static Filter getSourceFilter(final PageId source) {
        final int number = source.getNumber();
        final Filter numberFilter = getSourceNumberFilter(number);
        final String version = source.getVersion();
        final Filter versionFilter = getSourceVersionFilter(version);
        return CompositeFilterOperator.and(numberFilter, versionFilter);
    }

    private static Filter getSourceNumberFilter(final int number) {
        final String propertyName = SOURCE_NUMBER_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final int value = number;
        return new FilterPredicate(propertyName, operator, value);
    }

    private static int getSourceNumberFromEntity(final DatastoreEntity entity) {
        final Long number = (Long) entity.getProperty(SOURCE_NUMBER_PROPERTY);
        return number.intValue();
    }

    private static Filter getSourceVersionFilter(final String version) {
        final String propertyName = SOURCE_VERSION_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final String value = version;
        return new FilterPredicate(propertyName, operator, value);
    }

    private static String getSourceVersionFromEntity(
            final DatastoreEntity entity) {
        return (String) entity.getProperty(SOURCE_VERSION_PROPERTY);
    }

    private static String getTextFromEntity(final DatastoreEntity entity) {
        return (String) entity.getProperty(TEXT_PROPERTY);
    }

    private int listIndex;
    private PageId source;
    private int target;
    private String text;

    public StoryOption() {
        setListIndex(INVALID_LIST_INDEX);
        setTarget(INVALID_TARGET_PAGE_NUMBER);
    }

    public int getListIndex() {
        return listIndex;
    }

    public PageId getSource() {
        return source;
    }

    public int getTarget() {
        return target;
    }

    public String getText() {
        return text;
    }

    public boolean isConnected() {
        final int target = getTarget();
        return (target > 0);
    }

    public void readWithTarget() {
        final DatastoreEntity entity = getEntityWithTarget();
        if (entity != null) {
            readPropertiesFromEntity(entity);
        }
    }

    public void setListIndex(final int listIndex) {
        this.listIndex = listIndex;
    }

    public void setSource(final PageId source) {
        this.source = source;
    }

    public void setTarget(final int target) {
        this.target = target;
    }

    public void setText(final String text) {
        this.text = text;
    }

    private boolean areSourceAndIndexSet() {
        final PageId source = getSource();
        final boolean isSourceSet = ((source != null) && source.isValid());
        final int listIndex = getListIndex();
        final boolean isListIndexSet = (listIndex != INVALID_LIST_INDEX);
        return isSourceSet && isListIndexSet;
    }

    private DatastoreEntity getEntityWithTarget() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final int target = getTarget();
        final Filter filter = getTargetFilter(target);
        query.setFilter(filter);
        final Store store = new Store();
        final PreparedQuery preparedQuery = store.prepare(query);
        return new SingleEntity(preparedQuery).get();
    }

    private DatastoreQuery getQueryMatchingSourceAndIndex() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final PageId source = getSource();
        final Filter sourceFilter = getSourceFilter(source);
        final int listIndex = getListIndex();
        final Filter listIndexFilter = getListIndexFilter(listIndex);
        final Filter filter;
        filter = CompositeFilterOperator.and(sourceFilter, listIndexFilter);
        return query.setFilter(filter);
    }

    private DatastoreQuery getQueryMatchingTarget() {
        final DatastoreQuery query = new DatastoreQuery(KIND_NAME);
        final int target = getTarget();
        final Filter filter = getTargetFilter(target);
        return query.setFilter(filter);
    }

    private Filter getTargetFilter(final int target) {
        final String propertyName = TARGET_PROPERTY;
        final FilterOperator operator = FilterOperator.EQUAL;
        final int value = target;
        return new FilterPredicate(propertyName, operator, value);
    }

    private int getTargetFromEntity(final DatastoreEntity entity) {
        final Long number = (Long) entity.getProperty(TARGET_PROPERTY);
        if (number != null) {
            return number.intValue();
        } else {
            return INVALID_TARGET_PAGE_NUMBER;
        }
    }

    private void readListIndexFromEntity(final DatastoreEntity entity) {
        final int listIndex = getListIndexFromEntity(entity);
        setListIndex(listIndex);
    }

    private void readSourceFromEntity(final DatastoreEntity entity) {
        final PageId source = new PageId();
        final int number = getSourceNumberFromEntity(entity);
        source.setNumber(number);
        final String version = getSourceVersionFromEntity(entity);
        source.setVersion(version);
        setSource(source);
    }

    private void readTargetFromEntity(final DatastoreEntity entity) {
        final int target = getTargetFromEntity(entity);
        setTarget(target);
    }

    private void readTextFromEntity(final DatastoreEntity entity) {
        final String text = getTextFromEntity(entity);
        setText(text);
    }

    private void setListIndexInEntity(final DatastoreEntity entity) {
        final int listIndex = getListIndex();
        entity.setProperty(LIST_INDEX_PROPERTY, listIndex);
    }

    private void setSourceInEntity(final DatastoreEntity entity) {
        final PageId source = getSource();
        final int number = source.getNumber();
        entity.setProperty(SOURCE_NUMBER_PROPERTY, number);
        final String version = source.getVersion();
        entity.setProperty(SOURCE_VERSION_PROPERTY, version);
    }

    private void setTargetInEntity(final DatastoreEntity entity) {
        final int target = getTarget();
        if (target != 0) {
            entity.setProperty(TARGET_PROPERTY, target);
        }
    }

    private void setTextInEntity(final DatastoreEntity entity) {
        final String text = getText();
        entity.setProperty(TEXT_PROPERTY, text);
    }

    @Override
    String getKindName() {
        return KIND_NAME;
    }

    @Override
    DatastoreQuery getMatchingQuery() {
        if (areSourceAndIndexSet() == true) {
            return getQueryMatchingSourceAndIndex();
        } else {
            return getQueryMatchingTarget();
        }
    }

    @Override
    void readPropertiesFromEntity(final DatastoreEntity entity) {
        readSourceFromEntity(entity);
        readListIndexFromEntity(entity);
        readTextFromEntity(entity);
        readTargetFromEntity(entity);
    }

    @Override
    void setPropertiesInEntity(final DatastoreEntity entity) {
        setSourceInEntity(entity);
        setListIndexInEntity(entity);
        setTextInEntity(entity);
        setTargetInEntity(entity);
    }
}
