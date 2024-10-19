package com.beelinx.services.Imp;

import com.beelinx.model.UserEntity;
import com.beelinx.repository.jpa.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            UserEntity user = userRepository.findByEmail(username);

            // Check if the user exists
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
            // Return the UserDetails with the password and roles/authorities
            return new User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

}
