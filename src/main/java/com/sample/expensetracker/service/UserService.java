package com.sample.expensetracker.service;

import com.sample.expensetracker.api.SignUpRequestDto;
import com.sample.expensetracker.api.SignUpResponseDto;
import com.sample.expensetracker.converter.UserConverter;
import com.sample.expensetracker.entity.RoleEntity;
import com.sample.expensetracker.entity.UserEntity;
import com.sample.expensetracker.exception.DuplicateUsernameException;
import com.sample.expensetracker.exception.EntityAlreadyExistException;
import com.sample.expensetracker.repository.RoleRepository;
import com.sample.expensetracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author M.khoshnevisan
 * @since 9/15/2024
 */
@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;

    @Transactional(rollbackFor = Exception.class)
    public SignUpResponseDto signup(SignUpRequestDto requestDto) throws EntityAlreadyExistException, DuplicateUsernameException {
        synchronized (requestDto.getNationalCode().intern()) {
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
            RoleEntity role = roleRepository.findByName("USER");
            UserEntity user = userConverter.convertToUserEntity(requestDto, role);
            UserEntity savedEntity = userRepository.save(user);
            return userConverter.convertToSignUpResponseDto(savedEntity);
        }
    }
}