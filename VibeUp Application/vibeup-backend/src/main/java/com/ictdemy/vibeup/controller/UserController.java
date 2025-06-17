package com.ictdemy.vibeup.controller;

import com.ictdemy.vibeup.dto.RegisterRequest;
import com.ictdemy.vibeup.dto.RegisterResponse;
import com.ictdemy.vibeup.dto.UserDTO;
import com.ictdemy.vibeup.dto.UpdateProfileRequest;
import com.ictdemy.vibeup.model.User;
import com.ictdemy.vibeup.repository.UserRepository;
import com.ictdemy.vibeup.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;  // JwtUtil injection

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new RegisterResponse("This email is already registered.", request.getEmail()));
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBirthDate(request.getBirthDate());
        user.setRole("USER");

        userRepository.save(user);

        return ResponseEntity.ok(new RegisterResponse("Registration successful", user.getEmail()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok(toDTO(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody UpdateProfileRequest updatedProfile) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getName() == null) {
            return ResponseEntity.status(401).body("You are not logged in.");
        }

        Long userId;
        try {
            userId = Long.parseLong(auth.getName());
        } catch (NumberFormatException e) {
            return ResponseEntity.status(400).body("Invalid user ID.");
        }

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }

        User user = optionalUser.get();

        if (updatedProfile.getEmail() != null && !updatedProfile.getEmail().equals(user.getEmail())) {
            if (userRepository.findByEmail(updatedProfile.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("This email is already in use.");
            }
            user.setEmail(updatedProfile.getEmail());
        }

        if (updatedProfile.getName() != null) {
            user.setName(updatedProfile.getName());
        }

        if (updatedProfile.getAvatarUrl() != null) {
            user.setAvatarUrl(updatedProfile.getAvatarUrl());
        }

        if (updatedProfile.getBirthDate() != null) {
            user.setBirthDate(updatedProfile.getBirthDate());
        }

        if (updatedProfile.getPassword() != null && !updatedProfile.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(updatedProfile.getPassword()));
        }

        userRepository.save(user);

        // New token generalization
        String newToken = jwtUtil.generateToken(user);

        // Return the new user data and the token
        return ResponseEntity.ok(new UpdateProfileResponse(toDTO(user), newToken));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> searchUsers(@RequestParam("q") String query) {
        List<UserDTO> users = userRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getAvatarUrl(),
                user.getBirthDate()
        );
    }

    // DTO class for response
    public static class UpdateProfileResponse {
        private UserDTO user;
        private String token;

        public UpdateProfileResponse(UserDTO user, String token) {
            this.user = user;
            this.token = token;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
