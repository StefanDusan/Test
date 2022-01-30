package com.intpfy.verifiers.emi;

import com.intpfy.gui.dialogs.emi.EventArchivesDW;
import com.intpfy.gui.pages.emi.EventsPage;
import com.intpfy.model.Language;
import com.intpfy.model.event.Event;
import com.intpfyqa.gui.web.selenium.VerifyPage;
import com.intpfyqa.test.Verify;

import java.io.File;

import static com.intpfy.verifiers.emi.EventsPageVerificationMessages.*;

public class EventsPageVerifier extends BaseEmiVerifier {

    private final EventsPage page;

    public EventsPageVerifier(EventsPage page) {
        super(page);
        this.page = page;
    }

    public void assertExists(Event event) {
        Verify.assertTrue(page.exists(event), String.format(EVENT_EXISTS, event));
    }

    public void verifyData(Event event) {

        assertExists(event);

        VerifyPage.onPage(page, ACTUAL_EVENT_DATA_DISPLAYED)
                .booleanValueShouldBeEqual((p) -> page.isNameToDisplayActual(event), true, ACTUAL_NAME_TO_DISPLAY_DISPLAYED)
                .booleanValueShouldBeEqual((p) -> page.areLanguagesActual(event), true, ACTUAL_LANGUAGES_DISPLAYED)
                .booleanValueShouldBeEqual((p) -> page.isStartTimeActual(event), true, ACTUAL_START_TIME_DISPLAYED)
                .booleanValueShouldBeEqual((p) -> page.isEndTimeActual(event), true, ACTUAL_END_TIME_DISPLAYED)
                .booleanValueShouldBeEqual((p) -> page.isStartDateActual(event), true, ACTUAL_START_DATE_DISPLAYED)
                .booleanValueShouldBeEqual((p) -> page.isAudienceAccessActual(event), true, ACTUAL_AUDIENCE_ACCESS_VALUE_DISPLAYED)
                .completeVerify();
    }

    public void verifyDefaultTableColumns() {

        VerifyPage.onPage(page, ALL_DEFAULT_TABLE_COLUMNS_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isOrganizationTableColumnPresent, true, ORGANIZATION_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isEventNameTableColumnPresent, true, EVENT_NAME_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isLanguagesTableColumnPresent, true, LANGUAGES_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isEventActiveTableColumnPresent, true, EVENT_ACTIVE_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isStartAndEndTimeTableColumnPresent, true, START_AND_END_TIME_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isEventLocationTableColumnPresent, true, EVENT_LOCATION_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isAccessTokensTableColumnPresent, true, ACCESS_TOKENS_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isEventManagerAndModeratorTableColumnPresent, true, EVENT_MANAGER_AND_MODERATOR_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isDurationTableColumnPresent, true, DURATION_ACCUMUL_TIME_MINUTES_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isCurrentMaxUsersTableColumnPresent, true, CURRENT_MAX_USERS_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isMobileAccessTableColumnPresent, true, MOBILE_ACCESS_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isAudienceAccessToSourceTableColumnPresent, true, AUDIENCE_ACCESS_TO_SOURCE_COLUMN_PRESENT)
                .booleanValueShouldBeEqual(EventsPage::isActionsTableColumnPresent, true, ACTIONS_COLUMN_PRESENT)
                .completeVerify();
    }

    public void verifyMediaFilesCountForSource(EventArchivesDW eventArchivesDW, int expectedCount) {
        int actualCount = eventArchivesDW.getMediaFilesCountForSource();
        String message = String.format(SOURCE_MEDIA_FILES_COUNT, expectedCount);
        Verify.verifyEquals(actualCount, expectedCount, message);
    }

    public void verifyMediaFilesCountForLanguage(EventArchivesDW eventArchivesDW, int expectedCount, Language language) {
        int actualCount = eventArchivesDW.getMediaFilesCountForLanguage(language);
        String message = String.format(LANGUAGE_MEDIA_FILES_COUNT, language, expectedCount);
        Verify.verifyEquals(actualCount, expectedCount, message);
    }

    public void verifySourceRecordingFileExists(File file) {
        String message = String.format(SOURCE_RECORDING_FILE_EXISTS, file.getName());
        verifyFileExists(file, message);
    }

    public void verifyLanguageRecordingFileExists(File file, Language language) {
        String message = String.format(LANGUAGE_RECORDING_FILE_EXISTS, language, file.getName());
        verifyFileExists(file, message);
    }
}
