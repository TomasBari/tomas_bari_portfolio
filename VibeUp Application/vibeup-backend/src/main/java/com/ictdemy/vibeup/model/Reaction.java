package com.ictdemy.vibeup.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmojiType emojiType;  // Use the EmojiType enum

    private Long postId;  // Post ID they reacted to
    private Long userId;  // User who responded

    private LocalDateTime createdAt;

    public Reaction() {
        this.createdAt = LocalDateTime.now();  // Set the current time
    }

    // Getter & setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EmojiType getEmojiType() {
        return emojiType;
    }

    public void setEmojiType(EmojiType emojiType) {
        this.emojiType = emojiType;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
