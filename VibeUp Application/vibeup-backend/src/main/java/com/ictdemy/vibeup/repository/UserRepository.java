package com.ictdemy.vibeup.repository;

import com.ictdemy.vibeup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Find user by name, partial match, case-insensitive
    List<User> findByNameContainingIgnoreCase(String name);
}
