package com.beelinx.controller.Imp;

import com.beelinx.controller.UserController;
import com.beelinx.model.UserEntity;
import com.beelinx.repository.jpa.UserRepository;
import com.beelinx.services.Imp.UserServiceImpl;
import com.beelinx.dto.UserDto;
import com.beelinx.mapper.UserMapper;
import com.beelinx.utility.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signup(@RequestBody @Valid UserEntity user, @RequestHeader Map<String, String> headers) {

    /*    if (!user.isMobileNumberValid()) {
            return ResponseEntity.badRequest().body(null);
        }*/

        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        return new ResponseEntity<>(userService.registerUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDto>> getUser(@RequestHeader Map<String, String> headers) {

        return new ResponseEntity<>(userService.getAllUser(), HttpStatus.OK);
    }
}
