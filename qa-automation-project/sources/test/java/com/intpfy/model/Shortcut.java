package com.intpfy.model;

import org.openqa.selenium.Keys;

import static org.openqa.selenium.Keys.*;

public enum Shortcut {

    AcceptFloorWithAudioAndVideo(chord(ALT, "V")),
    AcceptFloorWithAudioOnly(chord(ALT, "Q")),
    ChangeIncomingLanguage(ARROW_DOWN),
    ChangeOutgoingLanguage(ARROW_LEFT),
    ConnectDisconnect(chord(ALT, "J")),
    DisableFullscreenInterface(ESCAPE),
    Disconnect(chord(ALT, "R")),
    Escape(ESCAPE),
    LogOut(chord(ALT, "M")),
    MuteUnmuteSourceAndLanguageChannels(chord(ALT, "Y")),
    OpenClosePrivateChat(chord(ALT, "P")),
    OpenCloseEventAndRemoteSupportHelpChats(chord(ALT, "A")),
    OpenCloseEventAndPartnerAndRemoteSupportHelpChats(chord(ALT, "A")),
    OpenRemoteSupportHelpChat(chord(ALT, "A")),
    RaiseLowerHand(chord(ALT, "H")),
    RejectFloor(chord(ALT, "Z")),
    SwitchAllSpeakersActiveSpeakerSetting(chord(ALT, "K")),
    SwitchCameraOnOff(chord(CONTROL, SHIFT)),
    SwitchMicOnOff(chord(CONTROL, SPACE)),
    SwitchVideoOnlyFullscreenModeOnOff(chord(ALT, "O"));

    private final String keysChord;

    Shortcut(String keysChord) {
        this.keysChord = keysChord;
    }

    Shortcut(Keys keys) {
        this.keysChord = chord(keys);
    }

    @Override
    public String toString() {
        return keysChord;
    }

    public String keysChord() {
        return keysChord;
    }
}
