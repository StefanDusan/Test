package com.intpfy.gui.dialogs.emi;

import com.intpfy.model.Language;
import com.intpfyqa.gui.web.selenium.BaseComponent;
import com.intpfyqa.gui.web.selenium.IParent;
import com.intpfyqa.gui.web.selenium.annotations.ElementInfo;
import com.intpfyqa.gui.web.selenium.elements.Button;
import com.intpfyqa.gui.web.selenium.elements.Element;
import com.intpfyqa.gui.web.selenium.factories.WebFactoryHelper;
import com.intpfyqa.http.ok.HttpHelper;
import com.intpfyqa.settings.WebSettings;
import com.intpfyqa.utils.FileStorage;
import okhttp3.HttpUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.intpfy.util.XpathUtil.createEqualsTextIgnoreCaseLocator;

public class EventArchivesDW extends BaseComponent {

    @ElementInfo(name = "Close", findBy = @FindBy(css = "i.icon-close"))
    private Button closeButton;

    public EventArchivesDW(IParent parent) {
        super("Event archives dialog window", parent, By.xpath("//div[@class='modal-content' and .//span[text()='Download media']]"));
    }

    public void expandMediaFilesListForSource() {
        info("Expand Media files list for Source.");
        expandMediaFilesList(Language.Source);
    }

    public void expandMediaFilesListForLanguage(Language language) {
        info(String.format("Expand Media files list for '%s' language.", language));
        expandMediaFilesList(language);
    }

    public int getMediaFilesCountForSource() {
        return getMediaFilesCountForLanguage(Language.Source);
    }

    public int getMediaFilesCountForLanguage(Language language) {
        return getMediaFilesElements(language).size();
    }

    public void downloadMediaFileToFileStorageForSource(int fileIndexOnUI, String fileName, String subDir) {
        downloadMediaFileToFileStorageForLanguage(fileIndexOnUI, fileName, subDir, Language.Source);
    }

    public void downloadMediaFileToFileStorageForLanguage(int fileIndexOnUI, String fileName, String subDir, Language language) {
        List<String> mediaFilesLinks = getLinksForMediaFiles(language);
        String mediaFileLink = mediaFilesLinks.get(fileIndexOnUI - 1);
        FileStorage.putFile(HttpHelper.download(HttpUrl.get(mediaFileLink), null), fileName, subDir);
    }

    public void close() {
        info("Close.");
        closeButton.click();
    }

    private void expandMediaFilesList(Language language) {
        createLanguageButton(language).click();
        waitForMediaFilesListToBeExpanded(language);
    }

    private Button createLanguageButton(Language language) {
        return WebFactoryHelper.getElementFactory()
                .createElement(Button.class, this, String.format("'%s' language button", language),
                        By.xpath(".//button[" + createEqualsTextIgnoreCaseLocator(language.name()) + "]"));
    }

    private void waitForMediaFilesListToBeExpanded(Language language) {
        createMediaFilesExpandAreaElement(language).waitCssClassContains("in collapse", WebSettings.AJAX_TIMEOUT);
    }

    private Element createMediaFilesExpandAreaElement(Language language) {
        return WebFactoryHelper.getElementFactory()
                .createElement(Element.class, this, String.format("'%s' language expand area", language),
                        By.xpath(".//button[" + createEqualsTextIgnoreCaseLocator(language.name()) + "]/following-sibling::div"));
    }

    private List<String> getLinksForMediaFiles(Language language) {
        return getMediaFilesElements(language)
                .stream()
                .map(e -> e.createChild("Button", By.linkText("Download")))
                .map(b -> b.getProperty("href"))
                .collect(Collectors.toList());
    }

    private List<Element> getMediaFilesElements(Language language) {
        String languageName = language.name().toLowerCase(Locale.ROOT);
        return this.getComponentElement().children(By.xpath(
                ".//div[contains(@class, 'archives-collapse') and .//b[" + createEqualsTextIgnoreCaseLocator(languageName) + "]]" +
                        "//div[@class='row archives-item'] | " +
                        ".//div[contains(@class, 'archives-collapse') and .//b[" + createEqualsTextIgnoreCaseLocator(languageName) + "]]" +
                        "/following-sibling::div/div[@class='row archives-item']"
        ));
    }
}
