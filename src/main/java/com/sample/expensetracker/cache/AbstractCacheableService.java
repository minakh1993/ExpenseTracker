package com.sample.expensetracker.cache;

import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Setter
public abstract class AbstractCacheableService<T extends KeyProvider> {

    @Autowired
    private CacheManagerUtility cacheManagerUtility;

    protected T cacheableGetById(String id) {
        if (cacheManagerUtility.isCacheEmpty(getCacheName())) {
            List<T> items = getAllFromDataSource();
            addAllToCache(items);
        }
        Object cachedObject = cacheManagerUtility.getFromCache(getCacheName(), id);
        if (cachedObject == null) {
            return null;
        }
        return convertInstanceOfObject(cachedObject, getType());
    }

    public List<T> cacheableGetAll() {
        List<T> items;
        if (cacheManagerUtility.isCacheEmpty(getCacheName())) {
            items = getAllFromDataSource();
            addAllToCache(items);
            return items;
        }
        items = new ArrayList<>();
        List<Object> itemsObj = cacheManagerUtility.getAllFromCache(getCacheName());
        for (Object item : itemsObj) {
            items.add(convertInstanceOfObject(item, getType()));
        }
        return items;
    }

    protected abstract CacheName.CacheIdentifier getCacheName();

    protected abstract Class<T> getType();

    protected abstract List<T> getAllFromDataSource();

    private T convertInstanceOfObject(Object obj, Class<T> clazz) {
        try {
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            return null;
        }
    }

    protected void addToCache(T item) {
        cacheManagerUtility.insertIntoCache(getCacheName(), item.getKey(), item);
    }

    protected void addAllToCache(List<T> items) {
        if (CollectionUtils.isNotEmpty(items)) {
            for (T item : items) {
                cacheManagerUtility.insertIntoCache(getCacheName(), item.getKey(), item);
            }
        }
    }
}