package com.sample.expensetracker.util;

import com.sample.expensetracker.exception.ServerBusyException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.jdbc.lock.JdbcLockRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * @author M.khoshnevisan
 * @since 9/17/2024
 */
@Component
@AllArgsConstructor
@Slf4j
public class LockUtil {

    private final JdbcLockRegistry lockRegistry;

    public Lock requestLock(String lockName, int lockTtl) {
        Lock lock = lockRegistry.obtain(lockName);
        try {
            lock.tryLock(lockTtl, TimeUnit.SECONDS);
            log.info("acquired lock with lockName: " + lockName);
            return lock;
        } catch (InterruptedException e) {
            throw new ServerBusyException("exception on request lock", e);
        }
    }

    public void releaseLock(Lock lock) {
        lock.unlock();
        log.info("released lock");

    }
}