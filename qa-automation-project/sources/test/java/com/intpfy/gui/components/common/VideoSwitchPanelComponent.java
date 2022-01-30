package com.intpfy.gui.components.common;

import com.intpfy.gui.complex_elements.Dropdown;
import com.intpfy.model.VideoQuality;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfy.gui.complex_elements.selection.ComplexCheckbox;
import com.intpfyqa.settings.WebSettings;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import static com.intpfy.model.VideoQuality.*;

public class VideoSwitchPanelComponent extends BaseComponent {

    @ElementInfo(name = "Video quality dropdown", findBy = @FindBy(css = "div.switch-resolution"))
    private Dropdown videoQualityDropdown;

    @ElementInfo(name = "Show video complex checkbox", findBy = @FindBy(css = "div.switch-panel__toggle div.toggle-switch"))
    private ComplexCheckbox showVideoComplexCheckbox;

    public VideoSwitchPanelComponent(IParent parent) {
        super("Video switch panel", parent, By.cssSelector("div.switch-panel"));
    }

    public void showVideo() {
        if (!showVideoComplexCheckbox.isSelected()) {
            showVideoComplexCheckbox.select();
            showVideoComplexCheckbox.waitIsSelected(WebSettings.AJAX_TIMEOUT);
        }
    }

    public void hideVideo() {
        if (showVideoComplexCheckbox.isSelected()) {
            showVideoComplexCheckbox.unselect();
            showVideoComplexCheckbox.waitIsNotSelected(WebSettings.AJAX_TIMEOUT);
        }
    }

    public void selectHdQuality() {
        selectQuality(HD);
    }

    public void selectHighQuality() {
        selectQuality(High);
    }

    public void selectLowQuality() {
        selectQuality(Low);
    }

    private void selectQuality(VideoQuality quality) {
        videoQualityDropdown.getComponentElement().moveMouseIn();
        switch (quality) {
            case HD:
                videoQualityDropdown.selectWithAssertion(HD.name());
                break;
            case High:
                videoQualityDropdown.selectWithAssertion(High.name());
                break;
            case Low:
                videoQualityDropdown.selectWithAssertion(Low.name());
                break;
            default:
                throw new IllegalArgumentException(String.format("No video quality available for: '%s'.", quality));
        }
    }
}
