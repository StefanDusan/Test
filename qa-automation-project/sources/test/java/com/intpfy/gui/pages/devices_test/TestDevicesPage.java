package com.intpfy.gui.pages.devices_test;

import com.intpfy.gui.complex_elements.PageDropdown;
import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfy.gui.components.common.VolumeBarComponent;
import com.intpfy.gui.dialogs.common.LanguageSettingsDW;
import com.intpfy.gui.pages.LoginPage;
import com.intpfy.gui.pages.event.AudiencePage;
import com.intpfy.gui.pages.event.InterpreterPage;
import com.intpfy.gui.pages.event.SpeakerPage;
import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

import static com.intpfy.util.XpathUtil.createContainsTextIgnoreCaseLocator;

public class TestDevicesPage extends BaseAutomationPage {

    @ElementInfo(name = "Back", findBy = @FindBy(className = "precall-test__link-previous"))
    private Button backButton;

    private final VideoTestContainer videoTestContainer;
    private final PreCallTestSettingsComponent preCallTestSettings;

    public TestDevicesPage(IPageContext pageContext) {
        super("Test devices", pageContext);
        videoTestContainer = new VideoTestContainer(this);
        preCallTestSettings = new PreCallTestSettingsComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return preCallTestSettings.visible(timeout);
    }

    public LoginPage back() {
        info("Back.");
        backButton.click();
        return new LoginPage(getPageContext());
    }

    public void selectLanguage(Language language) {
        info(String.format("Select '%s' language.", language));
        preCallTestSettings.selectLanguage(language);
    }

    public boolean isLanguageSelected(Language language, Duration timeout) {
        return preCallTestSettings.isLanguageSelected(language, timeout);
    }

    public boolean isCameraToggleAvailable(Duration timeout) {
        return preCallTestSettings.isCameraToggleAvailable(timeout);
    }

    public boolean isMicrophoneToggleAvailable(Duration timeout) {
        return preCallTestSettings.isMicrophoneToggleAvailable(timeout);
    }

    public boolean isAudioDeviceSelected(Duration timeout) {
        return preCallTestSettings.isAudioDeviceSelected(timeout);
    }

    public void turnCameraOff() {
        info("Turn Camera OFF.");
        preCallTestSettings.turnCameraOff();
    }

    public void turnMicrophoneOff() {
        info("Turn Microphone OFF.");
        preCallTestSettings.turnMicrophoneOff();
    }

    public boolean isCameraOff(Duration timeout) {
        return preCallTestSettings.isCameraOff(timeout) && videoTestContainer.isAudioOnly(timeout);
    }

    public boolean isMicrophoneOff(Duration timeout) {
        return preCallTestSettings.isMicrophoneOff(timeout);
    }

    public String getSelectedCameraName() {
        return preCallTestSettings.getSelectedCameraName();
    }

    public String getSelectedMicrophoneName() {
        return preCallTestSettings.getSelectedMicrophoneName();
    }

    public String getSelectedAudioDeviceName() {
        return preCallTestSettings.getSelectedAudioDeviceName();
    }

    public String getSelectedCameraTooltip() {
        return preCallTestSettings.getSelectedCameraTooltip();
    }

    public String getSelectedMicrophoneTooltip() {
        return preCallTestSettings.getSelectedMicrophoneTooltip();
    }

    public String getSelectedAudioDeviceTooltip() {
        return preCallTestSettings.getSelectedAudioDeviceTooltip();
    }

    public String getCameraDropdownTooltip(String cameraName) {
        return preCallTestSettings.getCameraDropdownTooltip(cameraName);
    }

    public String getMicrophoneDropdownTooltip(String microphoneName) {
        return preCallTestSettings.getMicrophoneDropdownTooltip(microphoneName);
    }

    public String getAudioDeviceDropdownTooltip(String audioDeviceName) {
        return preCallTestSettings.getAudioDeviceDropdownTooltip(audioDeviceName);
    }

    public boolean isConnectionTestGoing(Duration timeout) {
        return preCallTestSettings.isConnectionTestGoing(timeout);
    }

    public boolean isConnectionTestFinished(Duration timeout) {
        return preCallTestSettings.isConnectionTestFinished(timeout);
    }

    public boolean isVideoConnectionTestResultAvailable(Duration timeout) {
        return preCallTestSettings.isVideoConnectionTestResultAvailable(timeout);
    }

    public boolean isMicrophoneConnectionTestResultAvailable(Duration timeout) {
        return preCallTestSettings.isMicrophoneConnectionTestResultAvailable(timeout);
    }

    public boolean isSpeakerConnectionTestResultAvailable(Duration timeout) {
        return preCallTestSettings.isSpeakerConnectionTestResultAvailable(timeout);
    }

    public boolean isNetworkConnectionTestResultAvailable(Duration timeout) {
        return preCallTestSettings.isNetworkConnectionTestResultAvailable(timeout);
    }

    public void restartConnectionTest() {
        preCallTestSettings.restartConnectionTest();
    }

    public AudiencePage joinCallAsAudience() {
        info("Join Call as Audience.");
        joinCall();
        return new AudiencePage(getPageContext());
    }

    public LanguageSettingsDW joinCallAsInterpreter() {
        info("Join Call as Interpreter.");
        joinCall();
        return new LanguageSettingsDW(new InterpreterPage(getPageContext()));
    }

    public SpeakerPage joinCallAsSpeaker() {
        info("Join Call as Speaker.");
        joinCall();
        return new SpeakerPage(getPageContext());
    }

    private void joinCall() {
        preCallTestSettings.joinCall();
    }

    private static class VideoTestContainer extends BaseComponent {

        @ElementInfo(name = "Root", findBy = @FindBy(css = "div.OT_root"))
        private Element rootElement;

        private VideoTestContainer(IParent parent) {
            super("Video test container", parent, By.id("video-test-container"));
        }

        private boolean isAudioOnly(Duration timeout) {
            return rootElement.waitCssClassContains("audio-only", timeout);
        }
    }

    private static class PreCallTestSettingsComponent extends BaseComponent {

        private final LanguageComponent language;

        private final CameraComponent camera;
        private final MicrophoneComponent microphone;
        private final AudioDeviceComponent audioDevice;

        private final ConnectionTestComponent connectionTest;

        @ElementInfo(name = "Join call", findBy = @FindBy(className = "precall-test__submit-button"))
        private Button joinCallButton;

        private PreCallTestSettingsComponent(IParent parent) {

            super("Pre call test settings", parent, By.cssSelector("div.precall-test__settings"));

            language = new LanguageComponent(this);

            camera = new CameraComponent(this);
            microphone = new MicrophoneComponent(this);
            audioDevice = new AudioDeviceComponent(this);

            connectionTest = new ConnectionTestComponent(this);
        }

        private void selectLanguage(Language language) {
            this.language.select(language);
        }

        private boolean isLanguageSelected(Language language, Duration timeout) {
            return this.language.isSelected(language, timeout);
        }

        private boolean isCameraToggleAvailable(Duration timeout) {
            return camera.isToggleAvailable(timeout);
        }

        private boolean isMicrophoneToggleAvailable(Duration timeout) {
            return microphone.isToggleAvailable(timeout);
        }

        private boolean isAudioDeviceSelected(Duration timeout) {
            return audioDevice.isSelected(timeout);
        }

        private void turnCameraOff() {
            camera.off();
        }

        private void turnMicrophoneOff() {
            microphone.off();
        }

        private boolean isCameraOff(Duration timeout) {
            return camera.isOff(timeout);
        }

        private boolean isMicrophoneOff(Duration timeout) {
            return microphone.isOff(timeout);
        }

        private String getSelectedCameraName() {
            return camera.getSelectedName();
        }

        private String getSelectedMicrophoneName() {
            return microphone.getSelectedName();
        }

        private String getSelectedAudioDeviceName() {
            return audioDevice.getSelectedName();
        }

        private String getSelectedCameraTooltip() {
            return camera.getSelectedTooltip();
        }

        private String getSelectedMicrophoneTooltip() {
            return microphone.getSelectedTooltip();
        }

        private String getSelectedAudioDeviceTooltip() {
            return audioDevice.getSelectedTooltip();
        }

        private String getCameraDropdownTooltip(String cameraName) {
            return camera.getDropdownTooltip(cameraName);
        }

        private String getMicrophoneDropdownTooltip(String microphoneName) {
            return microphone.getDropdownTooltip(microphoneName);
        }

        private String getAudioDeviceDropdownTooltip(String audioDeviceName) {
            return audioDevice.getDropdownTooltip(audioDeviceName);
        }

        private boolean isConnectionTestGoing(Duration timeout) {
            return connectionTest.isGoing(timeout);
        }

        private boolean isConnectionTestFinished(Duration timeout) {
            return connectionTest.isFinished(timeout);
        }

        private boolean isVideoConnectionTestResultAvailable(Duration timeout) {
            return connectionTest.isVideoResultAvailable(timeout);
        }

        private boolean isMicrophoneConnectionTestResultAvailable(Duration timeout) {
            return connectionTest.isMicrophoneResultAvailable(timeout);
        }

        private boolean isSpeakerConnectionTestResultAvailable(Duration timeout) {
            return connectionTest.isSpeakerResultAvailable(timeout);
        }

        private boolean isNetworkConnectionTestResultAvailable(Duration timeout) {
            return connectionTest.isNetworkResultAvailable(timeout);
        }

        private void restartConnectionTest() {
            connectionTest.restart();
        }

        private void joinCall() {
            joinCallButton.click();
        }

        private abstract static class BaseSettingComponent extends BaseComponent {

            @ElementInfo(name = "Title", findBy = @FindBy(className = "precall-test__setting-title"))
            Element titleElement;

            private BaseSettingComponent(Name name, IParent parent) {
                super(name.toString(), parent,
                        By.xpath(".//div[@class = 'precall-test__setting-title' " +
                                "and " + createContainsTextIgnoreCaseLocator(name.toString()) + "]/parent::div"));
            }

            enum Name {

                Language("Language"),
                Camera("Camera"),
                Microphone("Microphone"),
                AudioDevice("Audio on"),
                Connection("Connection");

                private final String text;

                Name(String text) {
                    this.text = text;
                }

                @Override
                public String toString() {
                    return text;
                }
            }
        }

        private static class LanguageComponent extends BaseSettingComponent {

            private final PageDropdown settingDropdown;

            private LanguageComponent(IParent parent) {
                super(Name.Language, parent);
                settingDropdown = new PageDropdown(Name.Language.toString(), this, By.cssSelector("div.precall-test__setting-content"));
            }

            private void select(Language language) {
                settingDropdown.selectContainsText(language.name());
            }

            private boolean isSelected(Language language, Duration timeout) {
                return settingDropdown.isSelectedContainsText(language.name(), timeout);
            }
        }

        private abstract static class MediaSettingComponent extends BaseSettingComponent {

            @ElementInfo(name = "Tooltip", findBy = @FindBy(css = "div.tooltip-inner"))
            private Element tooltipElement;

            @ElementInfo(name = "ON / OFF toggle", findBy = @FindBy(className = "toggle-switch"))
            private ComplexCheckbox onOffToggle;

            private final PageDropdown settingDropdown;

            private MediaSettingComponent(Name name, IParent parent) {
                super(name, parent);
                settingDropdown = new PageDropdown(name.toString(), this, By.cssSelector("div.precall-test__setting-content"));
            }

            boolean isToggleAvailable(Duration timeout) {
                return onOffToggle.getComponentElement().waitCssClassNotContains("_disabled", timeout);
            }

            void off() {
                onOffToggle.unselect();
            }

            boolean isOff(Duration timeout) {
                return onOffToggle.waitIsNotSelected(timeout) && titleElement.waitForTextContains("off", timeout);
            }

            boolean isSelected(Duration timeout) {
                return settingDropdown.isSelected(timeout);
            }

            String getSelectedName() {
                return settingDropdown.getSelected();
            }

            String getSelectedTooltip() {
                settingDropdown.getComponentElement().moveMouseIn();
                tooltipElement.visible();
                return tooltipElement.getText().trim();
            }

            String getDropdownTooltip(String option) {
                return settingDropdown.getTooltip(option);
            }
        }

        private static class CameraComponent extends MediaSettingComponent {

            private CameraComponent(IParent parent) {
                super(Name.Camera, parent);
            }
        }

        private static class MicrophoneComponent extends MediaSettingComponent {

            private final VolumeBarComponent volumeBar;

            private MicrophoneComponent(IParent parent) {
                super(Name.Microphone, parent);
                volumeBar = new VolumeBarComponent(this);
            }

            @Override
            boolean isOff(Duration timeout) {
                return super.isOff(timeout) && isVolumeLevelNotChanging(timeout);
            }

            private boolean isVolumeLevelNotChanging(Duration timeout) {
                return volumeBar.isNotChanging(timeout);
            }
        }

        private static class AudioDeviceComponent extends MediaSettingComponent {

            private AudioDeviceComponent(IParent parent) {
                super(Name.AudioDevice, parent);
            }

            @Override
            boolean isToggleAvailable(Duration timeout) {
                return false;
            }
        }

        private static class ConnectionTestComponent extends BaseSettingComponent {

            private final ProgressBarComponent progressBar;

            private final ResultComponent videoResult;
            private final ResultComponent microphoneResult;
            private final ResultComponent speakerResult;
            private final ResultComponent networkResult;

            @ElementInfo(name = "Restart", findBy = @FindBy(className = "network-test__restart"))
            private Button restartButton;

            private ConnectionTestComponent(IParent parent) {

                super(Name.Connection, parent);

                progressBar = new ProgressBarComponent(this);

                videoResult = new ResultComponent(this, ResultType.Video);
                microphoneResult = new ResultComponent(this, ResultType.Microphone);
                speakerResult = new ResultComponent(this, ResultType.Speaker);
                networkResult = new ResultComponent(this, ResultType.Network);
            }

            private boolean isGoing(Duration timeout) {
                return progressBar.visible(timeout);
            }

            private boolean isFinished(Duration timeout) {
                return progressBar.notVisible(timeout);
            }

            private boolean isVideoResultAvailable(Duration timeout) {
                return videoResult.visible(timeout);
            }

            private boolean isMicrophoneResultAvailable(Duration timeout) {
                return microphoneResult.visible(timeout);
            }

            private boolean isSpeakerResultAvailable(Duration timeout) {
                return speakerResult.visible(timeout);
            }

            private boolean isNetworkResultAvailable(Duration timeout) {
                return networkResult.visible(timeout);
            }

            private void restart() {
                restartButton.click();
            }

            private static class ProgressBarComponent extends BaseComponent {

                private ProgressBarComponent(IParent parent) {
                    super("Progress bar", parent, By.cssSelector("div.precall-test-progress-bar"));
                }
            }

            private static class ResultComponent extends BaseComponent {

                private ResultComponent(IParent parent, ResultType type) {
                    super(type + " result", parent, By.xpath(".//div[@class = 'icon-list' and contains(., '" + type + "')]"));
                }
            }

            private enum ResultType {

                Video("Video"),
                Microphone("Microphone"),
                Speaker("Speaker"),
                Network("Network");

                private final String text;

                ResultType(String text) {
                    this.text = text;
                }

                @Override
                public String toString() {
                    return text;
                }
            }
        }
    }
}
