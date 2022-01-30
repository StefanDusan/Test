package com.intpfy.test.core_regression.web_panel.moderator_advanced_mode_event_pro_or_webmeet;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.emi.EventArchivesDW;
import com.intpfy.gui.dialogs.moderator.EventRecordingAvailableDW;
import com.intpfy.gui.dialogs.moderator.RecordingNotificationDW;
import com.intpfy.gui.dialogs.moderator.RecordingSettingsDW;
import com.intpfy.gui.pages.emi.DashboardPage;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.gui.pages.event.AdvancedModeratorPage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.EventTest;
import com.intpfy.test.FileTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.util.UserUtil;
import com.intpfy.util.WebContextUtil;
import com.intpfy.verifiers.emi.EventsPageVerifier;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.moderator.AdvancedModeratorVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.run.IWebApplicationContext;
import com.intpfyqa.test_rail.TestRailBugId;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import java.io.File;
import java.nio.file.Paths;

import static com.intpfy.test.TestGroups.*;

public class RecordSessionTest extends BaseWebTest implements EventTest, FileTest {

    private static final char NAME_SEPARATOR = File.separatorChar;
    private static final String SUB_DIR = "recordings";
    private static final String FULL_DOWNLOAD_PATH = Paths.get("FileStorage" + NAME_SEPARATOR + SUB_DIR).toAbsolutePath().toString();
    private static final String SOURCE_FILE_NAME = RandomUtil.getRandomNumericStringWithPrefix("S");
    private static final String LANGUAGE_FILE_NAME = RandomUtil.getRandomNumericStringWithPrefix("L");
    private static final File SOURCE_FILE = new File(FULL_DOWNLOAD_PATH + NAME_SEPARATOR + SOURCE_FILE_NAME);
    private static final File LANGUAGE_FILE = new File(FULL_DOWNLOAD_PATH + NAME_SEPARATOR + LANGUAGE_FILE_NAME);

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Language language = Language.getRandomLanguage();

    // Create 'Connect (WebMeet)' event with 1 Language and enabled Recording panel for Moderator.
    private final Event event = Event
            .createConnectWebMeetBuilder()
            .withLanguage(language)
            .withEnableModeratorRecording(true)
            .build();

    @Override
    public Event getEvent() {
        return event;
    }

    @Override
    public File getFile() {
        return new File(FULL_DOWNLOAD_PATH);
    }

    @Test(
            enabled = false,
            testName = "Check the record session",
            description = "Moderator can record Speaker and Interpreter sessions and recordings saved to Event archives.",
            groups = {
                    THREE_USERS,
                    MEDIA_DEVICES_REQUIRED,
                    EMI,
                    EVENT,
                    INTERPRETER,
                    SPEAKER,
                    MODERATOR
            }
    )
    @TestRailCase("1554")
    @TestRailBugId("CORE-3338")
    public void test() {

        // Log in as Moderator with Advanced monitoring.

        String moderatorName = RandomUtil.createRandomModeratorName();
        AdvancedModeratorPage moderatorPage = authorizer.logInAsModeratorWithAdvancedMonitoring(event, moderatorName);
        moderatorPage.assertIsOpened();

        // Close 'Event recording available' dialog window.

        EventRecordingAvailableDW eventRecordingAvailableDW = new EventRecordingAvailableDW(moderatorPage);
        eventRecordingAvailableDW.assertIsOpened();

        eventRecordingAvailableDW.close();
        eventRecordingAvailableDW.assertNotVisible();

        // Check that Recording for Source and Language session disabled.

        AdvancedModeratorVerifier moderatorVerifier = new AdvancedModeratorVerifier(moderatorPage);

        moderatorVerifier.assertRecordingForSourceSessionDisabled();
        moderatorVerifier.assertRecordingForLanguageSessionDisabled(language);

        // Log in as Interpreter with Outgoing language and start streaming.

        IWebApplicationContext interpreterContext = WebContextUtil.switchToNewContext();

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName, language);
        interpreterPage.assertIsOpened();

        interpreterPage.unmute();

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);
        interpreterVerifier.assertStreaming();

        // Log in as Speaker with Audio and Video.

        IWebApplicationContext speakerContext = WebContextUtil.switchToNewContext();

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeakerWithAudioAndVideo(event, speakerName);
        speakerPage.assertIsOpened();

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);
        speakerVerifier.assertStreamWithAudioAndVideoGoingOnUI();

        // Switch to Moderator and enable Recording for Source and Language session.

        WebContextUtil.switchToDefaultContext();

        moderatorPage.enableRecordingForSourceSession();
        moderatorPage.enableRecordingForLanguageSession(language);

        // Check that Recording for Source and Language session enabled.

        moderatorVerifier.assertRecordingForSourceSessionEnabled();
        moderatorVerifier.assertRecordingForLanguageSessionEnabled(language);

        // Open 'Recording settings' dialog window.

        RecordingSettingsDW recordingSettingsDW = moderatorPage.openRecordingSettings();
        recordingSettingsDW.assertIsOpened();

        // Check that Recording for Source and Language session enabled in dialog window.

        moderatorVerifier.assertRecordingForSourceSessionEnabled(recordingSettingsDW);
        moderatorVerifier.assertRecordingForLanguageSessionEnabled(recordingSettingsDW, language);

        // Disable recording for Source session.

        RecordingNotificationDW recordingNotificationDW = recordingSettingsDW.disableRecordingForSourceSession();
        recordingNotificationDW.assertIsOpened();

        recordingNotificationDW.confirm();
        recordingNotificationDW.assertNotVisible();

        // Disable recording for Language session.

        recordingNotificationDW = recordingSettingsDW.disableRecordingForLanguageSession(language);
        recordingNotificationDW.assertIsOpened();

        recordingNotificationDW.confirm();
        recordingNotificationDW.assertNotVisible();

        // Check that Recording for Source and Language session disabled in dialog window.

        moderatorVerifier.assertRecordingForSourceSessionDisabled(recordingSettingsDW);
        moderatorVerifier.assertRecordingForLanguageSessionDisabled(recordingSettingsDW, language);

        // Close dialog window.

        recordingSettingsDW.close();
        recordingSettingsDW.assertNotVisible();

        interpreterContext.close();
        speakerContext.close();

        // Check that Recording for Source and Language session disabled.

        moderatorVerifier.assertRecordingForSourceSessionDisabled();
        moderatorVerifier.assertRecordingForLanguageSessionDisabled(language);

        // Log in as Organization.

        DashboardPage mainPage = authorizer.logInToEMI(UserUtil.getEventOrganizationUser());
        mainPage.assertIsOpened();

        // Proceed to 'Events' page.

        EventsPage eventsPage = mainPage.goToEventsPage();
        eventsPage.assertIsOpened();

        // Open 'Event archives' dialog window.

        EventArchivesDW eventArchivesDW = eventsPage.openEventArchives(event);
        eventArchivesDW.assertIsOpened();

        // Check that there is 1 Media file for Source.

        eventArchivesDW.expandMediaFilesListForSource();

        EventsPageVerifier eventsPageVerifier = new EventsPageVerifier(eventsPage);

        int sourceExpectedMediaFilesCount = 1;
        eventsPageVerifier.verifyMediaFilesCountForSource(eventArchivesDW, sourceExpectedMediaFilesCount);

        // Check that there is 1 Media file for Language.

        eventArchivesDW.expandMediaFilesListForLanguage(language);

        int languageExpectedMediaFilesCount = 1;
        eventsPageVerifier.verifyMediaFilesCountForLanguage(eventArchivesDW, languageExpectedMediaFilesCount, language);

        // Download Source and Language Media files.

        eventArchivesDW.downloadMediaFileToFileStorageForSource(1, SOURCE_FILE_NAME, SUB_DIR);
        eventArchivesDW.downloadMediaFileToFileStorageForLanguage(1, LANGUAGE_FILE_NAME, SUB_DIR, language);

        eventsPageVerifier.verifySourceRecordingFileExists(SOURCE_FILE);
        eventsPageVerifier.verifyLanguageRecordingFileExists(LANGUAGE_FILE, language);

        // Close 'Event archives' dialog window.

        eventArchivesDW.close();
        eventArchivesDW.assertNotVisible();

        throwErrorIfVerificationsFailed();
    }
}
