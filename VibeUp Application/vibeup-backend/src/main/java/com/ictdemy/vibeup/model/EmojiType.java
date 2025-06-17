package com.ictdemy.vibeup.model;

public enum EmojiType {
    LIKE("Like"),
    LOVE("Love"),
    WOW("Wow"),
    SAD("Sad"),
    HAHA("Haha"),
    ANGRY("Angry");

    private final String label;

    EmojiType(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }
}
