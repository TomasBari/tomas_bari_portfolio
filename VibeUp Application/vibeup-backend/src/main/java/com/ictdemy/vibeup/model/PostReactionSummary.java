package com.ictdemy.vibeup.model;

import java.util.Map;

public class PostReactionSummary {
    private Map<String, Integer> reactionCounts;

    public PostReactionSummary(Map<String, Integer> reactionCounts) {
        this.reactionCounts = reactionCounts;
    }

    public Map<String, Integer> getReactionCounts() {
        return reactionCounts;
    }

    public void setReactionCounts(Map<String, Integer> reactionCounts) {
        this.reactionCounts = reactionCounts;
    }
}
