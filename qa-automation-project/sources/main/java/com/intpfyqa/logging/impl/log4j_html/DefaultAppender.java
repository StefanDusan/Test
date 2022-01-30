package com.intpfyqa.logging.impl.log4j_html;

import com.intpfyqa.utils.FileStorage;
import org.apache.log4j.FileAppender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * Default logging appender
 */
public class DefaultAppender extends FileAppender {

    private String timestampFormat = "yyyy-MM-dd HH:mm:ss"; // Default format. Example: 2008-11-21-18:35:21
    /**
     * datetime formatter
     */
    private SimpleDateFormat formatter = new SimpleDateFormat(timestampFormat, Locale.ENGLISH);

    public DefaultAppender() throws IOException {
        super(new DefaultLayout(),
                FileStorage.createNewFileOrReplaceExisting("suite_data.js", Logger.TEST_LOGS_SUB_FOLDER)
                        .getRealFile().getAbsolutePath(), false);
    }

    void createHtml(SuiteObjectIdentifier suite) {
        File html = FileStorage.createNewFileOrReplaceExisting(
                System.getProperty("debug.index.name",
                "suite-" + suite.getName() + "-" +
                        new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".html"),
                Logger.LOGS_FOLDER_NAME).getRealFile();

        try {
            if (html.exists()) html.delete();
            html.createNewFile();
            FileWriter wr = new FileWriter(html);
            wr.write(getHtml(suite));
            wr.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getHtml(SuiteObjectIdentifier suite) {

        StringBuilder s = new StringBuilder();
        s.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>Suite - " + suite.getName() + "</title>\n" +
                "\t<link rel=\"stylesheet\" href=\"ext/style.css\" />\n" +
                "\t<link rel=\"stylesheet\" href=\"ext/suite.css\" />\n" +
                "\t<script src=\"ext/jquery-1.12.0.min.js\"></script>\n" +
                "\t<script src=\"ext/jstree.min.js\"></script>\n" +
                "\t<script src=\"ext/suite.js\"></script>\n" +
                "\t<script src=\"" + Logger.TEST_LOGS_FOLDER_NAME + "/suite_data.js\"></script>\n" +
                "\t<meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<input type='hidden' id='fldr' value='" + Logger.TEST_LOGS_FOLDER_NAME + "/'/></input>\n" +
                "\t<div>\n" +
                "\t\t<table class='title'>\n");

        for (Map.Entry<String, Object> entry : suite.getProperties().entrySet()) {
            s.append(
                    "\t\t\t<tr>\n" +
                            "\t\t\t\t<td class='title-left'>" + entry.getKey() + "</td>\n" +
                            "\t\t\t\t<td class='title-right'>" + entry.getValue() + "</td>\n" +
                            "\t\t\t</tr>\n");
        }

        s.append("\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'>Date</td>\n" +
                "\t\t\t\t<td class='title-right'>" + formatter.format(Calendar.getInstance().getTime()) + "</td>\n" +
                "\t\t\t</tr>\n" +

                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'><b class='pass'>Pass:</b></td>\n" +
                "\t\t\t\t<td class='title-right'><b id='pass_stat' class='pass'>-</b></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td class='title-left'><b class='fail'>Fail:</b></td>\n" +
                "\t\t\t\t<td class='title-right'><b id='fail_stat' class='fail'>-</b></td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\t\t<table>\n" +
                "\t\t\t<tr>\n" +
                "\t\t\t\t<td>\n" +
                "\t\t\t\t\t<input type='checkbox' id='treeGroup' checked>Group</input>\n" +
                "\t\t\t\t\t<input type='checkbox' id='treeFailed' checked>Only failed</input>\n" +
                "\t\t\t\t\t<input type='checkbox' id='treeConfig' checked>Hide configuration</input>\n" +
                "\t\t\t\t</td>\n" +
                "\t\t\t</tr>\n" +
                "\t\t</table>\n" +
                "\t</div>\n" +
                "\t<div id='holder'>\n" +
                "\t\t<div class='tree' id='tree'></div>\n" +
                "\t\t<div class='content' id='content'>\n" +
                "\t\t\t<div id='stattable'>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<iframe id='iframe' sandbox=\"allow-same-origin allow-scripts allow-popups allow-forms\";></iframe>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>");

        return s.toString();
    }

}
