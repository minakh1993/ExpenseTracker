package com.sample.expensetracker.service;

import com.sample.expensetracker.api.SignUpRequestDto;
import com.sample.expensetracker.api.SignUpResponseDto;
import com.sample.expensetracker.entity.UserEntity;
import com.sample.expensetracker.exception.DuplicateUsernameException;
import com.sample.expensetracker.exception.EntityAlreadyExistException;
import com.sample.expensetracker.repository.UserRepository;
import com.sample.expensetracker.util.LockUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * @author M.khoshnevisan
 * @since 9/17/2024
 */
@Service
@AllArgsConstructor
public class SignUpService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final LockUtil lockUtil;
    private final int SignUpLockTtl = 30;

    @Transactional(rollbackFor = Exception.class)
    public SignUpResponseDto signup(SignUpRequestDto requestDto) throws EntityAlreadyExistException, DuplicateUsernameException {
        Lock lock = lockUtil.requestLock(requestDto.getNationalCode(), SignUpLockTtl);
        try {
            List<UserEntity> userList = userRepository
                    .findByNationalCodeOrUsername(requestDto.getNationalCode(), requestDto.getUsername());
            if (CollectionUtils.isNotEmpty(userList)) {
                if (userList.stream()
                        .anyMatch(entity -> entity.getNationalCode().equals(requestDto.getNationalCode()))) {
                    throw new EntityAlreadyExistException("user exist with nationalCode: " + requestDto.getNationalCode());
                }
                if (userList.stream()
                        .anyMatch(entity -> entity.getUsername().equals(requestDto.getUsername()))) {
                    throw new DuplicateUsernameException("duplicate user with username: " + requestDto.getUsername());
                }
            }
            return userService.save(requestDto);
        } finally {
            lockUtil.releaseLock(lock);
        }
    }
}