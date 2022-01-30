package com.intpfy.gui.pages.emi;

import com.intpfy.gui.components.emi.profile.ProfileDataComponent;
import com.intpfyqa.gui.web.selenium.IPageContext;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Element;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

public class ProfilePage extends BaseEmiPage {

    @ElementInfo(name = "Page title", findBy = @FindBy(xpath = "//h1[text() = 'Edit Profile']"))
    private Element pageTitle;

    private final ProfileDataComponent profileData;

    public ProfilePage(IPageContext pageContext) {
        super("Profile page", pageContext);
        profileData = new ProfileDataComponent(this);
    }

    @Override
    public boolean isOpened(Duration timeout) {
        return pageTitle.visible(timeout);
    }

    public String getProfileDataEmail() {
        return profileData.getEmail();
    }

    public boolean isProfileDataEmailNotEqual(String email, Duration timeout) {
        return profileData.isEmailNotEqual(email, timeout);
    }
}
