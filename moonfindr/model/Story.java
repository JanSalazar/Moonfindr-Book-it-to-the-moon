package com.tanion.aston.rovery.moonfindr.model;

/**
 * Created by Aston Tanion on 29/04/2016.
 */
public class Story {

    private int imageResId;
    private int storyResId;

    public Story(int imageResId, int storyResId) {
        this.imageResId = imageResId;
        this.storyResId = storyResId;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getStoryResId() {
        return storyResId;
    }
}
