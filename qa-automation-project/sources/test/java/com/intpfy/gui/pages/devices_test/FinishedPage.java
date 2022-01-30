package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.BaseIFramePage;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class FinishedPage extends BaseDevicesTestPage {

    @ElementInfo(name = "Title", findBy = @FindBy(xpath = "//h1[contains(text(),'Finished')]"))
    private Element title;

    private final VideoPlayerIFramePage videoPlayerIFramePage;

    FinishedPage(IPageContext pageContext) {
        super("Finished", pageContext);
        videoPlayerIFramePage = new VideoPlayerIFramePage(getPageContext(), By.cssSelector("iframe.precall-test-tutorial__video"));
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return title.visible(timeout);
    }

    @Override
    public TestDevicesPage next() {
        info("Go to Test devices.");
        clickNext();
        return new TestDevicesPage(getPageContext());
    }

    @Override
    public TestDevicesPage skipTutorial() {
        throw new UnsupportedOperationException("'Skip tutorial' is not available on 'Finished' page.");
    }

    public void playVideo() {
        info("Play Video.");
        switchToIFrame(videoPlayerIFramePage);
        videoPlayerIFramePage.play();
        switchToDefaultContent();
    }

    public boolean isVideoPlaying(Duration timeout) {
        switchToIFrame(videoPlayerIFramePage);
        boolean playing = videoPlayerIFramePage.isPlaying(timeout);
        switchToDefaultContent();
        return playing;
    }

    private static class VideoPlayerIFramePage extends BaseIFramePage {

        private final VideoPlayer player;

        private VideoPlayerIFramePage(IPageContext pageContext, By iframeLocator) {
            super("Video player", pageContext, iframeLocator);
            player = new VideoPlayer(this);
        }

        @Override
        public boolean isOpened(Duration timeout) {
            return player.visible(timeout);
        }

        private void play() {
            player.play();
        }

        private boolean isPlaying(Duration timeout) {
            return player.isPlaying(timeout);
        }

        private static class VideoPlayer extends BaseComponent {

            @ElementInfo(name = "Play", findBy = @FindBy(css = "button.ytp-large-play-button"))
            private Button playButton;

            private VideoPlayer(IParent parent) {
                super("Video player", parent, By.id("movie_player"));
            }

            private void play() {
                playButton.click();
            }

            private boolean isPlaying(Duration timeout) {
                return getComponentElement().waitCssClassContains("playing-mode", timeout);
            }
        }
    }
}
