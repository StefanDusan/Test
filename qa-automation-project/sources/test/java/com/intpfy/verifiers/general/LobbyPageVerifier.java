package com.intpfy.verifiers.general;

import com.intpfy.gui.pages.event.LobbyPage;
import com.intpfyqa.test.Verify;

import static com.intpfy.verifiers.general.LobbyPageVerificationMessages.LOBBY_MESSAGE_DISPLAYED;
import static com.intpfyqa.settings.WebSettings.AJAX_TIMEOUT;

public final class LobbyPageVerifier {

    private final LobbyPage page;

    public LobbyPageVerifier(LobbyPage page) {
        this.page = page;
    }

    public void assertMessageDisplayed() {
        Verify.assertTrue(page.isMessageDisplayed(AJAX_TIMEOUT), LOBBY_MESSAGE_DISPLAYED);
    }
}
