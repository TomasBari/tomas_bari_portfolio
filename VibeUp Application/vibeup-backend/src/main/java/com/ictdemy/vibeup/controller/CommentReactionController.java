package com.ictdemy.vibeup.controller;

import com.ictdemy.vibeup.model.CommentReaction;
import com.ictdemy.vibeup.repository.CommentReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment-reactions")
public class CommentReactionController {

    @Autowired
    private CommentReactionRepository commentReactionRepository;

    // Add reaction to a comment
    @PostMapping
    public CommentReaction createCommentReaction(@RequestBody CommentReaction commentReaction) {
        // Check if the user has already responded to this comment
        List<CommentReaction> existingReactions = commentReactionRepository.findAll().stream()
                .filter(reaction -> reaction.getCommentId().equals(commentReaction.getCommentId()) &&
                        reaction.getUserId().equals(commentReaction.getUserId()))
                .toList();

        if (!existingReactions.isEmpty()) {
            // If there is already a reaction we will modify it
            CommentReaction existingReaction = existingReactions.get(0);
            existingReaction.setEmoji(commentReaction.getEmoji());
            return commentReactionRepository.save(existingReaction);
        } else {
            // If there is no reaction yet, we create a new reaction
            return commentReactionRepository.save(commentReaction);
        }
    }

    // Delete reaction
    @DeleteMapping("/{id}")
    public void deleteCommentReaction(@PathVariable Long id) {
        commentReactionRepository.deleteById(id);
    }

    // Modify reaction
    @PutMapping("/{id}")
    public CommentReaction updateCommentReaction(@PathVariable Long id, @RequestBody CommentReaction updatedReaction) {
        return commentReactionRepository.findById(id)
                .map(reaction -> {
                    reaction.setEmoji(updatedReaction.getEmoji());
                    return commentReactionRepository.save(reaction);
                })
                .orElseThrow(() -> new RuntimeException("Reaction not found with id " + id));
    }
}
