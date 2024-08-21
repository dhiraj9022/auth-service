package com.auth_service.service;

import com.auth_service.dto.UserDto;
import com.auth_service.exception.NotFoundException;
import com.auth_service.repo.UserRepo;
import com.auth_service.utils.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuthUtil authUtil;

    public UserDto getAuthUser() {
        Optional<UserDto> userDto = userRepo.findDtoByEmail(authUtil.getAuthEmail());
        if (!userDto.isPresent()) throw new NotFoundException("user not found");
        return userDto.get();
    }
}
