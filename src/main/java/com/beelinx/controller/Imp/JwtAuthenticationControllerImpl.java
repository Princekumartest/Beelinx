package com.beelinx.controller.Imp;

import com.beelinx.model.JwtRequest;
import com.beelinx.model.JwtResponse;
import com.beelinx.services.Imp.JwtUserDetailsServiceImpl;
import com.beelinx.utility.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class JwtAuthenticationControllerImpl {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsServiceImpl userDetailsService;

    @PostMapping("/log-out")
    public ResponseEntity<?> logOut(@RequestHeader("Authorization") String authorizationHeader) {
        // Extract the token from the Authorization header
        String token = authorizationHeader.substring("Bearer ".length()).trim();

        // Invalidate the token
        jwtTokenUtil.logOut(token);

        return ResponseEntity.ok("Log Out successfully");
    }

    @PostMapping("/log-in")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}


