package com.beelinx.services;

import com.beelinx.model.UserEntity;
import com.beelinx.dto.UserDto;
import java.util.List;

public interface UserService {

    UserDto registerUser(UserEntity user);

    List<UserDto> getAllUser();

}
