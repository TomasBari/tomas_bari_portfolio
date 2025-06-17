package com.ictdemy.vibeup.controller;

import com.ictdemy.vibeup.dto.PostDTO;
import com.ictdemy.vibeup.model.Post;
import com.ictdemy.vibeup.model.Reaction;
import com.ictdemy.vibeup.repository.PostRepository;
import com.ictdemy.vibeup.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    // Query all posts with DTO
    @GetMapping
    public List<PostDTO> getAllPosts(){
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setId(post.getId());
                    dto.setContent(post.getContent());
                    dto.setCreatedAt(post.getCreatedAt());
                    if(post.getUser() != null) {
                        dto.setAuthorName(post.getUser().getName());
                        dto.setAvatarUrl(post.getUser().getAvatarUrl());
                    } else {
                        dto.setAuthorName("Unknown user");
                        dto.setAvatarUrl(null);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // Create new post
    @PostMapping
    public Post createPost(@RequestBody Post post){
        return postRepository.save(post);
    }

    // Query a single post by id
    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }

    // Delete post by id
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
    }

    // Modify post
    @PutMapping("/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody Post updatedPost) {
        return postRepository.findById(id)
                .map(post -> {
                    post.setTitle(updatedPost.getTitle());
                    post.setContent(updatedPost.getContent());
                    return postRepository.save(post);
                })
                .orElseGet(() -> {
                    updatedPost.setId(id);
                    return postRepository.save(updatedPost);
                });
    }

    // Add a reaction to a post (like, love, haha, etc.)
    @PostMapping("/{id}/reactions")
    public Reaction addReactionToPost(@PathVariable Long id, @RequestBody Reaction reaction) {
        reaction.setPostId(id);
        return reactionRepository.save(reaction);
    }

    // Get all reactions for a post
    @GetMapping("/{id}/reactions")
    public List<Reaction> getReactionsForPost(@PathVariable Long id) {
        return reactionRepository.findAll();  // Filtering later if necessary
    }
}
