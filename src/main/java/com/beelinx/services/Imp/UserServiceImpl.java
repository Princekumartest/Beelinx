package com.beelinx.services.Imp;

import com.beelinx.model.UserEntity;
import com.beelinx.repository.jpa.UserRepository;
import com.beelinx.repository.spec.UserSpecification;
import com.beelinx.services.UserService;
import com.beelinx.dto.UserDto;
import com.beelinx.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSpecification userSpecification;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto registerUser(UserEntity user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserEntity updatedUser = userRepository.save(user);
        return userMapper.mapToDto(updatedUser);
    }

    @Override
    public List<UserDto> getAllUser() {

        return userRepository.findAll().stream().map(userMapper::mapToDto).toList();
    }

}
