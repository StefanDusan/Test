package com.intpfy.model;

public enum VideoQuality {

    HD(1280, 720),
    High(640, 480),
    Low(320, 240);

    private final int width;
    private final int height;

    VideoQuality(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}