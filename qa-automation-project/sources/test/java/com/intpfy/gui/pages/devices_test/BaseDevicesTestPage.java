package com.intpfy.gui.pages.devices_test;

import com.intpfyqa.gui.web.selenium.BaseAutomationPage;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.annotations.Matcher;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.util.matchers.ElementFromListMatcherByIndex;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public abstract class BaseDevicesTestPage extends BaseAutomationPage {

    private final ControlsComponent controls;

    BaseDevicesTestPage(String name, IPageContext pageContext) {
        super(name, pageContext);
        controls = new ControlsComponent(this);
    }

    public abstract BaseAutomationPage next();

    public boolean isNextButtonVisible(Duration timeout) {
        return controls.isNextButtonVisible(timeout);
    }

    public boolean isSkipTutorialButtonVisible(Duration timeout) {
        return controls.isSkipTutorialButtonVisible(timeout);
    }

    public boolean areDotsControlsVisible(Duration timeout) {
        return controls.areDotsControlsVisible(timeout);
    }

    public TestDevicesPage skipTutorial() {
        info("Skip tutorial.");
        controls.skipTutorial();
        return new TestDevicesPage(getPageContext());
    }

    public TestYourConnectionPage openTestYourConnectionPageByDot() {
        info("Open 'Test your connection' page by dot.");
        controls.clickOnDot_6();
        return new TestYourConnectionPage(getPageContext());
    }

    void clickNext() {
        controls.clickNext();
    }

    private static class ControlsComponent extends BaseComponent {

        @ElementInfo(name = "Next", findBy = @FindBy(css = "button.controls__button-main"))
        private Button nextButton;

        @ElementInfo(name = "Skip tutorial", findBy = @FindBy(css = "button.controls__button-secondary"))
        private Button skipTutorialButton;

        private final ControlsDotsComponent controlsDots;

        private ControlsComponent(IParent parent) {
            super("Controls", parent, By.cssSelector("div.controls"));
            controlsDots = new ControlsDotsComponent(this);
        }

        private void clickNext() {
            nextButton.click();
        }

        private void skipTutorial() {
            skipTutorialButton.click();
        }

        private boolean isNextButtonVisible(Duration timeout) {
            return nextButton.visible(timeout);
        }

        private boolean isSkipTutorialButtonVisible(Duration timeout) {
            return skipTutorialButton.visible(timeout);
        }

        private boolean areDotsControlsVisible(Duration timeout) {
            return controlsDots.visible(timeout);
        }

        private void clickOnDot_6() {
            controlsDots.clickOnDot_6();
        }

        private static class ControlsDotsComponent extends BaseComponent {

            @ElementInfo(name = "Dot 6", findBy = @FindBy(xpath = ".//div[contains(@class, 'dot')]"), matcher =
            @Matcher(clazz = ElementFromListMatcherByIndex.class, args = "6"))
            private Button dotButton_6;

            private ControlsDotsComponent(IParent parent) {
                super("Controls dots", parent, By.cssSelector("div.controls__dots"));
            }

            private void clickOnDot_6() {
                dotButton_6.click();
            }
        }
    }
}
