package com.intpfyqa.logging.impl.log4j_html;

import com.intpfyqa.test_rail.Reporter;
import com.intpfyqa.utils.FileStorage;
import com.intpfyqa.utils.FileStorageItem;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.HTMLLayout;
import org.apache.log4j.spi.LoggingEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Test debug layout
 */
public class TestLogLayout extends HTMLLayout {

    /**
     * screenshot substring in debug message
     */
    private final TestObjectIdentifier testDescription;
    /**
     * datetime format
     */
    private final String timestampFormat = "yyyy-MM-dd HH:mm:ss"; // Default format. Example: 2008-11-21-18:35:21
    /**
     * datetime formatter
     */
    private final SimpleDateFormat formatter = new SimpleDateFormat(timestampFormat, Locale.ENGLISH);

    /**
     * Constructor
     *
     * @param testDescription Test description
     */
    public TestLogLayout(TestObjectIdentifier testDescription) {
        this.testDescription = testDescription;
        this.formatter.setTimeZone(TimeZone.getDefault());
    }

    private String getBugLinks() {
        return testDescription.bugIds().stream()
                .map(this::createBugLink)
                .collect(Collectors.joining("<br/>"));
    }

    private String createBugLink(String bugId) {
        return "<a target='blank' " + "href='" + Reporter.JIRA_URL + bugId + "'>" + bugId + "</a>";
    }

    /**
     * Title of html debug file
     *
     * @return testISF34 name
     */
    public String getTitle() {
        return "Test - " + testDescription.getTestName();
    }

    /**
     * Header of html debug file
     *
     * @return html text debug file should start with
     */
    public String getHeader() {
        return "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>" + getTitle() + "</title>\n" +
                "\t<link rel=\"stylesheet\" href=\"../ext/tst.css\" />\n" +
                "\t<script src=\"../ext/jquery-1.12.0.min.js\"></script>\n" +
                "\t<script src=\"../ext/tst.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<input type='hidden' id='fldr' value='" + Logger.TEST_LOGS_FOLDER_NAME + "/'/></input>\n" +
                "\t<div style='height: 35%; overflow:auto'>\n" +
                "\t\t<table class='title'>\n" +

                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'>Test name</td>\n" +
                "\t\t\t\t<td class='title-right'>" + escapeHtml(testDescription.getTestName()) + "</td>\n" +
                "\t\t\t</tr>\n" +

                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'>Class</td>\n" +
                "\t\t\t\t<td class='title-right'>" + escapeHtml(testDescription.getClassName()) + "</td>\n" +
                "\t\t\t</tr>\n" +

                /*"\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'>Description</td>\n" +
                "\t\t\t\t<td class='title-right'>" + escapeHtml(testDescription.getDescription()) + "</td>\n" +
                "\t\t\t</tr>\n" +

                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'>Story</td>\n" +
                "\t\t\t\t<td class='title-right'>" + getStoryLink() + "</td>\n" +
                "\t\t\t</tr>\n" +

                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'>TestLink</td>\n" +
                "\t\t\t\t<td class='title-right'>" + getTestLinkLink() + "</td>\n" +
                "\t\t\t</tr>\n" +*/

                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'>Date</td>\n" +
                "\t\t\t\t<td class='title-right'>" + formatter.format(Calendar.getInstance(TimeZone.getDefault()).getTime()) + "</td>\n" +
                "\t\t\t</tr>\n" +

                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'><b class='fail'>Known bugs</b></td>\n" +
                "\t\t\t\t<td class='title-right'>" + getBugLinks() + "</td>\n" +
                "\t\t\t</tr>\n" +

                "\t\t</table>\n" +
                "\t\t<br/>\n" +
                "\t\t<br/>\n" +
                "\t\t<input type='radio' name='logLevel' value='main' checked>Business Level</input>\n" +
                "\t\t<input type='radio' name='logLevel' value='debug'>Debug</input>\n" +
                "\t\t<input type='radio' name='logLevel' value='all'>All</input>\n" +
                "\t\t<br/>\n" +
                "\t</div>\n" +

                "\t<div class='test-log-section'>\n" +
                "\t\t\t<table class='log' name='tableLog'>\n" +
                "\t\t\t\t<tr>\n" +
                "\t\t\t\t\t<th class='first'>Time</th>\n" +
                "\t\t\t\t\t<th>Status</th>\n" +
                "\t\t\t\t\t<th>Header</th>\n" +
                "\t\t\t\t\t<th class='msg'>Message</th>\n" +
                "\t\t\t\t\t<th class='last'>Screen</th>\n" +
                "\t\t\t\t</tr>\n";

    }

    /**
     * ends HTML debug file
     *
     * @return html text to correct end file
     */
    public String getFooter() {
        return "\t\t\t</table>\n" +
                "\t\t</div>\n" +
                "</body>\n" +
                "</html>";
    }

    private String escapeHtml(String source) {
        if (source == null) return "";
        String result = StringEscapeUtils.escapeHtml3(source).replace("\n", "<br/>");
        return result.replace("\t", "&nbsp;&nbsp;&nbsp;").replace(" ", "&nbsp;");
    }

    /**
     * Formats debug event
     *
     * @param event event
     * @return formatted HTML string
     */
    public String format(LoggingEvent event) {

        LogEventObject eventObject = EventObjectHelper.eventObjectToLogEvent(event.getMessage().toString());

        String result;
        String txtLevel = null;
        String snapshotUrl = "";

        if (null == eventObject) return "";

        if (eventObject.hasSnapshot()) {
            String savedFile = eventObject.getSnapshotFilePath();

            if (null != savedFile) {
                String relativeLink = getPathRelativeToCurrent(savedFile);
                snapshotUrl = String.format("<a target=new href='%s'>%s</a>",
                        relativeLink, (eventObject.isSnapshotIsImage() ?
                                "<img height=100px width=200px src='" + relativeLink + "'/>" : ""));
            }
        }

        switch (eventObject.getLevel()) {
            case ERROR:
            case FAIL:
                result = "\t\t\t\t<tr class='err'>\n";
                break;
            case DEBUG:
                result = "\t\t\t\t<tr name='debug' style='DISPLAY:NONE;'>\n";
                break;
            case TRACE:
                result = "\t\t\t\t<tr name='trace' style='DISPLAY:NONE;'>\n";
                break;
            case PASS:
                result = "\t\t\t\t<tr class='pass'>\n";
                txtLevel = "PASS";
                break;
            default:
                result = "\t\t\t\t<tr>\n";
        }

        String message = escapeHtml(eventObject.getMessage());

        result += "\t\t\t\t\t<td>" + formatter.format(new Date(event.timeStamp)) + "</td>\n";
        result += "\t\t\t\t\t<td title=\"Level\">" + (txtLevel == null ? event.getLevel() : txtLevel) + "</td>\n";
        result += "\t\t\t\t\t<td title=\"Header\">" + eventObject.getHeader() + "</td>\n";
        result += "\t\t\t\t\t<td title=\"Message\" class='msg'><div>" + message + "</div></td>\n";
        result += "\t\t\t\t\t<td class='last' title=\"Snapshot\">" + snapshotUrl + "</td>\n";
        result += "\t\t\t\t</tr>\n";

        return result;
    }

    private String getPathRelativeToCurrent(String fileToExpect) {
        return Logger.toJSPath(FileStorageItem.createRelativePath(fileToExpect,
                FileStorage.createSubDir(Logger.LOGS_FOLDER_NAME + File.separator +
                        Logger.TEST_LOGS_FOLDER_NAME).getAbsolutePath()));
    }
}
