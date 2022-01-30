package com.intpfy.test.core_regression.web_panel.speaker.speaker_webmeet_classroom_with_chairperson;

import com.intpfy.authorization.Authorizer;
import com.intpfy.gui.dialogs.speaker.SelectFileRecipientsRolesDW;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.event.Event;
import com.intpfy.model.event.Role;
import com.intpfy.model.event.Toggles;
import com.intpfy.test.BaseWebTest;
import com.intpfy.test.FileSharingTest;
import com.intpfy.util.RandomUtil;
import com.intpfy.verifiers.event.audience.AudienceVerifier;
import com.intpfy.verifiers.event.interpreter.InterpreterVerifier;
import com.intpfy.verifiers.event.speaker.SpeakerVerifier;
import com.intpfyqa.test_rail.TestRailCase;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.intpfy.model.event.Role.*;
import static com.intpfy.test.FileSharingTest.*;
import static com.intpfy.test.TestGroups.*;

public class HostCanChooseRolesToWhomSendFilesTest extends BaseWebTest implements FileSharingTest {

    private final Authorizer authorizer = Authorizer.getInstance();

    private final Toggles toggles = new Toggles.Builder()
            .withDocumentSharing(true)
            .build();

    // Create 'Connect Pro (Classroom)' event with enabled Document sharing
    // and enabled access to chat for Audience and Interpreter.
    private final Event event = Event
            .createConnectProClassroomBuilder()
            .withToggles(toggles)
            .withAudienceAccessToChat(true)
            .withInterpreterBlockToTypeInChat(false)
            .build();


    @Override
    public Event getEvent() {
        return event;
    }

    @Test(
            testName = "Check that HOST can choose roles to whom send files",
            description = "Host can choose Roles to whom send Files.",
            groups = {
                    ONE_USER,
                    EVENT,
                    AUDIENCE,
                    INTERPRETER,
                    SPEAKER
            }
    )
    @TestRailCase("2074")
    public void test() {

        // Log in as Host with Meeting control only.

        String hostName = RandomUtil.createRandomHostName();
        SpeakerPage hostPage = authorizer.logInAsHostWithMeetingControlOnly(event, hostName);
        hostPage.assertIsOpened();

        // Switch Event chat to Files.

        hostPage.switchEventChatToFiles();

        SpeakerVerifier hostVerifier = new SpeakerVerifier(hostPage);

        hostVerifier.assertEventChatIsOfFilesType();

        // Upload file.

        SelectFileRecipientsRolesDW selectFileRecipientsRolesDW = hostPage.uploadFile(getSelenoidTestFilePath());
        selectFileRecipientsRolesDW.assertIsOpened();

        // Select File recipients Roles.

        Map<Role, Boolean> roles = new HashMap<>();

        roles.put(Attendee, true);
        roles.put(Interpreter, false);
        roles.put(Delegate, true);

        selectFileRecipientsRolesDW.selectRoles(roles);

        // Send File.

        selectFileRecipientsRolesDW.send();
        selectFileRecipientsRolesDW.assertNotVisible();

        // Check that file Scanning for some time.

        String fileName = getFileNameFromPath(getSelenoidTestFilePath());

        hostVerifier.assertFilePresent(fileName);

        hostVerifier.assertFileScanning(fileName);
        hostVerifier.assertFileNotScanning(fileName);

        // Check File Roles.

        hostVerifier.assertFileRolesEqual(fileName, getSelectedRoles(roles));

        // Log in as Speaker.

        String speakerName = RandomUtil.createRandomSpeakerName();
        SpeakerPage speakerPage = authorizer.logInAsSpeaker(event, speakerName);
        speakerPage.assertIsOpened();

        // Check that Event chat enabled.

        SpeakerVerifier speakerVerifier = new SpeakerVerifier(speakerPage);

        speakerVerifier.assertEventChatEnabled();

        // Switch Event chat to Files.

        speakerPage.switchEventChatToFiles();
        speakerVerifier.assertEventChatIsOfFilesType();

        // Check that File present and available for Download.

        speakerVerifier.assertFilePresent(fileName);
        speakerVerifier.assertFileAvailableForDownload(fileName);

        // Log in as Audience.

        String audienceName = RandomUtil.createRandomAudienceName();
        AudiencePage audiencePage = authorizer.logInAsAudienceWithUsername(event, audienceName);
        audiencePage.assertIsOpened();

        // Select Source language channel.

        audiencePage.selectSourceChannel();

        AudienceVerifier audienceVerifier = new AudienceVerifier(audiencePage);

        audienceVerifier.assertConnectedToSourceChannel();

        // Check that Event chat enabled.

        audienceVerifier.assertEventChatEnabled();

        // Switch Event chat to Files.

        audiencePage.switchEventChatToFiles();
        audienceVerifier.assertEventChatIsOfFilesType();

        // Check that File present and available for Download.

        audienceVerifier.assertFilePresent(fileName);
        audienceVerifier.assertFileAvailableForDownload(fileName);

        // Log in as Interpreter.

        String interpreterName = RandomUtil.createRandomInterpreterName();
        InterpreterPage interpreterPage = authorizer.logInAsInterpreter(event, interpreterName);
        interpreterPage.assertIsOpened();

        // Check that Event chat enabled.

        InterpreterVerifier interpreterVerifier = new InterpreterVerifier(interpreterPage);

        interpreterVerifier.assertEventChatEnabled();

        // Switch Event chat to Files.

        interpreterPage.switchEventChatToFiles();
        interpreterVerifier.assertEventChatIsOfFilesType();

        // Check that File not present.

        interpreterVerifier.assertFileNotPresent(fileName);

        throwErrorIfVerificationsFailed();
    }
}
