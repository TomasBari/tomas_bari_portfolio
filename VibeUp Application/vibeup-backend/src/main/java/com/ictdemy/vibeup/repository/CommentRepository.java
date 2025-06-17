package com.ictdemy.vibeup.repository;

import com.ictdemy.vibeup.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
