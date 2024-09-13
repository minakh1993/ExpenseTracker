package com.sample.expensetracker.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Configuration
@EnableCaching
public class CacheConfiguration {

    private final static String EHCACH_PROVIDER_NAME = "org.ehcache.jsr107.EhcacheCachingProvider";

    @Bean
    public CacheManager ehCacheManager() {
        CachingProvider provider = Caching.getCachingProvider(EHCACH_PROVIDER_NAME);
        CacheManager cacheManager = provider.getCacheManager();
        return cacheManager;
    }
}