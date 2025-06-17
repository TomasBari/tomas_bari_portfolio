package com.ictdemy.vibeup.controller;

import com.ictdemy.vibeup.dto.JwtResponse;
import com.ictdemy.vibeup.dto.LoginRequest;
import com.ictdemy.vibeup.dto.RegisterResponse;
import com.ictdemy.vibeup.model.User;
import com.ictdemy.vibeup.security.JwtUtil;
import com.ictdemy.vibeup.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User registered = userService.registerUser(user.getName(), user.getEmail(), user.getPassword());

            return ResponseEntity.ok(new RegisterResponse("Successful registration", registered.getEmail()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            logger.info("Login attempt: " + loginRequest.getEmail());

            Optional<User> userOpt = userService.findByEmail(loginRequest.getEmail());
            if (userOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong email or password");
            }

            User user = userOpt.get();
            logger.debug("Password from database (encrypted): " + user.getPassword());

            if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                logger.warn("Wrong password: " + loginRequest.getEmail());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong email or password");
            }

            logger.info("Login successful: " + user.getEmail());

            String token = jwtUtil.generateToken(user);
            logger.debug("Generated JWT token: " + token);
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            logger.error("Login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

}
