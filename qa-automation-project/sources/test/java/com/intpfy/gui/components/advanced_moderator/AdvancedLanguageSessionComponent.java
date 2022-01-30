package com.intpfy.gui.components.advanced_moderator;

import com.intpfy.gui.components.moderator.LanguageSessionComponent;
import com.intpfy.gui.components.moderator.UsersComponent;
import com.intpfy.gui.dialogs.moderator.SetActiveChannelsDW;
import com.intpfy.gui.dialogs.moderator.UsersSessionDW;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

import static com.intpfy.util.XpathUtil.createEqualsTextIgnoreCaseLocator;

public class AdvancedLanguageSessionComponent extends LanguageSessionComponent {

    private final RecordingComponent recording;
    private final UsersComponent users;

    public AdvancedLanguageSessionComponent(Language language, IParent parent) {
        super(String.format("'%s' language session component", language), parent,
                By.xpath("//div[contains(@class, 'session-item') and .//div[" + createEqualsTextIgnoreCaseLocator(language.name()) + "]]"));
        recording = new RecordingComponent(this);
        users = new UsersComponent(this, language);
    }

    public void showChat() {
        partnerChat.show();
    }

    public void hideChat() {
        partnerChat.hide();
    }

    public boolean isChatShown(Duration timeout) {
        return partnerChat.isShown(timeout);
    }

    public boolean isChatHidden(Duration timeout) {
        return partnerChat.isHidden(timeout);
    }

    public void enableRecording() {
        recording.enable();
    }

    public void disableRecording() {
        recording.disable();
    }

    public boolean isRecordingEnabled(Duration timeout) {
        return recording.isEnabled(timeout);
    }

    public boolean isRecordingDisabled(Duration timeout) {
        return recording.isDisabled(timeout);
    }

    public boolean isUserPresent(String username, Duration timeout) {
        return users.isPresent(username, timeout);
    }

    public boolean userCanStream(String username, Duration timeout) {
        return users.canStream(username, timeout);
    }

    public boolean userCanListen(String username, Duration timeout) {
        return users.canListen(username, timeout);
    }

    public boolean userHasIncomingLanguage(String username, Language language, Duration timeout) {
        return users.hasIncomingLanguage(username, language, timeout);
    }

    public boolean userHasOutgoingLanguage(String username, Language language, Duration timeout) {
        return users.hasOutgoingLanguage(username, language, timeout);
    }

    public boolean isUserMuted(String username, Duration timeout) {
        return users.isMuted(username, timeout);
    }

    public boolean isUserUnmuted(String username, Duration timeout) {
        return users.isUnmuted(username, timeout);
    }

    public boolean isStreamingMultiple(Duration timeout) {
        return users.isStreamingMultiple(timeout);
    }

    public boolean isStreamingNotMultiple(Duration timeout) {
        return users.isStreamingNotMultiple(timeout);
    }

    public int getUserOrdinalPosition(String username) {
        return users.getOrdinalPosition(username);
    }

    public SetActiveChannelsDW openSetActiveChannelsDW(String username) {
        return users.openSetActiveChannelsDW(username);
    }

    public UsersSessionDW openUsersSessionDW() {
        return users.openUsersSessionDW();
    }
}
