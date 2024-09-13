package com.sample.expensetracker.service;

import com.sample.expensetracker.cache.CacheName;
import com.sample.expensetracker.entity.UserEntity;
import com.sample.expensetracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author M.khoshnevisan
 * @since 9/13/2024
 */
@Service
@AllArgsConstructor
public class UserCacheableService {
    private final UserRepository userRepository;

    @Cacheable(cacheNames = CacheName.USER_INFO, key = "#username", unless = "#result==null")
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}