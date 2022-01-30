package com.intpfy.test;

import com.intpfyqa.settings.LobbySettings;

public interface LobbyTest extends EventTest {

    LobbySettings SETTINGS = LobbySettings.instance();

    static String getDomain() {
        return SETTINGS.getDomain();
    }
}
