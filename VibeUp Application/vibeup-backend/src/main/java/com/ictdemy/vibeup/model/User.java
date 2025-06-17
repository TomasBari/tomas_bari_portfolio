package com.ictdemy.vibeup.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
public class User {

    public User() {
        // Empty constructor for Spring/Hibernate
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "The name cannot be empty.")
    @Size(min = 2, max = 50, message = "The name can be a minimum of 2 and a maximum of 50 characters long.")
    private String name;

    @NotBlank(message = "The email cannot be empty.")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @NotBlank(message = "The password cannot be empty.")
    @Size(min = 6, message = "The password must be at least 6 characters long.")
    private String password;

    private String role;

    @Column(name = "avatar_url")
    private String avatarUrl;  // New field for profile picture

    @Column(name = "birth_date")
    private LocalDate birthDate; // Add date of birth

    // Constructor
    public User(String name, String email, String password, String role, String avatarUrl, LocalDate birthDate) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.avatarUrl = avatarUrl;
        this.birthDate = birthDate;
    }

    // Getter & setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public LocalDate getBirthDate(){
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate){
        this.birthDate = birthDate;
    }
}
