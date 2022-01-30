package com.intpfyqa.test_rail;

public enum TestRailStatus {

    PASSED(1), BLOCKED(2), UNTESTED(3), RETEST(4), FAILED(5);

    private final int id;

    TestRailStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
