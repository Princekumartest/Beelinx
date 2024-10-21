package com.beelinx.services.Imp;

import com.beelinx.model.UserEntity;
import com.beelinx.repository.jpa.UserRepository;
import com.beelinx.repository.spec.UserSpecification;
import com.beelinx.services.UserService;
import com.beelinx.dto.UserDto;
import com.beelinx.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.xml.bind.ValidationException;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    final UserRepository userRepository;

    final UserMapper userMapper;

    final PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(UserEntity user ) throws Exception{

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            UserEntity updatedUser = userRepository.save(user);
            return userMapper.mapToDto(updatedUser);
        }catch(Exception ex){
            throw new ValidationException("Failed to register User: " + ex.getMessage());
        }
    }

    @Override
    public List<UserDto> getAllUser() {

        return userRepository.findAll().stream().map(userMapper::mapToDto).toList();
    }
}


