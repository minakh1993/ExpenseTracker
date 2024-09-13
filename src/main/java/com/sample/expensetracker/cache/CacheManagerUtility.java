package com.sample.expensetracker.cache;

import com.sample.expensetracker.config.CacheConfig;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Component
public class CacheManagerUtility {

    private final ExpenseCacheManager customerCacheManager;

    public CacheManagerUtility(ExpenseCacheManager customerCacheManager, CacheConfig cacheConfig) {
        this.customerCacheManager = customerCacheManager;
        customerCacheManager.createCache(CacheName.CATEGORY_INFO, cacheConfig.getCategoryTimeToLive());
        customerCacheManager.createCache(CacheName.USER_INFO, cacheConfig.getUserTimeToLive());
    }

    public void insertIntoCache(CacheName.CacheIdentifier cacheName, String key, Object value) {
        customerCacheManager.insertIntoCache(cacheName.toString(), key, value);
    }

    public Object getFromCache(CacheName.CacheIdentifier cacheName, String key) {
        return customerCacheManager.getFromCache(cacheName.toString(), key);
    }

    public void removeFromCache(CacheName.CacheIdentifier cacheName, String key) {
        customerCacheManager.removeFromCache(cacheName.toString(), key);
    }

    public void replaceCacheItem(CacheName.CacheIdentifier cacheName, String key, Object newValue) {
        customerCacheManager.replaceCacheItem(cacheName.toString(), key, newValue);
    }

    public boolean isKeyInCache(CacheName.CacheIdentifier cacheName, String key) {
        return customerCacheManager.isKeyInCache(cacheName.toString(), key);
    }

    public Boolean clearCache(CacheName.CacheIdentifier cacheName) {
        return customerCacheManager.clearCache(cacheName.toString());
    }

    public Boolean isCacheEmpty(CacheName.CacheIdentifier cacheName) {
        return customerCacheManager.isCacheEmpty(cacheName.toString());
    }

    public List<Object> getAllFromCache(CacheName.CacheIdentifier cacheName) {
        return customerCacheManager.getAllFromCache(cacheName.toString());
    }
}