package com.ictdemy.vibeup.repository;

import com.ictdemy.vibeup.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>{
}
