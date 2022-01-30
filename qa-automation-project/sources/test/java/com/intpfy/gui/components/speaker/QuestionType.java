package com.intpfy.gui.components.speaker;

public enum QuestionType {

    SingleType ("Single type"),
    MultipleType ("Multiple type");

    private final String type;

    QuestionType(String questionType) {
        this.type = questionType;
    }

    public String getType() {
        return type;
    }
}