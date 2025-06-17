package com.ictdemy.vibeup.controller;

import com.ictdemy.vibeup.model.Reaction;
import com.ictdemy.vibeup.model.PostReactionSummary;
import com.ictdemy.vibeup.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private ReactionRepository reactionRepository;

    // Summarize reactions to a given post
    @GetMapping("/post/{postId}/summary")
    public PostReactionSummary getPostReactionsSummary(@PathVariable Long postId) {
        // Query for all reactions for post
        List<Reaction> reactions = reactionRepository.findAll();

        // Filter reactions based on postID
        Map<String, Integer> reactionCounts = new HashMap<>();
        for (Reaction reaction : reactions) {
            if (reaction.getPostId().equals(postId)) {
                String emoji = reaction.getEmojiType().getLabel();
                reactionCounts.put(emoji, reactionCounts.getOrDefault(emoji, 0) + 1);
            }
        }

        // Return summary of reactions
        return new PostReactionSummary(reactionCounts);
    }

    // Summarize reactions to a given comment
    @GetMapping("/comment/{commentId}/summary")
    public PostReactionSummary getCommentReactionsSummary(@PathVariable Long commentId) {
        // Query for all reactions to the comment
        List<Reaction> reactions = reactionRepository.findAll();

        // Filter reactions based on commentID
        Map<String, Integer> reactionCounts = new HashMap<>();
        for (Reaction reaction : reactions) {
            if (reaction.getPostId().equals(commentId)) {
                String emoji = reaction.getEmojiType().getLabel();
                reactionCounts.put(emoji, reactionCounts.getOrDefault(emoji, 0) + 1);
            }
        }

        // Return summary of reactions
        return new PostReactionSummary(reactionCounts);
    }
}
