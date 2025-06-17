package com.ictdemy.vibeup.model;

import jakarta.persistence.*;

@Entity
public class CommentReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long commentId;  // The ID of the comment they are reacting to
    private Long userId;  // User ID of the person who responded
    private String emoji;  // Type of reaction (like, love, haha, etc.)

    public CommentReaction() {
    }

    // get/set
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }
}
