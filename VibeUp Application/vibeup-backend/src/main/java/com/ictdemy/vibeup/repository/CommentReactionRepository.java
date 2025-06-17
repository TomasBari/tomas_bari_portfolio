package com.ictdemy.vibeup.repository;

import com.ictdemy.vibeup.model.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
}
