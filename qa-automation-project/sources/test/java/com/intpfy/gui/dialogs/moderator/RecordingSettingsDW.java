package com.intpfy.gui.dialogs.moderator;

import com.intpfy.gui.components.moderator.RecordingItemComponent;
import com.intpfy.gui.dialogs.Closeable;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import org.openqa.selenium.By;

import java.time.Duration;

public class RecordingSettingsDW extends BaseComponent implements Closeable {

    private final RecordingItemComponent mediaLibraryRecordingItem;
    private final RecordingItemComponent sourceRecordingItem;

    public RecordingSettingsDW(IParent parent) {
        super("Recording settings dialog window", parent, By.xpath("//div[@class='modal-content' and .//h4[text()='RECORDINGS']]"));
        mediaLibraryRecordingItem = new RecordingItemComponent(this, "Record event to Media Library");
        sourceRecordingItem = new RecordingItemComponent(this, Language.Source);
    }

    public void enableMediaLibraryRecording() {
        info("Enable Media Library recording.");
        mediaLibraryRecordingItem.enableRecording();
    }

    public void disableMediaLibraryRecording() {
        info("Disable Media Library recording.");
        mediaLibraryRecordingItem.disableRecording();
    }

    public boolean isMediaLibraryRecordingEnabled(Duration timeout) {
        return mediaLibraryRecordingItem.isRecordingEnabled(timeout);
    }

    public boolean isMediaLibraryRecordingDisabled(Duration timeout) {
        return mediaLibraryRecordingItem.isRecordingDisabled(timeout);
    }

    public void enableRecordingForSourceSession() {
        info("Enable recording for Source session.");
        sourceRecordingItem.enableRecording();
    }

    public RecordingNotificationDW disableRecordingForSourceSession() {
        info("Disable recording for Source session.");
        sourceRecordingItem.disableRecording();
        return new RecordingNotificationDW(getPage());
    }

    public void enableRecordingForLanguageSession(Language language) {
        info(String.format("Enable recording for '%s' language session.", language));
        createRecordingItemComponent(language).enableRecording();
    }

    public RecordingNotificationDW disableRecordingForLanguageSession(Language language) {
        info(String.format("Disable recording for '%s' language session.", language));
        createRecordingItemComponent(language).disableRecording();
        return new RecordingNotificationDW(getPage());
    }

    public boolean isRecordingForSourceSessionEnabled(Duration timeout) {
        return sourceRecordingItem.isRecordingEnabled(timeout);
    }

    public boolean isRecordingForSourceSessionDisabled(Duration timeout) {
        return sourceRecordingItem.isRecordingDisabled(timeout);
    }

    public boolean isRecordingForLanguageSessionEnabled(Language language, Duration timeout) {
        return createRecordingItemComponent(language).isRecordingEnabled(timeout);
    }

    public boolean isRecordingForLanguageSessionDisabled(Language language, Duration timeout) {
        return createRecordingItemComponent(language).isRecordingDisabled(timeout);
    }

    private RecordingItemComponent createRecordingItemComponent(Language language) {
        return new RecordingItemComponent(this, language);
    }
}
