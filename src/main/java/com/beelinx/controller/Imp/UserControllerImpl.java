package com.beelinx.controller.Imp;

import com.beelinx.controller.UserController;
import com.beelinx.helper.ApiResponseList;
import com.beelinx.helper.ApiResponseMessage;
import com.beelinx.model.UserEntity;
import com.beelinx.repository.jpa.UserRepository;
import com.beelinx.services.Imp.UserServiceImpl;
import com.beelinx.dto.UserDto;
import com.beelinx.utility.JwtTokenUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserControllerImpl implements UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponseMessage> signup(@RequestBody @Valid UserEntity user,
                                                     @RequestHeader Map<String, String> headers) throws ValidationException {
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        try{
            UserDto registeredUser = userService.signUp(user);

            ApiResponseMessage response = new ApiResponseMessage(
                    "User Account Created Successful", "SUCCESS", HttpStatus.CREATED.value());
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            throw new ValidationException("Failed to register user: " + ex.getMessage());
        }
    }

    @GetMapping("/getAllUser")
    public ResponseEntity<List<UserDto>> getUser(@RequestHeader Map<String, String> headers) {
        List<UserDto> users = userService.getAllUser();

        ApiResponseList<List<UserDto>> response = new ApiResponseList<>(
                "Fetch all user successfully",
                "OK",
                200,
                users
        );
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
