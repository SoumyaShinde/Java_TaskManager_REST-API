package com.project.taskmanager.controller;

import com.project.taskmanager.dto.AuthRequest;
import com.project.taskmanager.dto.AuthResponse;
import com.project.taskmanager.util.JwtUtil;
import com.project.taskmanager.dto.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private com.project.taskmanager.security.impl.UserDetailsServiceImpl userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createToken(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            // Log the exception for debugging purposes
            logger.error("Authentication failed for username: " + authRequest.getUsername(), e);
            return ResponseEntity.status(401).body(new Status("401", "Invalid username or password"));
        }

        // If authentication is successful, generate the JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());  // Explicit type
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return the JWT in the response
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

}
