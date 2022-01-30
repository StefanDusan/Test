package com.intpfyqa.logging.impl.log4j_html;

import com.intpfyqa.utils.FileStorage;
import org.apache.log4j.FileAppender;

import java.io.IOException;

/**
 * Test debug file appender
 */
public class TestLogAppender extends FileAppender {

    /**
     * Constructor
     *
     * @param testDescription TestDescription
     * @throws IOException if lof file creation failed
     */
    public TestLogAppender(TestObjectIdentifier testDescription) throws IOException {
        super(new TestLogLayout(testDescription),
                FileStorage.createNewFileOrReplaceExisting(testDescription.getId() + ".html",
                        Logger.TEST_LOGS_SUB_FOLDER).getRealFile().getAbsolutePath(), false);
    }
}
