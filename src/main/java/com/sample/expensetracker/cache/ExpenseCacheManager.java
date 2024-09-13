package com.sample.expensetracker.cache;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.cache.Cache;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Component
@AllArgsConstructor
public class ExpenseCacheManager {

    private static final long defaultTimeToLive = 86400;
    private static final boolean defaultStoreByValue = false;

    private final javax.cache.CacheManager manager;

    public void insertIntoCache(String cacheName, String key, Object value) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache == null) {
            cache = manager.createCache(cacheName, getMutableConfiguration(defaultTimeToLive, defaultStoreByValue));
        }
        cache.put(key, value);
    }

    public Object getFromCache(String cacheName, String key) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache != null) {
            return cache.get(key);
        }
        return null;
    }

    public List<Object> getAllFromCache(String cacheName) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache != null) {
            List<Object> items = new ArrayList<>();
            for (Cache.Entry<Object, Object> entry : cache) {
                items.add(entry.getValue());
            }
            return items;
        }
        return null;
    }

    public void removeFromCache(String cacheName, String key) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache != null) {
            cache.remove(key);
        }
    }

    public void replaceCacheItem(String cacheName, String key, Object newValue) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache != null) {
            cache.replace(key, newValue);
        }
    }

    public boolean isKeyInCache(String cacheName, String key) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache != null) {
            return cache.containsKey(key);
        }
        return false;
    }

    public void createCache(String cacheName, long timeToLive) {
        manager.createCache(cacheName, getMutableConfiguration(timeToLive, false));
    }

    protected MutableConfiguration<Object, Object> getMutableConfiguration(Long timeToLive, Boolean storeByValue) {
        MutableConfiguration<Object, Object> mutableConfiguration = new MutableConfiguration<>()
                .setTypes(Object.class, Object.class)
                .setStoreByValue(storeByValue);
        if (timeToLive != null && timeToLive != -1) {
            mutableConfiguration.setExpiryPolicyFactory(
                    (CreatedExpiryPolicy.factoryOf(new Duration(TimeUnit.SECONDS, timeToLive))));
        }
        return mutableConfiguration;
    }

    public Boolean clearCache(String cacheName) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
            return true;
        }
        return false;
    }

    public Boolean isCacheEmpty(String cacheName) {
        Cache<Object, Object> cache = manager.getCache(cacheName);
        if (cache != null) {
            return !cache.iterator().hasNext();
        }
        return true;
    }
}