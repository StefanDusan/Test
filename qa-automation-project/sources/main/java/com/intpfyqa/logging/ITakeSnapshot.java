package com.intpfyqa.logging;

public interface ITakeSnapshot {

    /**
     * Take a snapshot of current application state
     *
     * @return Snapshot of application
     */
    Snapshot takeSnapshot();
}
