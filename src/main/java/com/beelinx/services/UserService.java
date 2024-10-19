package com.beelinx.services;

import com.beelinx.model.UserEntity;
import com.beelinx.dto.UserDto;
import java.util.List;

public interface UserService {

    UserDto signUp(UserEntity user) throws Exception;

    List<UserDto> getAllUser();
}
