package com.intpfy.model;

public enum Key {

    S(83),
    SPACE(32);

    private final int code;

    Key(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}