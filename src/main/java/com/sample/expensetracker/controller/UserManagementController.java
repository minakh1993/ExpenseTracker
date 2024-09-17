package com.sample.expensetracker.controller;

import com.sample.expensetracker.api.SignUpRequestDto;
import com.sample.expensetracker.api.SignUpResponseDto;
import com.sample.expensetracker.exception.DuplicateUsernameException;
import com.sample.expensetracker.exception.EntityAlreadyExistException;
import com.sample.expensetracker.service.SignUpService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@RestController
@RequestMapping("/management/user")
@Slf4j
@Tag(name = "${UserManagementController.serviceNames}")
@AllArgsConstructor
public class UserManagementController {

    private final SignUpService service;

    @PostMapping(value = "/signup",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ROLE_USER")
    @Operation(operationId = "signUp", description = "${signUp}",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ok"),
                    @ApiResponse(responseCode = "400", description = "md:signUp.md"),
            })
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto requestDto) throws EntityAlreadyExistException, DuplicateUsernameException {
        return service.signup(requestDto);
    }
}