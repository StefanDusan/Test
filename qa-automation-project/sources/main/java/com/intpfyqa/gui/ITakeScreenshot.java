package com.intpfyqa.gui;

import com.intpfyqa.logging.ITakeSnapshot;
import com.intpfyqa.logging.Snapshot;

public interface ITakeScreenshot extends ITakeSnapshot {

    @Override
    default Snapshot takeSnapshot() {
        return takeScreenshot();
    }

    /**
     * Base64 string of PNG screenshot
     *
     * @return Base64 string of PNG screenshot
     */
    Snapshot takeScreenshot();
}

