package com.sample.expensetracker.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
public interface CacheName {
    String CATEGORY_INFO = "CATEGORY_INFO";
    String USER_INFO = "USER_INFO";
    Map<String, CacheIdentifier> cacheNames = new HashMap<>() {
        {
            put(CATEGORY_INFO, CacheIdentifier.CATEGORY_INFO);
            put(USER_INFO, CacheIdentifier.USER_INFO);
        }
    };

    enum CacheIdentifier {CATEGORY_INFO, USER_INFO}
}