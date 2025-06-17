package com.ictdemy.vibeup.repository;

import com.ictdemy.vibeup.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findByPostId(Long postId);  // Query all reactions to the given post
}
