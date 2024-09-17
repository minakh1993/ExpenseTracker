package com.sample.expensetracker.service;

import com.sample.expensetracker.api.SignUpRequestDto;
import com.sample.expensetracker.api.SignUpResponseDto;
import com.sample.expensetracker.converter.UserConverter;
import com.sample.expensetracker.entity.RoleEntity;
import com.sample.expensetracker.entity.UserEntity;
import com.sample.expensetracker.repository.RoleRepository;
import com.sample.expensetracker.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public SignUpResponseDto save(SignUpRequestDto requestDto) {
        RoleEntity role = roleRepository.findByName("USER");
        UserEntity user = userConverter.convertToUserEntity(requestDto, role);
        UserEntity savedEntity = userRepository.save(user);
        return userConverter.convertToSignUpResponseDto(savedEntity);
    }
}